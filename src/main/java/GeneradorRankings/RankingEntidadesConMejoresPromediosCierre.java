package GeneradorRankings;

import Exportador.EnumerableTipoExportador;
import Exportador.GeneradorExportables;
import Exportador.RankingExportable;
import Mappers.MapperEntidad;
import Organizaciones.Entidad;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RankingEntidadesConMejoresPromediosCierre implements RankingGenerable{
    @Override
    public void generarRanking(EnumerableTipoExportador tipoExportable, String rutaArchivo) {
        List<Entidad> listadoEntidades = getEntidades();

        RankingExportable exportador = new GeneradorExportables().obtenerExportable(tipoExportable,rutaArchivo);

        listadoEntidades.sort(Comparator.comparingDouble(Entidad::getPromedioCierreIncidentes));

        List<String> entidadesMapeadas = new MapperEntidad().convertirListadoEntidadesAListadoStrings(listadoEntidades, MapperEntidad.TipoRanking.MEJOR_TIEMPO_CIERRE);

        exportador.exportarRanking(entidadesMapeadas);

    }

    private List<Entidad> getEntidades() {
        List<Entidad> entidades = new ArrayList<>();
        //TODO Agregar logica para obtener las entidades
        return entidades;
    }
}
