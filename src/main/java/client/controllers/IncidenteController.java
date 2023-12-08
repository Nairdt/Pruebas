package client.controllers;

import Comunidad.Comunidad;
import Comunidad.Incidente;
import Comunidad.Miembro;
import Comunidad.Usuario;
import Notificador.FechaHora;
import Notificador.TipoNotificacion;
import Organizaciones.Entidad;
import Servicios.Servicio;
import Servicios.ServicioPorEstablecimiento;
import client.models.ModelBase;
import client.models.repositories.RepositorioDeComunidades;
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
import javax.persistence.EntityTransaction;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static client.helpers.ControllerHelpers.*;

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
        RepositorioDeComunidades repositorioDeComunidades = new RepositorioDeComunidades();
        List<Comunidad> comunidades = repositorioDeComunidades.buscarPorIdUsuario((Integer) context.req().getSession().getAttribute("idUsuario"));
        /*List<Comunidad> misComunidades = comunidades.stream().filter(comunidad ->
                comunidad.getMiembros().stream().anyMatch(miembro -> miembro.getUsuario().getId_usuario() == (Integer) context.req().getSession().getAttribute("idUsuario"))
        ).toList();*/
        ModelBase model = new ModelBase(generarMapModelBase(context));
        List<ServicioPorEstablecimiento> serviciosPorE = this.repositorioDeServicios.buscarTodosPorEstablecimiento();
        List<Incidente> incidentes =  this.repositorioDeIncidentes.buscarIncidentesNoResueltos((Integer) context.req().getSession().getAttribute("idUsuario"));
        List<Incidente> resueltos = this.repositorioDeIncidentes.buscarIncidentesResueltos((Integer) context.req().getSession().getAttribute("idUsuario"));
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
        model.put("comunidades", comunidades);
            context.render("incidentes.hbs", model.getModel());
    }

    public void nuevoIncidente(Context context){
        try {
            String descripcion = context.formParam("descripcion");
            int idServicio = Integer.parseInt(context.formParam("servicio"));
            int idComunidad = Integer.parseInt(context.formParam("comunidad"));
            RepositorioDeComunidades repositorioDeComunidades = new RepositorioDeComunidades();
            Comunidad comunidad = repositorioDeComunidades.buscarPorId(idComunidad);
            ServicioPorEstablecimiento servicioPorEstablecimiento = repositorioDeServicios.buscarServicioPorEstablicimientoPorId(idServicio);
            Entidad entidadAfectada = servicioPorEstablecimiento.getEstablecimiento().getEntidad();
            Miembro miembro = (Miembro) repositorioDeComunidades.buscarMiembroPorId((Integer) context.req().getSession().getAttribute("idUsuario"), idComunidad);

            miembro.notificarIncidente(descripcion, TipoNotificacion.NO_FUNCIONA_SERVICIO, servicioPorEstablecimiento, null);
            FechaHora fechaHoraIncidente = new FechaHora(LocalDate.now(), LocalTime.now());
            Incidente unIncidente = new Incidente(comunidad, descripcion, servicioPorEstablecimiento, getNombreFromSession(context), fechaHoraIncidente, false);
            unIncidente.setEntidad(entidadAfectada);
            unIncidente.setServicioAfectado(servicioPorEstablecimiento.getServicio());
            comunidad.agregarIncidente(unIncidente, descripcion, servicioPorEstablecimiento, null);
            entidadAfectada.addIncidente(unIncidente);
            EntityTransaction tx = entityManager().getTransaction();
            tx.begin();
                entityManager().persist(fechaHoraIncidente);
                entityManager().persist(miembro);
                entityManager().persist(comunidad);
                entityManager().persist(entidadAfectada);
                entityManager().persist(unIncidente);
                //entityManager().persist(unServicio);
                //entityManager().persist(servicioPorEstablecimiento);
            tx.commit();
            entityManager().clear();
            context.redirect("/incidentes/");
        }
        catch (Exception e){
            context.result(e.getMessage());
        }
    }

    public void resolverIncidente(Context context) throws IOException {
        RepositorioDeComunidades repositorioDeComunidades = new RepositorioDeComunidades();
        int idComunidad = Integer.parseInt(context.pathParam("id_comunidad"));
        //System.out.println(context.pathParam("id_incidente"));
        Incidente unIncidente = (Incidente) repositorioDeIncidentes.buscarPorId(Integer.parseInt(context.pathParam("id_incidente")));

        unIncidente.resolverIncidente(getNombreFromSession(context));
        Miembro miembro = (Miembro) repositorioDeComunidades.buscarMiembroPorId((Integer) context.req().getSession().getAttribute("idUsuario"), idComunidad);

        miembro.notificarIncidente(unIncidente.getObservaciones(), TipoNotificacion.FUNCIONA_SERVICIO, unIncidente.getServicioBase(), null);
        entityManager().getTransaction().begin();
        entityManager().persist(unIncidente.getTimestampFin());
        entityManager().persist(unIncidente);
        entityManager().getTransaction().commit();
        context.redirect("/incidentes/");
    }

    public void mostrarRanking(Context context){
        ModelBase model = new ModelBase(generarMapModelBase(context));
        context.render("rankingIncidentes.hbs",model.getModel());
    }

}
