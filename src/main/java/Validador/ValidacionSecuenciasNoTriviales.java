package Validador;

import java.util.ArrayList;
import java.util.List;

public class ValidacionSecuenciasNoTriviales {
    private List<String> secuencias;

    public void agregarSecuencia(String secuencia) {
        this.secuencias.add(secuencia);
    }

    public ValidacionSecuenciasNoTriviales() {
        secuencias = new ArrayList<String>();
    }

    public boolean cumpleValidacion(String passAValidar) {
        return !secuencias.stream().anyMatch(s -> s.contains(passAValidar));
    }
}