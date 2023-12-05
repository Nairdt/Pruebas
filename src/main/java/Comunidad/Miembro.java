package Comunidad;

import Notificador.*;
import Organizaciones.Establecimiento;
import Servicios.ServicioCompuesto;
import Servicios.ServicioPorEstablecimiento;
import dbManager.LapsoReceptorConverter;
import dbManager.MedioReceptorConverter;
import dbManager.RolUsuarioConverter;
import lombok.Getter;
import lombok.Setter;
import org.quartz.SchedulerException;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "miembro")
public class Miembro {
    @Id
    @GeneratedValue
    @Column(name = "id_miembro")
    private int id_miembro;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "correo")
    private String correo;
    @ManyToOne(cascade = CascadeType.ALL) //TODO Ver si es mejor que sea al reves, que el cascadetype sea desde Usuario
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @Column(name = "telefono")
    private String telefono;
    @Transient
    private List<Interes> intereses;
    @Convert(converter = MedioReceptorConverter.class)
    @Column(length = 20)
    private MedioReceptor medio;
    @Convert(converter = LapsoReceptorConverter.class)
    @Column(length = 20)
    private LapsoReceptor lapso;
    @Transient
    private NotificarCuandoSuceden notificarCuandoSuceden;
    @ManyToOne
    @JoinColumn(name = "comunidad", referencedColumnName = "id_comunidad")
    private Comunidad comunidad;


    public Miembro(String nombre, String apellido, String correo, Usuario usuario, String telefono, Comunidad unaComunidad){
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.usuario = usuario;
        this.telefono = telefono;
        this.intereses = new ArrayList<Interes>();
        this.medio = medio;
        this.comunidad = unaComunidad;
    }
    //TODO ELIMINAR SERVICIOS COMPUESTOS
    public void notificarIncidente(String descripcion, TipoNotificacion tipo, ServicioPorEstablecimiento servicio, ServicioCompuesto compuesto){
        FechaHora fechaHora = new FechaHora(LocalDate.now(), LocalTime.now());
        //TODO NO MEZCLAR USO CON CREACION
        NotificacionIncidente notificacionIncidente = new NotificacionIncidente(this, descripcion, tipo, fechaHora, servicio, compuesto);

        this.comunidad.reportarIncidente(notificacionIncidente);
    }

    public void serNotificado(NotificacionIncidente notificacion) throws SchedulerException, IOException {
        String destino = elegirDestinatario();

        usuario.notificar(destino);
    }

    public Miembro() {

    }

    public void notificarCercania(List<Establecimiento> listaEstablecimiento) throws SchedulerException, IOException {
        List<ServicioPorEstablecimiento> servicios = usuario.serviciosCercanos(listaEstablecimiento);
        List<ServicioPorEstablecimiento> serviciosConIncidentes = servicios.stream().filter(servicio -> servicio.getActualmenteConIncidente()).toList();

        if(serviciosConIncidentes.size()!=0){
            for(ServicioPorEstablecimiento unServicio : serviciosConIncidentes){
                FechaHora fechaHora = new FechaHora(LocalDate.now(), LocalTime.now());
                NotificacionIncidente notificacionIncidente = new NotificacionIncidente(this, "Servicio cercano", TipoNotificacion.REVISION_INCIDENTE, fechaHora, unServicio,null);
                notificarCuandoSuceden.setNotificacion(notificacionIncidente);
                //Deberia notificar el incidente cercano como urgente y por mail

                String destinatario = elegirDestinatario();

                notificarCuandoSuceden.notificarPorLapso(destinatario);

            }
        }
    }

    public String elegirDestinatario(){
        String destinatario = "";
        if(medio == MedioReceptor.WHATSAPP){
            destinatario = telefono;
        }
        else{
            destinatario = correo;
        }
        return destinatario;
    }

}
