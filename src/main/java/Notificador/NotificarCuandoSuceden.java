package Notificador;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.Setter;
import org.quartz.SchedulerException;

import javax.persistence.*;
import java.io.IOException;
@Setter
@Entity
@DiscriminatorValue("Cuando Suceden")
public class NotificarCuandoSuceden extends LapsoNotificable {
    @Transient
    private NotificacionIncidente notificacion;
    @Transient
    private static final String ACCOUNT_SID = "ACf298ad92277c008e81907979f0ba6e33";
    @Transient
    private static final String AUTH_TOKEN = "8bb973a22252d70f4fa3a60daa36cda3";
    @Transient
    private MedioNotificable medio;

    public NotificarCuandoSuceden(NotificacionIncidente notificacionIncidente){
        this.notificacion = notificacionIncidente;
        this.medio = new NotificarPorMail();//todo arreglar (polimorficamente)
    }

    public NotificarCuandoSuceden() {

    }


    @Override
    public void notificarPorLapso(String destinatario) throws SchedulerException, IOException {
        this.medio.notificarPorMedio(destinatario, armarMensaje());
    }

    public String armarMensaje(){
        String mensaje = "-----NUEVA NOTIFICACION-----\n\n";
        mensaje += this.notificacion.informeNotificacion() + "------------------------------";
        return mensaje;
    }
}
