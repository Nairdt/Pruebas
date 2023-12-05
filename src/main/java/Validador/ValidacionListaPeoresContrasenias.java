package Validador;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter

public class ValidacionListaPeoresContrasenias implements Validable {
    private String rutaArchivo;

    private static List<String> peoresContrasenias;

    public ValidacionListaPeoresContrasenias() throws IOException {
            this.peoresContrasenias = obtenerListaPeoresContrasenias();
    }

    @Override
    public boolean cumpleValidacion(String passAValidar) {
        return !this.peoresContrasenias.contains(passAValidar);
    }

    @Override
    public String getDescripcion() {
        return null;
    }

    private List<String> obtenerListaPeoresContrasenias() throws IOException {
        BufferedReader lectorPorLinea = new BufferedReader(new FileReader("Files/top10000peoresContrasenias.txt"));
        List<String> listadoContrasenias = new ArrayList<>();
        String contrasenia;

        while ((contrasenia = lectorPorLinea.readLine()) != null) {
            listadoContrasenias.add(contrasenia);
        }

        return listadoContrasenias;
    }
}
