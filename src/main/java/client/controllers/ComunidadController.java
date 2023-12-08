package client.controllers;

import Comunidad.Comunidad;
import Comunidad.Miembro;
import Comunidad.Usuario;
import client.models.ModelBase;
import client.models.repositories.RepositorioDeComunidades;
import client.models.repositories.RepositorioDeUsuarios;
import dbManager.LapsoReceptorConverter;
import dbManager.MedioReceptorConverter;
import io.javalin.http.Context;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static client.helpers.ControllerHelpers.*;

public class ComunidadController {
    RepositorioDeUsuarios repositorioDeUsuarios = new RepositorioDeUsuarios();
    RepositorioDeComunidades repositorioDeComunidades = new RepositorioDeComunidades();


    public void nuevaComunidad(Context context) {
            RepositorioDeComunidades repositorioDeComunidades = new RepositorioDeComunidades();

            int idUsuario = getIdUsuarioFromSession(context);

            String nombreComunidad = context.formParam("nombre_comunidad");
            String colabComunidad = context.formParam("colaboracion_comunidad");
            String problemComunidad = context.formParam("problematica_comunidad");

            // Obtener el usuario desde el repositorio
            Usuario usuario = (Usuario) repositorioDeUsuarios.buscarPorId(idUsuario);

            // Crear una nueva comunidad
            Comunidad comunidad = new Comunidad(nombreComunidad, colabComunidad, problemComunidad);

            // Asociar el usuario como administrador de la comunidad
            comunidad.getAdministradores().add(usuario);

            //Persistir la comunidad
            repositorioDeComunidades.guardar(comunidad);

            // Redirigir después de la operación exitosa
            context.redirect("/comunidades/");
    }
    public void mostrarComunidades(Context context){
        //TODO Solo se deben mostrar las comunidades que no incluyan a ese usuario en sus miembros
        int idUsuario = getIdUsuarioFromSession(context);
        ModelBase model = new ModelBase(generarMapModelBase(context));
        List<Comunidad> comunidades = repositorioDeComunidades.buscarTodos();

        List<Comunidad> comunidadesNuevas = comunidades.stream().filter(comunidad ->
                comunidad.getMiembros().stream().allMatch(miembro -> miembro.getUsuario().getId_usuario() != idUsuario)
        ).toList();

        List<Comunidad> misComunidades = comunidades.stream().filter(comunidad ->
                comunidad.getMiembros().stream().anyMatch(miembro -> miembro.getUsuario().getId_usuario() == idUsuario)
        ).toList();

        model.put("comunidades", comunidadesNuevas);
        model.put("misComunidades", misComunidades);

        context.render("comunidades.hbs", model.getModel());
    }

    public void nuevoMiembro(Context context){
        RepositorioDeComunidades repositorioDeComunidades = new RepositorioDeComunidades();
        //TODO Se tiene que instanciar un nuevo miembro con atributos del usuario, y agregarlo a la comunidad
        int idUsuario = getIdUsuarioFromSession(context);
        int idComunidad = Integer.parseInt(context.pathParam("id_comunidad"));
        String medio = context.formParam("medio");
        MedioReceptorConverter medioConverter = new MedioReceptorConverter();
        String lapso = context.formParam("lapso");
        LapsoReceptorConverter lapsoConverter = new LapsoReceptorConverter();

        Comunidad comunidad = (Comunidad) repositorioDeComunidades.buscarPorId(idComunidad);
        Usuario usuario = (Usuario) repositorioDeUsuarios.buscarPorId(idUsuario);
        Miembro miembro = new Miembro(usuario, comunidad, lapsoConverter.convertToEntityAttribute(lapso), medioConverter.convertToEntityAttribute(medio));
        usuario.agregarMembresia(miembro);
        comunidad.getMiembros().add(miembro);
        EntityTransaction tran = repositorioDeComunidades.entityManager().getTransaction();
        tran.begin();
        repositorioDeComunidades.entityManager().persist(usuario);
        repositorioDeComunidades.entityManager().persist(comunidad);
        tran.commit();
        repositorioDeComunidades.entityManager().clear();
        repositorioDeComunidades.entityManager().close();
        context.redirect("/comunidades/");
    }

    public void salirComunidad(Context context){
        RepositorioDeComunidades repositorioDeComunidades = new RepositorioDeComunidades();
        //TODO Se tiene que instanciar un nuevo miembro con atributos del usuario, y agregarlo a la comunidad
        int idUsuario = getIdUsuarioFromSession(context);
        int idComunidad = Integer.parseInt(context.pathParam("id_comunidad"));
        Comunidad comunidad = (Comunidad) repositorioDeComunidades.buscarPorId(idComunidad);
        Miembro miembro = (Miembro) repositorioDeUsuarios.buscarMiembroPorIdUsuarioYComunidad(idUsuario, idComunidad);
        repositorioDeComunidades.entityManager().getTransaction().begin();
        Usuario unUsuario = miembro.getUsuario();
        unUsuario.removerMembresia(miembro);
        comunidad.eliminarMiembro(miembro);
        repositorioDeComunidades.entityManager().remove(miembro);
        repositorioDeComunidades.persist(comunidad);
        repositorioDeComunidades.persist(unUsuario);
        repositorioDeComunidades.entityManager().getTransaction().commit();
        repositorioDeComunidades.entityManager().clear();
        repositorioDeComunidades.entityManager().close();

        context.redirect("/comunidades/");
    }
}
