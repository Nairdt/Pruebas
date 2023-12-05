package Comunidad;

//prueba de GitDesktop
import Servicios.Servicio;
import Notificador.FechaHora;
import Notificador.NotificacionIncidente;
import Servicios.ServicioCompuesto;
import Servicios.ServicioPorEstablecimiento;
import lombok.Getter;
import lombok.Setter;
import org.quartz.SchedulerException;
import services.georef.entities.Localizacion;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "comunidad")
public class Comunidad {
    @Id
    @GeneratedValue
    @Column(name = "id_comunidad")
    private int id_comunidad;
    @Column(name = "activa")
    private boolean activa;
    @Column(name = "nombre", length = 50)
    private String nombre;
    @Transient //TODO revisar la conexion de administradores en el DER
    private List<Usuario> administradores;
    @Column(name = "colaboracion",length = 50)
    private String colaboracion;
    @OneToMany(mappedBy = "comunidad",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Miembro> miembros;
    @Column(name = "problematica", length = 50)
    private String problematica;
    @ManyToMany
    @JoinTable(name = "servicio_por_comunidad",joinColumns = {@JoinColumn(name = "id_comunidad")},
    inverseJoinColumns = {@JoinColumn(name = "id_servicio")})
    private List<Servicio> listaServicio;
    @Transient
    //@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ServicioPorEstablecimiento> serviciosInteresados;
    @OneToMany
    private List<Incidente> incidentes;

    public int cantidadMiembros() {
        return miembros.size();
    }

    public Comunidad(String colaboracion, String problematica){

            this.administradores = new ArrayList<Usuario>();
            this.colaboracion = colaboracion;
            this.miembros = new ArrayList<Miembro>();
            this.problematica = problematica;
            this.serviciosInteresados = new ArrayList<ServicioPorEstablecimiento>();
        }
    public void agregarMiembro(Miembro miembro){
        this.miembros.add(miembro);
    }
    public Comunidad() {

    }

        public void agregarMiembros(Miembro ... miembros){
        Collections.addAll(this.miembros, miembros);
        }

    public List<Miembro> usuarioDentroDeLozalizacion(Localizacion unaLocalizacion){
        List<Miembro> usuariosCercanos = new ArrayList<Miembro>();
        for(Miembro unMiembro : this.miembros){
            if (unaLocalizacion.getId() == unMiembro.getUsuario().getLocalizacionInteres().getId()){
                usuariosCercanos.add(unMiembro);
            }
        }
        return usuariosCercanos;
    }

    public void reportarIncidente(NotificacionIncidente notificacion){
        this.miembros.forEach(miembro -> {
            try {
                miembro.serNotificado(notificacion);

            } catch (SchedulerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public void agregarIncidente(String observaciones, ServicioPorEstablecimiento servicioPorEstablecimiento, ServicioCompuesto servicioCompuesto){
        FechaHora fechaHora = new FechaHora(LocalDate.now(), LocalTime.now());
        incidentes.add(new Incidente(this , observaciones,servicioPorEstablecimiento, servicioCompuesto, fechaHora, false));
    }


    public String generarListadoIncidentes(){
        String listadoString = "";
        for(Incidente unIncidente : this.incidentes){
            listadoString.concat(unIncidente.reporteIncidente());
            listadoString.concat("\n");
        }
        return listadoString;
    }

}
