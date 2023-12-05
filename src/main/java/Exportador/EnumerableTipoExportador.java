package Exportador;

public enum EnumerableTipoExportador implements GeneradorExportable {
    CSV {
        @Override
        public RankingExportable obtenerExportable(String rutaArchivo) {
            return new ExportarEnCSV(rutaArchivo);
        }
    },
    XLSX {
        @Override
        public RankingExportable obtenerExportable(String rutaArchivo) { return new ExportarEnXLSX(rutaArchivo); }
    },
    PDF {
        @Override
        public RankingExportable obtenerExportable(String rutaArchivo) { return new ExportarEnPDF(rutaArchivo); }
    }
}