package client.controllers;

import Comunidad.Comunidad;
import Comunidad.Miembro;
import Comunidad.Usuario;
import client.models.ModelBase;
import client.models.repositories.RepositorioDeComunidades;
import client.models.repositories.RepositorioDeUsuarios;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static client.helpers.ControllerHelpers.getRolUsuarioFromSession;

public class ComunidadController {
    RepositorioDeComunidades repositorioDeComunidades = new RepositorioDeComunidades();
    RepositorioDeUsuarios repositorioDeUsuarios = new RepositorioDeUsuarios();

    public void mostrarComunidades(Context context){
        //TODO Solo se deben mostrar las comunidades que no incluyan a ese usuario en sus miembros
        int idUsuario = Integer.parseInt(context.pathParam("id_usuario"));
        ModelBase model = new ModelBase(getRolUsuarioFromSession(context));
        List<Comunidad> comunidades = this.repositorioDeComunidades.buscarTodos();

        List<Comunidad> comunidadesNuevas = comunidades.stream().filter(comunidad ->
                comunidad.getMiembros().stream().anyMatch(miembro -> miembro.getUsuario().getId_usuario() == idUsuario)
        ).toList();

        model.put("comunidades", comunidadesNuevas);

        context.render("comunidades.hbs", model.getModel());
    }

    public void nuevoMiembro(Context context){
        //TODO Se tiene que instanciar un nuevo miembro con atributos del usuario, y agregarlo a la comunidad
        int idUsuario = Integer.parseInt(context.pathParam("id_usuario"));
        int idComunidad = Integer.parseInt(context.pathParam("id_comunidad"));
        Comunidad comunidad = (Comunidad) repositorioDeComunidades.buscarPorId(idComunidad);
        Usuario usuario = (Usuario) repositorioDeUsuarios.buscarPorId(idUsuario);

        Miembro miembro = new Miembro();

        comunidad.getMiembros().add(miembro);

        context.redirect("/comunidades");
    }
}
