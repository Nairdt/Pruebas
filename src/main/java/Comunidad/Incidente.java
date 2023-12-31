package Comunidad;

import Notificador.FechaHora;
import Organizaciones.Entidad;
import Servicios.Servicio;
import Servicios.ServicioCompuesto;
import Servicios.ServicioPorEstablecimiento;
import lombok.Getter;
import lombok.Setter;
import java.io.IOException;
import java.time.*;
import java.util.Date;

import static java.lang.Thread.sleep;
import javax.persistence.*;
import java.io.IOException;

import static java.lang.Thread.sleep;
@Getter
@Setter
@Entity
@Table(name = "incidente")
public class Incidente {
    @Id
    @GeneratedValue
    @Column(name = "id_incidente")
    private int id_incidente;
    @ManyToOne
    @JoinColumn(name = "id_comunidad",referencedColumnName = "id_comunidad")
    private Comunidad comunidad;
    @Column(name = "observaciones",length = 200)
    private String observaciones;
    @Column(name = "resuelto")
    private Boolean resuelto;
    @OneToOne
    @JoinColumn(name = "id_servicio",referencedColumnName = "id_servicio")
    private Servicio servicioAfectado;
    @OneToOne
    @JoinColumn(name = "id_servicio_por_establecimiento", referencedColumnName = "id_servicio_por_establecimiento")//TODO ver si es servicioporestablecimiento o cambiarlo NoBorrar Servicio
    private ServicioPorEstablecimiento servicioBase;
    @OneToOne //converter
    @JoinColumn(name = "id_fecha_hora_fin", referencedColumnName = "id_fecha_hora")
    private FechaHora timestampFin;
    @OneToOne
    @JoinColumn(name = "id_fecha_hora_inicio", referencedColumnName = "id_fecha_hora")
    private FechaHora fechaHora;
    @Column(name = "reportador")
    private String usuarioQueReporto;
    @Column(name = "clausurador")
    private String usuarioQueCerro;

    @ManyToOne
    @JoinColumn(name = "id_entidad", referencedColumnName = "id_entidad")
    private Entidad entidad;

    public Incidente(Comunidad comunidad, String observaciones, ServicioPorEstablecimiento servicioBase, String usuarioQueReporto, FechaHora fechaHora, Boolean resuelto) {
        this.comunidad = comunidad;
        this.observaciones = observaciones;
        this.servicioBase = servicioBase;
        this.fechaHora = fechaHora;
        this.resuelto = resuelto;
        this.usuarioQueReporto = usuarioQueReporto;
    }

    public Incidente() {

    }


    public static void main(String[] args) throws IOException, InterruptedException {
        IncidenteTimer timerHilo = new IncidenteTimer();
        timerHilo.start();
        sleep(15000);
        IncidenteTimer timerHilo2 = new IncidenteTimer();
        timerHilo2.start();
    }

    public void resolverIncidente(String usuarioQueCerro){
        this.resuelto = true;
        this.timestampFin = new FechaHora(LocalDate.now(),LocalTime.now());
        this.usuarioQueCerro = usuarioQueCerro;
    }

    public long horasDiferenciaEntreAperturaYCierre() {
        LocalDateTime fechaHoraInicio = fechaHora.getFecha().atTime(fechaHora.getHora());
        LocalDateTime fechaHoraFin;
        if(timestampFin!=null)
            fechaHoraFin = timestampFin.getFecha().atTime(timestampFin.getHora());
        else
            fechaHoraFin = LocalDateTime.now();
        Duration duracion = Duration.between(fechaHoraInicio,fechaHoraFin);
        return duracion.toHours();
    }
    public Boolean resuelto(){
        return resuelto;
    }

    public String reporteIncidente() {
        return "";
    }
}
