package Comunidad;

import Notificador.*;
import Servicios.ServicioCompuesto;

import java.time.LocalDate;
import java.time.LocalTime;

public enum LapsoReceptor implements ILapsoReceptor{
    CUANDO_SUCEDEN {
        public LapsoNotificable notificar() {
            return new NotificarCuandoSuceden(new NotificacionIncidente(new Miembro("Nico", "Aparicio", "nicolas.matias.aparicio@gmail.com", null, "1162917177",null), "No funcan los ñobas de la utn", TipoNotificacion.NO_FUNCIONA_SERVICIO, new FechaHora(LocalDate.now(), LocalTime.now()), null, new ServicioCompuesto("baños")));
            //todo cambiar NoBorrar lapso polimorfico
        }
    },
    SIN_APUROS{
        public LapsoNotificable notificar() {
            return new NotificarSinApuros();
        }
    }
}
