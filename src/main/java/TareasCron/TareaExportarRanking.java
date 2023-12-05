package TareasCron;

import Exportador.EnumerableTipoExportador;
import GeneradorRankings.RankingEntidadesConMayorCantIncidentes;
import GeneradorRankings.RankingImpactoProblematica;

public class TareaExportarRanking {
    public static void main(String[] args) {
        EnumerableTipoExportador enumerableTipoExportador = EnumerableTipoExportador.CSV;
        String rutaArchivo = "";


        RankingImpactoProblematica rankingImpactoProblematica = new RankingImpactoProblematica();
        rankingImpactoProblematica.generarRanking(enumerableTipoExportador,rutaArchivo);

        RankingEntidadesConMayorCantIncidentes rankingEntidadesConMayorCantIncidentes = new RankingEntidadesConMayorCantIncidentes();
        rankingEntidadesConMayorCantIncidentes.generarRanking(enumerableTipoExportador,rutaArchivo);

    }
}
