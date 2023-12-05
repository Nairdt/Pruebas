import Comunidad.Comunidad;
import Comunidad.Incidente;
import DTOs.RankingExportableDTO;
import Exportador.EnumerableTipoExportador;
import GeneradorRankings.RankingEntidadesConMayorCantIncidentes;
import Notificador.FechaHora;
import Organizaciones.Entidad;
import Organizaciones.Establecimiento;
import Servicios.Servicio;
import Servicios.ServicioCompuesto;
import Servicios.ServicioPorEstablecimiento;
import Servicios.TipoServicio;
import org.junit.Before;
import org.junit.Test;

import Exportador.RankingExportable;
import GeneradorRankings.RankingGenerable;
import services.georef.entities.Centroide;
import services.georef.entities.Localizacion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
/*
public class TestExportacion {
    RankingExportable exportadorRanking;
    List<Entidad> listadoEntidades;

    @Before
    public void init() {
        listadoEntidades = new ArrayList<>();
        Set<Incidente> listadoIncidentes = new HashSet<>();

        Comunidad comunidad = new Comunidad("Colaboracion","Problematica");
        Localizacion localizacion = new Localizacion(
                new Centroide(1,1),1);
        Establecimiento establecimiento = new Establecimiento(localizacion);
        Servicio servicio =                 new Servicio("Un servicio",
                new TipoServicio("Un tipo")
        );
        ServicioPorEstablecimiento servicioPorEstablecimiento = new ServicioPorEstablecimiento("Primer Servicio", establecimiento,
                true, servicio

        );

        Incidente unIncidente = new Incidente(comunidad,
                "Observacion", servicioPorEstablecimiento,
                new ServicioCompuesto("Servicio compuesto"),
                new FechaHora(LocalDate.now(), LocalTime.now()),
                false
        );

        unIncidente.resolverIncidente();
        Entidad entidadConIncidente = new Entidad("Entidad1");
        entidadConIncidente.addIncidente(unIncidente);
        listadoEntidades.add(entidadConIncidente);
        listadoEntidades.add(new Entidad("Entidad2"));
        listadoEntidades.add(new Entidad("Entidad3"));

    }

    @Test
    public void generarRankingMayorCantidadIncidentes() {
        RankingExportableDTO DTOEntidades = new RankingExportableDTO();
        DTOEntidades.setearListadoEntidades(listadoEntidades);
        RankingGenerable ranking = new RankingEntidadesConMayorCantIncidentes();
        ranking.generarRanking(EnumerableTipoExportador.CSV,"TestRanking.CSV");
    }
}
*/