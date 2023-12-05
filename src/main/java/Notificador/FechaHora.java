package Notificador;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "fecha_hora")
public class FechaHora {
    //TODO LocalDate, LocalDateTime
    @Id
    @GeneratedValue
    @Column(name = "id_fecha_hora")
    private int id_fecha_hora;
    @Column(name = "fecha", columnDefinition = "DATE")
    private LocalDate fecha;
    @Column(name = "hora", columnDefinition = "TIME")
    private LocalTime hora;

    public FechaHora(LocalDate fecha, LocalTime hora){
        this.fecha = fecha;
        this.hora = hora;
    }

    public FechaHora() {

    }
}
