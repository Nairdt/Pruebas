package Notificador;

import java.io.IOException;

public interface MedioNotificable {

    public void notificarPorMedio(String destinatario, String mensaje) throws IOException;
}
