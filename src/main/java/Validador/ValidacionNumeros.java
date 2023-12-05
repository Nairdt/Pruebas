package Validador;

public class ValidacionNumeros implements Validable {
    public String descripcion = "La contraseña debe poseer al menos un numero";
    @Override
    public boolean cumpleValidacion(String passAValidar) {
        return passAValidar.matches("(.*[0-9]+.*)");
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}
