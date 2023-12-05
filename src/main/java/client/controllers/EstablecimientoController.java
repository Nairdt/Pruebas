package client.controllers;

import Organizaciones.Entidad;
import Organizaciones.Establecimiento;
import Servicios.Servicio;
import Servicios.ServicioPorEstablecimiento;
import Servicios.TipoServicio;
import client.models.ModelBase;
import client.models.repositories.RepositorioDeEntidades;
import client.models.repositories.RepositorioDeEstablecimientos;
import client.models.repositories.RepositorioDeServicios;
import io.javalin.http.Context;
import services.georef.entities.Localizacion;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static client.helpers.ControllerHelpers.getRolUsuarioFromSession;

public class EstablecimientoController {
    public void nuevoEstablecimiento(Context context) {
        RepositorioDeEstablecimientos repositorioDeEstablecimientos = new RepositorioDeEstablecimientos();
        RepositorioDeEntidades repositorioDeEntidades = new RepositorioDeEntidades();
        repositorioDeEstablecimientos.entityManager().getTransaction().begin();

        int idEntidad = Integer.parseInt(context.pathParam("id_entidad"));
        Entidad entidad = (Entidad) repositorioDeEntidades.buscarPorId(idEntidad);

        String nombreEstablecimiento = context.formParam("nombre");
        String provincia = context.formParam("provincia");
        String departamento = context.formParam("departamento");
        String localidad = context.formParam("localidad");
        String municipio = context.formParam("municipio");

        //Localizacion localizacion = new Localizacion();
        Establecimiento establecimiento = new Establecimiento(nombreEstablecimiento, null);

        entidad.agregarEstablecimiento(establecimiento);
        establecimiento.setEntidad(entidad);

        //repositorioDeEstablecimientos.entityManager().persist(localizacion); // Persiste la localización
        repositorioDeEstablecimientos.entityManager().persist(establecimiento);
        repositorioDeEstablecimientos.entityManager().persist(entidad);

        repositorioDeEstablecimientos.entityManager().getTransaction().commit();
        repositorioDeEstablecimientos.entityManager().close();
        context.redirect("/entidades/"+ idEntidad);
    }

    public void establecimiento(Context context) {
        try {
            ModelBase model = new ModelBase(getRolUsuarioFromSession(context));
            int idEstablecimiento = Integer.parseInt(context.pathParam("id_establecimiento"));
            Establecimiento establecimiento = (Establecimiento) new RepositorioDeEstablecimientos().buscarPorId(idEstablecimiento);

            model.put("Establecimiento", establecimiento);
            context.render("/establecimiento.hbs", model.getModel());
        }
        catch (Exception e) {
            context.result(e.getMessage());
        }
    }
    public void eliminarEstablecimiento(Context context){
        int idEstablecimiento = Integer.parseInt(context.pathParam("id_establecimiento"));
        RepositorioDeEstablecimientos repositorioDeEstablecimientos = new RepositorioDeEstablecimientos();
        repositorioDeEstablecimientos.entityManager().getTransaction().begin();
        Establecimiento establecimientoAEliminar = (Establecimiento) repositorioDeEstablecimientos.buscarPorId(idEstablecimiento);
        establecimientoAEliminar.getEntidad().eliminarEstablecimiento(establecimientoAEliminar);
        repositorioDeEstablecimientos.entityManager().persist(establecimientoAEliminar.getEntidad());
        establecimientoAEliminar.setEntidad(null);
        repositorioDeEstablecimientos.entityManager().remove(establecimientoAEliminar);
        repositorioDeEstablecimientos.entityManager().getTransaction().commit();
    }


    public void guardarEstablecimiento(Context context) {
        int idEstablecimiento = Integer.parseInt(context.pathParam("id_establecimiento"));
        String nombreForm = context.formParam("nombre");
        RepositorioDeEstablecimientos repositorioDeEstablecimientos = new RepositorioDeEstablecimientos();
        repositorioDeEstablecimientos.entityManager().getTransaction().begin();
        Establecimiento establecimientoAEditar = (Establecimiento) repositorioDeEstablecimientos.buscarPorId(idEstablecimiento);
        establecimientoAEditar.setNombre(nombreForm);
        repositorioDeEstablecimientos.entityManager().persist(establecimientoAEditar);
        repositorioDeEstablecimientos.entityManager().getTransaction().commit();
        context.redirect("/entidades/" + establecimientoAEditar.getEntidad().getId_entidad());
    }
}
