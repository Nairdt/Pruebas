package Notificador;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class NotificarPorWhatsapp implements MedioNotificable{
    private static final String ACCOUNT_SID = "ACf298ad92277c008e81907979f0ba6e33";
    private static final String AUTH_TOKEN = "8bb973a22252d70f4fa3a60daa36cda3";
    private static final String PHONE_FROM = "+14155238886";

    // TODO
    @Override
    public void notificarPorMedio(String telefono, String mensaje) {
        //sendWhatsapp(telefono, mensaje);
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:+549" + telefono),
                        new com.twilio.type.PhoneNumber("whatsapp:" + PHONE_FROM),
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
