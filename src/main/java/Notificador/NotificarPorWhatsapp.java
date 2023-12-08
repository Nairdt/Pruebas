package Notificador;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class NotificarPorWhatsapp implements MedioNotificable{


    // TODO
    @Override
    public void notificarPorMedio(String telefono, String mensaje) {
        //sendWhatsapp(telefono, mensaje);
        Twilio.init(ConfigTwilio.ACCOUNT_SID, ConfigTwilio.AUTH_TOKEN);

        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:+549" + telefono),
                        new com.twilio.type.PhoneNumber("whatsapp:" + ConfigTwilio.PHONE_FROM),
                        mensaje)
                .create();

        System.out.println("MENSAJE A " + telefono + " - ESTADO: " + message.getStatus() + message.getApiVersion());

    }


    /*
    public void sendWhatsapp(String telefono, String mensaje){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:+549" + telefono),
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                        mensaje)
                .create();

        System.out.println(message.getSid());
    }
*/
}
