package Comunidad;

public class IncidenteTimer extends Thread{
    private int contador = 0;
    @Override
    public void run(){
        while (true) {
            System.out.println("Prueba " + contador);
            contador++;
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
