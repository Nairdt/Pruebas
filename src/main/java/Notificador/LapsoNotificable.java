package Notificador;

import org.quartz.SchedulerException;

import javax.persistence.*;
import java.io.IOException;
@Entity(name = "lapso_notificable")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_notificacion",discriminatorType = DiscriminatorType.STRING)
public abstract class LapsoNotificable {
    @Id
    @GeneratedValue
    @Column(name = "id_lapso_notificable")
    private int id_lapso_notificable;

    public abstract void notificarPorLapso(String destinatario) throws IOException;
}
