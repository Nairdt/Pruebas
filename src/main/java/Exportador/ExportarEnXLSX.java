package Exportador;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ExportarEnXLSX implements RankingExportable{
    private XSSFWorkbook workbook;
    private String nombreDeArchivo;

    public ExportarEnXLSX(String nombreDelArchivo) {
        this.nombreDeArchivo = nombreDelArchivo;
    }

    @Override
    public void exportarRanking(List<String> listadoInfo) {
        crearLibroDeTrabajo();
        XSSFSheet hoja = crearHoja( "Hoja1");
        agregarDatos(hoja, listadoInfo);
        guardar();
    }

    private String rutaCompletaDelArchivo(){
        return this.nombreDeArchivo;
    }

    private void guardar(){
        try {
            FileOutputStream outputStream = new FileOutputStream(rutaCompletaDelArchivo());
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void crearLibroDeTrabajo(){
        this.workbook = new XSSFWorkbook();
    }

    private XSSFSheet crearHoja(String nombre){
        return workbook.createSheet(nombre);
    }

    private void agregarDatos(XSSFSheet hoja, List<String> datos){
        int numeroDeFila = 0;

        for (int i = 0; i < datos.size(); i++) {
            String entidad = datos.get(i);
            String[] elementos = entidad.split(" - ");
            List<String> valores = Arrays.asList(elementos);

            Row fila = crearFila(hoja, numeroDeFila);
            numeroDeFila++;
            agregarColumna(valores, fila, hoja);
        }
    }
    private Row crearFila(XSSFSheet hoja, int numeroDeFila){
        return hoja.createRow(numeroDeFila);
    }

    private void agregarColumna(List<String> valores, Row fila, XSSFSheet hoja){
        int numeroDeColumna = 0;
        for (String v : valores) {
            Cell celda = fila.createCell(numeroDeColumna);
            numeroDeColumna++;
            celda.setCellValue(v);
        }
    }
}
