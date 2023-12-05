package Validador;

public class ValidacionMayuscula implements Validable {
    public String descripcion = "La contraseña debe al menos una mayuscula";
    @Override
    public boolean cumpleValidacion(String passAValidar) {
        return passAValidar.matches("(.*[A-Z]+.*)");
    }

    @Override
    public String getDescripcion() {return descripcion;};
}
