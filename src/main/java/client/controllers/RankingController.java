package client.controllers;

import Comunidad.Comunidad;
import Comunidad.Incidente;
import GeneradorRankings.EntidadRanking;
import Organizaciones.Entidad;
import Validador.Validador;
import client.models.ModelBase;
import client.models.repositories.RepositorioDeEntidades;
import client.models.repositories.RepositorioDeUsuarios;
import client.server.utils.ICrudViewsHandler;
import dbManager.EntityManagerHelper;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import services.generadorDeRanking.ServicioGeneradorDeRanking;
import services.generadorDeRanking.entities.RequestEntidadDTO;
import services.generadorDeRanking.entities.ResponseEntidadDTO;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static client.helpers.ControllerHelpers.generarMapModelBase;

public class RankingController extends EntityManagerHelper {
    RepositorioDeEntidades repositorioDeEntidades;
    private static final int CNF = 1;

    public RankingController(RepositorioDeEntidades repositorioDeEntidades){
        this.repositorioDeEntidades = repositorioDeEntidades;
    }

    public void exportarRanking(Context context){



        context.redirect("/incidentes/ranking");
    }

    public void generarRankingEntidades(Context context){
        ServicioGeneradorDeRanking generadorDeRanking = ServicioGeneradorDeRanking.getInstancia();
        try {
            generadorDeRanking.cambiarCnfRanking(CNF);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Entidad> entidades = repositorioDeEntidades.buscarTodos();
        List<RequestEntidadDTO> requestRanking = new ArrayList<>();

        entidades.forEach(entidad -> {
            //int id, int sumatoria, int cantidadIncidentes, int cantidadMiembros
            int afectados = cantidadMiembrosAfectados(entidad);
            requestRanking.add(new RequestEntidadDTO(entidad.getId_entidad(), entidad.getSumatoriaCierreIncidentes(), entidad.getCantidadIncidentes(), afectados));
        });

        List<ResponseEntidadDTO> responseRanking = new ArrayList<>();
        List<EntidadRanking> listadoEntidadesRanking = new ArrayList<>();
        try {
            responseRanking = generadorDeRanking.generarRankingEntidades(requestRanking);
            responseRanking.forEach(x ->{
                listadoEntidadesRanking.add(
                  new EntidadRanking(
                          x.idEntidad,
                          ((Entidad)(entidades.stream().filter(entidad -> entidad.getId_entidad() == x.idEntidad)).findFirst().orElse(new Entidad())).getNombre(),
                          x.puestoRanking,
                          x.nivelImpactoEntidad
                  )
                );
                    });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listadoEntidadesRanking.sort(Comparator.comparingInt(EntidadRanking::getPuestoRanking));
        ModelBase model = new ModelBase(generarMapModelBase(context));
        model.put("entidades", listadoEntidadesRanking);
        context.render("rankingIncidentes.hbs",model.getModel());

    }

    private int cantidadMiembrosAfectados(Entidad entidad){
        Set<Incidente> incidentes = entidad.getIncidentes();
        List<Comunidad> comunidades = new ArrayList<Comunidad>();
        int afectados = 0;
        for (Incidente incidente : incidentes) {
            if(comunidades.stream().allMatch(comunidad -> comunidad.getId_comunidad() != incidente.getComunidad().getId_comunidad())){
                afectados += incidente.getComunidad().getMiembros().size();
                comunidades.add(incidente.getComunidad());
            }
        }
        return afectados;
    }
}
