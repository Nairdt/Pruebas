package Notificador;

import org.quartz.*;

import java.io.IOException;

public class Notificador {
    private LapsoNotificable lapso;

    public Notificador(){
        this.lapso = lapso;
    }
    //TODO Notificador va NoBorrar tener una recepcion de notificaciones y va NoBorrar evaluar, cada vez que le llegue una, segun el tipo si se guada para el fin de semana
    //  o se notifica directamente
    public void notificar(String destinatario) throws SchedulerException, IOException {
        this.lapso.notificarPorLapso(destinatario);
    }
}
