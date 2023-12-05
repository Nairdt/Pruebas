package Notificador;

import com.sendgrid.*;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;

public class NotificarPorMail implements MedioNotificable{
    //TODO importar directamente desde un .config o txt
    //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
    //private static final String ACCOUNT_SID = "ACf298ad92277c008e81907979f0ba6e33";
    //private static final String AUTH_TOKEN = "8bb973a22252d70f4fa3a60daa36cda3";
    private static final String ACCOUNT_SID = null;
    private static final String AUTH_TOKEN = null;

    public static void main(String[] args) throws IOException {
        NotificarPorMail prueba = new NotificarPorMail();
        prueba.notificarPorMedio("naparicio@frba.utn.edu.ar", "Prueba");
    }

    @Override
    public void notificarPorMedio(String email, String mensaje) throws IOException{
        Email from = new Email("nicolas.matias.aparicio@gmail.com");
        Email to = new Email(email); // use your own email address here

        String subject = "Notificacion incidente";
        mensaje = mensaje.replace("\n","<br>");
        Content content = new Content("text/html", "Te llego el siguiente informe de <strong>INCIDENTES</strong>:<br><br>" +
                mensaje);

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sg.api(request);

        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getBody());
        System.out.println(mensaje);
    }
}
