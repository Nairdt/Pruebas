package Notificador;

//import org.quartz.*;
//import org.quartz.impl.StdSchedulerFactory;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Entity
@DiscriminatorValue("Sin Apuros")
public class NotificarSinApuros extends LapsoNotificable {
    @Transient
    private static ArrayList<NotificacionIncidente> notificaciones;
    @Column(name = "dia_semanal")
    private String diaSemanal;
    @Column(name = "horario")
    private String horario;

    @Transient
    private MedioNotificable medio;

    public NotificarSinApuros(){
        if(notificaciones == null){
            notificaciones = new ArrayList<NotificacionIncidente>();
        }
    }

    public void agregarNotificacion(NotificacionIncidente notificacion){
        this.notificaciones.add(notificacion);
    }

    @Override
    public void notificarPorLapso(String destinatario) {
        // Obtén la hora actual para calcular el retraso inicial hasta el próximo lunes a las 6 a.m.
        Calendar ahora = Calendar.getInstance();
        int diaActual = ahora.get(Calendar.DAY_OF_WEEK);
        int horaActual = ahora.get(Calendar.HOUR_OF_DAY);
        int minutosActuales = ahora.get(Calendar.MINUTE);

        // Calcula el retraso inicial en milisegundos hasta el próximo lunes a las 6 a.m.
        int retrasoInicial = ((8 - diaActual) % 7) * 24 * 60 * 60 * 1000; // Calcula días restantes hasta el próximo lunes
        retrasoInicial += ((6 - horaActual) * 60 + (60 - minutosActuales)) * 60 * 1000; // Calcula minutos restantes hasta las 6 a.m.

        // Programa la tarea para ejecutarse cada 7 días (una semana)
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable tarea = () -> {
            try {
                this.medio.notificarPorMedio(destinatario, armarMensaje());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        // Ejecuta la tarea inicialmente con el retraso calculado y luego cada 7 días
        scheduler.scheduleAtFixedRate(tarea, retrasoInicial, 7 * 24 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);
    }


    /*FORMATO DE MENSAJES (ejemplo)
    ---NOTIFICACIONES SEMANALES---

    NOMBRE AUTOR: Nicolas Aparicio
    TELEFONO: 1162917177 (dsp le metemos los prefijos en el notificador twilio)
    FECHA: 7/10/2023
    HORA: 16:30
    TIPO: Incidente informado
    DESCRIPCION:
    ------------------------------

    (asi sucesivamente con todas las demas)
     */

    public String armarMensaje(){
        String mensaje = "---NOTIFICACIONES SEMANALES---\n\n";
        for (NotificacionIncidente notificacion : this.notificaciones) {
            mensaje += notificacion.informeNotificacion() + "------------------------------\n";
        }

        return mensaje;
    }

}
