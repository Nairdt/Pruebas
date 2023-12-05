package Validador;


public class ValidacionLongitud implements Validable{
    private int minimoCaracteres = 8;

    public String descripcion = "La contraseña debe tener ";
    @Override
    public boolean cumpleValidacion(String passAValidar) {
        return passAValidar.length() > minimoCaracteres;
    }

    @Override
    public String getDescripcion() {
        return descripcion + minimoCaracteres + " caracteres";
    }
}
