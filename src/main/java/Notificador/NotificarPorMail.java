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

        SendGrid sg = new SendGrid(ConfigTwilio.KEY_SENDGRID);
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
