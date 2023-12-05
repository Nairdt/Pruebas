package GeneradorRankings;

import Exportador.EnumerableTipoExportador;

public interface RankingGenerable {
    void generarRanking(EnumerableTipoExportador tipoExportable, String rutaTest);
}
