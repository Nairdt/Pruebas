package GeneradorRankings;

import Comunidad.Incidente;
import Exportador.EnumerableTipoExportador;
import Exportador.GeneradorExportables;
import Exportador.RankingExportable;
import java.util.ArrayList;
import java.util.List;

public class RankingImpactoProblematica implements RankingGenerable{

    @Override
    public void generarRanking(EnumerableTipoExportador tipoExportable, String rutaArchivo) {

        //TODO Ver como seria la generacion de este ranking en la prox entrega
        List<Incidente> listadoIncidentes = new ArrayList<>();

        RankingExportable exportador = new GeneradorExportables().obtenerExportable(tipoExportable,rutaArchivo);

    }
}
