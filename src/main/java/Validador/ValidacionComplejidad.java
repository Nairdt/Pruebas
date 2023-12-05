package Validador;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidacionComplejidad implements Validable {
    private boolean requiereMayusculas;
    private boolean requiereMinusculas;
    private boolean requiereNumeros;

    public ValidacionComplejidad(Boolean requiereMayusculas,Boolean requiereMinusculas,Boolean requiereNumeros) {
        this.requiereMayusculas = requiereMayusculas;
        this.requiereMinusculas = requiereMinusculas;
        this.requiereNumeros = requiereNumeros;
    }
    @Override
    public boolean cumpleValidacion(String passAValidar) {
        boolean esValido = true;

        if(requiereMayusculas)
            esValido = esValido && passAValidar.matches("(.+[A-Z]+.*)");
        if(requiereMinusculas)
            esValido = esValido && passAValidar.matches("(.+[a-z]+.*)");
        if(requiereNumeros)
            esValido = esValido && passAValidar.matches("(.+[0-9]+.*)");
        return esValido;
    }

    @Override
    public String getDescripcion() {
        return null;
    }
}
