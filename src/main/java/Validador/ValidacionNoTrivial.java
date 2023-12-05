package Validador;

import java.util.ArrayList;
import java.util.List;

public class ValidacionNoTrivial implements Validable{
    private List<String> secuencias = new ArrayList<String>();

    public void agregarSecuencia(String secuencia){
        this.secuencias.add(secuencia);
    }

    @Override
    public boolean cumpleValidacion(String passAValidar) {
        return secuenciasNoTriviales(passAValidar) && caracteresNoIguales(passAValidar);
    }

    @Override
    public String getDescripcion() {
        return null;
    }

    protected boolean secuenciasNoTriviales(String passAValidar) {
        boolean valida = true;

        for(String s : secuencias) {
            if(s.contains(passAValidar)) {
                valida = false;
            }
        }

        //TODO
        //Implementar con any

        return valida;
    }

    protected boolean caracteresNoIguales(String passAValidar) {
        boolean valida = false;
        char primerCaracter = passAValidar.charAt(0);

        for(int i=1; i < passAValidar.length(); i++) {
            if(passAValidar.charAt(i) != primerCaracter) {
                valida = true;
            }
        }
        return valida;
    }
}
