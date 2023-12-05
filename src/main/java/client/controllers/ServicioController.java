package client.controllers;

import Organizaciones.Establecimiento;
import Servicios.Servicio;
import Servicios.ServicioPorEstablecimiento;
import Servicios.TipoServicio;
import client.helpers.ControllerHelpers;
import client.models.ModelBase;
import client.models.entities.usuarios.Rol;
import client.models.repositories.RepositorioDeEntidades;
import client.models.repositories.RepositorioDeEstablecimientos;
import client.models.repositories.RepositorioDeServicios;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static client.helpers.ControllerHelpers.getRolUsuarioFromSession;

public class ServicioController {
    public void nuevoServicio(Context context){
        int idEstablecimiento = Integer.parseInt(context.pathParam("id_establecimiento"));
        String nombreServicio = context.formParam("nombre_servicio");
        String tipoServicio = context.formParam("tipo_servicio");
        String problemaServicio = context.formParam("problematica_servicio");

        RepositorioDeEstablecimientos repositorioDeEstablecimientos = new RepositorioDeEstablecimientos();
        Establecimiento establecimiento = (Establecimiento) repositorioDeEstablecimientos.buscarPorId(idEstablecimiento);
        RepositorioDeServicios repositorioDeServicios = new RepositorioDeServicios();


        TipoServicio tipo;
        try{
            tipo = repositorioDeServicios.buscarTipoServicioPorNombre(tipoServicio);
        }
        catch(Exception e){
            tipo = new TipoServicio(tipoServicio);
        }
        Servicio servicio;
        try{
            servicio = repositorioDeServicios.buscarPorNombre(nombreServicio);
        }
        catch(Exception e){
            servicio = new Servicio(nombreServicio, tipo);
        }



        if(tipo == null){
            tipo = new TipoServicio(tipoServicio);
        }

        if(servicio == null){
            servicio = new Servicio(nombreServicio, tipo);
        }

        ServicioPorEstablecimiento servicioPorEstablecimiento = new ServicioPorEstablecimiento(nombreServicio, establecimiento, problemaServicio, false, servicio);
        repositorioDeServicios.entityManager().getTransaction().begin();
        repositorioDeServicios.entityManager().persist(tipo);
        repositorioDeServicios.entityManager().persist(servicio);
        repositorioDeServicios.entityManager().persist(servicioPorEstablecimiento);
        repositorioDeServicios.entityManager().getTransaction().commit();

        context.redirect("/establecimientos/" + idEstablecimiento);

    }


    public void servicio(Context context) {
        try {
            int idServicio = Integer.parseInt(context.pathParam("id_servicio"));
            ServicioPorEstablecimiento servicio = (ServicioPorEstablecimiento) new RepositorioDeServicios().buscarServicioPorEstablicimientoPorId(idServicio);
            List<TipoServicio> tiposServicio = new RepositorioDeServicios().buscarTodosLosTiposDeServicios();

            ModelBase model = new ModelBase(getRolUsuarioFromSession(context));
            model.put("Servicio", servicio);
            model.put("tiposServicio", tiposServicio);

            context.render("/servicio.hbs", model.getModel());
        }
        catch (Exception e) {
            context.result(e.getMessage());
        }
    }

    public void editarServicio(Context context){
        int idServicio = Integer.parseInt(context.pathParam("id_servicio"));
        int tipoServ = Integer.parseInt(context.formParam("tipoServicio"));
        String nombreForm = context.formParam("nombre");

        RepositorioDeServicios repositorioDeServicios = new RepositorioDeServicios();
        repositorioDeServicios.entityManager().getTransaction().begin();
        //java.lang.ClassCastException: class Organizaciones.Establecimiento cannot be cast to class Servicios.ServicioPorEstablecimiento (Organizaciones.Establecimiento and Servicios.ServicioPorEstablecimiento are in unnamed module of loader 'app')
        ServicioPorEstablecimiento servicioAEditar = (ServicioPorEstablecimiento) new RepositorioDeServicios().buscarPorId(idServicio);
        TipoServicio tipoServicio = (TipoServicio) new RepositorioDeServicios().buscarTipoServicioPorId(tipoServ);
        servicioAEditar.setNombre(nombreForm);
        servicioAEditar.getServicio().setTipoServicio(tipoServicio);
        repositorioDeServicios.entityManager().persist(servicioAEditar);
        repositorioDeServicios.entityManager().getTransaction().commit();

        context.redirect("/establecimientos/" + servicioAEditar.getEstablecimiento().getId_establecimiento());
    }

    public void cambiarEstadoServicio(Context context){
        try {
            int idServicio = Integer.parseInt(context.pathParam("id_servicio"));
            RepositorioDeServicios repositorioDeServicios = new RepositorioDeServicios();
                repositorioDeServicios.entityManager().getTransaction().begin();
            ServicioPorEstablecimiento servicioACambiarEstado = (ServicioPorEstablecimiento) new RepositorioDeServicios().buscarServicioPorEstablicimientoPorId(idServicio);
            servicioACambiarEstado.cambiarEstado();
            repositorioDeServicios.entityManager().persist(servicioACambiarEstado);
            repositorioDeServicios.entityManager().getTransaction().commit();
            repositorioDeServicios.entityManager().clear();
            context.status(200);
            context.result(servicioACambiarEstado.getHabilitado().toString());
        }
        catch (Exception e) {
            context.result(e.getMessage());
        }
    }

    public boolean nuevoServicio(List<Servicio> servicios, String nombreServicio){
        return servicios.stream().anyMatch(unServicio ->
                unServicio.getNombre().toUpperCase() == nombreServicio.toUpperCase()
        );
    }

}
