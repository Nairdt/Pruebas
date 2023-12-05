package Notificador;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;

public class NotificacionesCalendarizadas implements Job {

    private MedioNotificable medio;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        String telefono = context.getJobDetail().getJobDataMap().getString("destinatario");
        String mensaje = context.getJobDetail().getJobDataMap().getString("mensaje");
        //MedioNotificable medio = context.getJobDetail().getJobDataMap().getClass("medio");
        try {
            this.medio.notificarPorMedio(telefono, mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
