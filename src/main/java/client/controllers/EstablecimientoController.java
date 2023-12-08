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
import services.georef.ServicioGeoref;
import services.georef.entities.*;

import javax.persistence.EntityManager;
import java.util.*;

import static client.helpers.ControllerHelpers.*;

public class EstablecimientoController {
    public void nuevoEstablecimiento(Context context) {
        RepositorioDeEstablecimientos repositorioDeEstablecimientos = new RepositorioDeEstablecimientos();
        RepositorioDeEntidades repositorioDeEntidades = new RepositorioDeEntidades();
        repositorioDeEstablecimientos.entityManager().getTransaction().begin();

        int idEntidad = Integer.parseInt(context.pathParam("id_entidad"));
        Entidad entidad = (Entidad) repositorioDeEntidades.buscarPorId(idEntidad);

        String nombreEstablecimiento = context.formParam("nombre");
        Localizacion localizacion = setearLocalizacion(context);
        Establecimiento establecimiento = new Establecimiento(nombreEstablecimiento, localizacion);

        entidad.agregarEstablecimiento(establecimiento);
        establecimiento.setEntidad(entidad);
/*
        repositorioDeEstablecimientos.entityManager().persist(prov);
        repositorioDeEstablecimientos.entityManager().persist(depto);

        repositorioDeEstablecimientos.entityManager().persist(loc);


        repositorioDeEstablecimientos.entityManager().persist(mun);*/


        repositorioDeEstablecimientos.entityManager().persist(localizacion); // Persiste la localización
        repositorioDeEstablecimientos.entityManager().persist(establecimiento);
        repositorioDeEstablecimientos.entityManager().persist(entidad);

        repositorioDeEstablecimientos.entityManager().getTransaction().commit();
        repositorioDeEstablecimientos.entityManager().clear();
        repositorioDeEstablecimientos.entityManager().close();
        context.redirect("/entidades/"+ idEntidad);
    }

    public void establecimiento(Context context) {
        try {
            //int idEntidad = Integer.parseInt(context.pathParam("id_entidad"));
            ServicioGeoref servicioGeoref = ServicioGeoref.getInstancia();
            List<Provincia> lista = servicioGeoref.listadoProvincias().provincias;
            RepositorioDeServicios repositorioDeServicios = new RepositorioDeServicios();
            List<TipoServicio> tiposServicio = repositorioDeServicios.buscarTodosLosTiposDeServicios();
            ModelBase model = new ModelBase(generarMapModelBase(context));
            int idEstablecimiento = Integer.parseInt(context.pathParam("id_establecimiento"));
            Establecimiento establecimiento = (Establecimiento) new RepositorioDeEstablecimientos().buscarPorId(idEstablecimiento);

            Collections.sort(lista, Comparator.comparing(Provincia::getNombre));
            model.put("provincias", lista);
            model.put("Establecimiento", establecimiento);
            model.put("tiposServicio", tiposServicio);
            //model.put("id_entidad", idEntidad);
            context.render("/establecimiento.hbs", model.getModel());
        }
        catch (Exception e) {
            context.result(e.getMessage());
        }
    }

    public void provinciaElegidaNuevoEstablecimiento(Context context){
        try {
            int idProvincia = Integer.parseInt(context.pathParam("id_provincia"));
            ServicioGeoref servicioGeoref = ServicioGeoref.getInstancia();
            List<Departamento> departamentos = servicioGeoref.listadoDepartamentosDeProvincia(idProvincia).departamentos;
            List<Municipio> municipios = servicioGeoref.listadoMunicipiosDeProvincia(idProvincia).municipios;

            Collections.sort(departamentos, Comparator.comparing(Departamento::getNombre));
            Collections.sort(municipios, Comparator.comparing(Municipio::getNombre));
            String html = "";
            for (Departamento depto : departamentos) {
                html += "<option value=\"" + depto.getNombre() + "\">" + depto.getNombre() + "</option>";
            }
            html+="|";
            for (Municipio mun : municipios) {
                html += "<option value=\"" + mun.getNombre() + "\">" + mun.getNombre() + "</option>";
            }
            List<Localidad> localidades;
            if(idProvincia==2){
                localidades = servicioGeoref.localidadesDeProvincia(idProvincia).localidades;
                Collections.sort(localidades, Comparator.comparing(Localidad::getNombre));
                html+="|";
                for (Localidad loc : localidades) {
                    html += "<option value=\"" + loc.getNombre() + "\">" + loc.getNombre() + "</option>";
                }
            }
            context.result(html);
        }
        catch (Exception e) {
            context.result(e.getMessage());
        }
    }

    public void eliminarEstablecimiento(Context context){
        int idEstablecimiento = Integer.parseInt(context.pathParam("id_establecimiento"));
        int idEntidad = Integer.parseInt(context.pathParam("id_entidad"));
        RepositorioDeEstablecimientos repositorioDeEstablecimientos = new RepositorioDeEstablecimientos();
        repositorioDeEstablecimientos.entityManager().getTransaction().begin();
        Establecimiento establecimientoAEliminar = (Establecimiento) repositorioDeEstablecimientos.buscarPorId(idEstablecimiento);
        establecimientoAEliminar.getEntidad().eliminarEstablecimiento(establecimientoAEliminar);
        repositorioDeEstablecimientos.entityManager().persist(establecimientoAEliminar.getEntidad());
        establecimientoAEliminar.setEntidad(null);
        establecimientoAEliminar.getServiciosDisponibles().clear();
        repositorioDeEstablecimientos.entityManager().remove(establecimientoAEliminar);
        repositorioDeEstablecimientos.entityManager().getTransaction().commit();
        repositorioDeEstablecimientos.entityManager().clear();
        repositorioDeEstablecimientos.entityManager().close();
        context.redirect("/entidades/"+ idEntidad);
    }


    public void guardarEstablecimiento(Context context) {
        int idEstablecimiento = Integer.parseInt(context.pathParam("id_establecimiento"));
        String nombreForm = context.formParam("nombre");
        RepositorioDeEstablecimientos repositorioDeEstablecimientos = new RepositorioDeEstablecimientos();
        repositorioDeEstablecimientos.entityManager().getTransaction().begin();
        Establecimiento establecimientoAEditar = (Establecimiento) repositorioDeEstablecimientos.buscarPorId(idEstablecimiento);
        establecimientoAEditar.setNombre(nombreForm);
        Localizacion localizacion = setearLocalizacion(context);
        establecimientoAEditar.setLocalizacion(localizacion);
        repositorioDeEstablecimientos.entityManager().persist(establecimientoAEditar);
        repositorioDeEstablecimientos.entityManager().getTransaction().commit();
        repositorioDeEstablecimientos.entityManager().clear();
        repositorioDeEstablecimientos.entityManager().close();
        context.redirect("/entidades/" + establecimientoAEditar.getEntidad().getId_entidad());
    }

    public Localizacion setearLocalizacion(Context context){
        String nombreProvincia = context.formParam("provincia");
        String nombreDepartamento = context.formParam("departamento");
        String nombreMunicipio = context.formParam("municipio");

        Municipio mun = null;
        Localidad loc = null;

        Provincia prov = new Provincia();
        prov.setNombre(nombreProvincia);
        Departamento depto = new Departamento();
        depto.setNombre(nombreDepartamento);
        if(nombreMunicipio == nombreMunicipio.toUpperCase()){
            loc = new Localidad();
            loc.setNombre(nombreMunicipio);
        }
        else{
            mun = new Municipio();
            mun.setNombre(nombreMunicipio);
        }



        Localizacion localizacion = new Localizacion(prov, depto, loc, mun);
        return localizacion;
    }
}
