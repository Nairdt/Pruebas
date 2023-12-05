package Exportador;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class ExportarEnPDF implements RankingExportable {

    private String nombreDeArchivo;
    public ExportarEnPDF(String nombreDeArchivo) {
        this.nombreDeArchivo = nombreDeArchivo;
    }
    @Override
    public void exportarRanking(List<String> listadoEntidades) {
        generarPDF(listadoEntidades);
    }

   /* private void generarPDF(List<String> listadoInfo) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);

                // Agregar el contenido del ranking al documento
                for (int i = 0; i < listadoInfo.size(); i++) {

                    String contenido = (i + 1) + ". " + listadoInfo.get(i);

                    contentStream.showText(contenido);
                    contentStream.newLine();
                }

                contentStream.endText();
                document.save(nombreDeArchivo);
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void generarPDF(List<String> listadoInfo) {
        PDDocument doc = new PDDocument();
        PDPage myPage = new PDPage();
        doc.addPage(myPage);
        try {
            PDPageContentStream cont = new PDPageContentStream(doc, myPage);
            cont.beginText();
            cont.setFont(PDType1Font.COURIER_BOLD, 12);
            cont.setLeading(14.5f);
            cont.newLineAtOffset(25, 700);
            agregarDatos(cont, listadoInfo);

            cont.endText();
            cont.close();
            doc.save(rutaCompletaDelArchivo());
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String rutaCompletaDelArchivo(){
        return this.nombreDeArchivo;
    }

    private void agregarDatos(PDPageContentStream pagina, List<String> listadoInfo) throws IOException {
        for (int i = 0; i < listadoInfo.size(); i++) {
            pagina.newLine();
            String datosDeLaFila = (i + 1) + ". " + listadoInfo.get(i);
            pagina.showText(datosDeLaFila);
        }
    }
}

