package client.controllers;

import Comunidad.RolUsuario;
import Organizaciones.Entidad;
import client.models.ModelBase;
import client.models.repositories.RepositorioDeEntidades;
import io.javalin.http.Context;
import org.springframework.ui.Model;
import services.georef.ServicioGeoref;
import services.georef.entities.ListadoProvincias;
import services.georef.entities.Provincia;

import java.util.*;

import static client.helpers.ControllerHelpers.*;

public class EntidadController {
    public void listadoEntidades(Context context) {
        ModelBase model = new ModelBase(generarMapModelBase(context));
        Integer idUsuario = getIdUsuarioFromSession(context);
        RolUsuario rolUsuario = getRolUsuarioFromSession(context);
        List entidades;

        if(rolUsuario == RolUsuario.ADMINISTRADOR)
            entidades = new RepositorioDeEntidades().buscarTodos();
        else
            entidades = new RepositorioDeEntidades().buscarEntidadesPrestador(idUsuario);
        model.put("entidades",entidades);


        context.render("/datosEntidades.hbs",model.getModel());
    }

    public void entidad(Context context) {
        try {
            ServicioGeoref servicioGeoref = ServicioGeoref.getInstancia();
            List<Provincia> lista = servicioGeoref.listadoProvincias().provincias;
            ModelBase model = new ModelBase(getRolUsuarioFromSession(context),getNombreFromSession(context), getIdUsuarioFromSession(context));
            int idEntidad = Integer.parseInt(context.pathParam("id_entidad"));
            Entidad entidad = (Entidad) new RepositorioDeEntidades().buscarPorId(idEntidad);

            List<String> provincias = lista.stream().map(provincia -> provincia.getNombre()).toList();

            Collections.sort(lista, Comparator.comparing(Provincia::getNombre));
            //TODO ordenar alfabeticamente
            model.put("Entidad", entidad);
            model.put("provincias", lista);
            context.render("/entidad.hbs", model.getModel());
        }
        catch (Exception e) {
            context.result(e.getMessage());
        }
    }
}
