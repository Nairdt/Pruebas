package Exportador;

public class GeneradorExportables {
    public RankingExportable obtenerExportable(EnumerableTipoExportador tipoExportable,String nombreArchivo) {
        RankingExportable exportador;
        try {
            exportador = tipoExportable.obtenerExportable(nombreArchivo);
        }
        catch(Exception e) {
            exportador = new ExportarEnXLSX(nombreArchivo); //Se defaultea NoBorrar tipo CSV
        }
        return exportador;
    }
}
