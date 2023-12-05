package Validador;

public class ValidacionMinuscula implements Validable {
    public String descripcion = "La contraseña debe poseer al menos una minuscula";
    @Override
    public boolean cumpleValidacion(String passAValidar) {
        return passAValidar.matches("(.*[a-z]+.*)");
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}
