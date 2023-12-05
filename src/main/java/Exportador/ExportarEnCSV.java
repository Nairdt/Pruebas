package Exportador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportarEnCSV implements RankingExportable{
    private String nombreArchivo;
    public ExportarEnCSV(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    @Override
    public void exportarRanking(List<String> listadoInfo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (String linea : listadoInfo) {
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            // Handle any exceptions
            e.printStackTrace();
        }
    }
}
