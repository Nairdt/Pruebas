package Validador;

public class ValidacionCaracteresNoIguales {
    public boolean cumpleValidacion(String passAValidar) {
        char primerCaracter = passAValidar.charAt(0);
        //TODO IMPLEMENTAR CON ANY (Que alguno sea distinto) -- no se puede porque no es una lista
        for(int i=1; i < passAValidar.length(); i++) {
            if(passAValidar.charAt(i) != primerCaracter) {
                return true;
            }
        }
        return false;
    }
}
