package client.controllers;

import Comunidad.Incidente;
import Comunidad.Usuario;
import Notificador.FechaHora;
import Servicios.Servicio;
import Servicios.ServicioPorEstablecimiento;
import client.models.ModelBase;
import client.models.repositories.RepositorioDeIncidentes;
import client.models.repositories.RepositorioDeServicios;
import client.models.repositories.RepositorioDeUsuarios;
import client.server.utils.ICrudViewsHandler;
import dbManager.EntityManagerHelper;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static client.helpers.ControllerHelpers.getRolUsuarioFromSession;

public class IncidenteController extends EntityManagerHelper implements ICrudViewsHandler, Handler {
    RepositorioDeIncidentes repositorioDeIncidentes = new RepositorioDeIncidentes();
    RepositorioDeServicios repositorioDeServicios = new RepositorioDeServicios();

    public IncidenteController(RepositorioDeIncidentes repositorioDeIncidentes) {
        this.repositorioDeIncidentes = repositorioDeIncidentes;
    }

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<Incidente> incidentes = null;
        model.put("Incidentes", incidentes);
        //context.render("Usuarios/Usuarios.hbs", model);
    }


    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) {

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }

    @Override
    public void handle(@NotNull Context context) throws Exception {

    }

    public void mostrarIncidentes(Context context){
        ModelBase model = new ModelBase(getRolUsuarioFromSession(context));
        List<ServicioPorEstablecimiento> serviciosPorE = this.repositorioDeServicios.buscarTodosPorEstablecimiento();
        List<Incidente> incidentes =  this.repositorioDeIncidentes.buscarIncidentesNoResueltos();
        List<Incidente> resueltos = this.repositorioDeIncidentes.buscarIncidentesResueltos();
        //System.out.println(incidentes.get(0).getServicioAfectado());
        if(serviciosPorE!=null){
            model.put("servicios", serviciosPorE);
        }
        if(incidentes!=null){
            model.put("noresueltos", incidentes);
        }
        if(resueltos!=null){
            model.put("resueltos", resueltos);
        }
            context.render("incidentes.hbs", model.getModel());
    }

    public void nuevoIncidente(Context context){
        try {
            String descripcion = context.formParam("descripcion");
            int idServicio = Integer.parseInt(context.formParam("servicio"));
            ServicioPorEstablecimiento servicioPorEstablecimiento = repositorioDeServicios.buscarServicioPorEstablicimientoPorId(idServicio);
            //Servicio unServicio = (Servicio) repositorioDeServicios.buscarPorId(servicioPorEstablecimiento.getServicio().getId_servicio());
            FechaHora fechaHoraIncidente = new FechaHora(LocalDate.now(), LocalTime.now());
            Incidente unIncidente = new Incidente(null, descripcion, servicioPorEstablecimiento, null, fechaHoraIncidente, false);
            entityManager().getTransaction().begin();
            entityManager().persist(fechaHoraIncidente);
            entityManager().persist(unIncidente);
            //entityManager().persist(unServicio);
            //entityManager().persist(servicioPorEstablecimiento);
            entityManager().getTransaction().commit();
            //todo persistir incidente
            //TODO entityManager().clear();
            context.redirect("/incidents");
        }
        catch (Exception e){
            context.result(e.getMessage());
        }
    }

    public void resolverIncidente(Context context){

        //System.out.println(context.pathParam("id_incidente"));
        Incidente unIncidente = (Incidente) repositorioDeIncidentes.buscarPorId(Integer.parseInt(context.pathParam("id_incidente")));
        System.out.println(unIncidente.getId_incidente());
        unIncidente.resolverIncidente();
        entityManager().getTransaction().begin();
        entityManager().persist(unIncidente.getTimestampFin());
        entityManager().persist(unIncidente);
        entityManager().getTransaction().commit();
        context.redirect("/incidents");
    }

    public void mostrarRanking(Context context){
        ModelBase model = new ModelBase(getRolUsuarioFromSession(context));
        context.render("rankingIncidentes.hbs",model.getModel());
    }

}
