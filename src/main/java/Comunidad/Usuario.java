package Comunidad;

import Notificador.LapsoNotificable;
import Notificador.NotificarCuandoSuceden;
import Organizaciones.Establecimiento;
import Servicios.ServicioPorEstablecimiento;
import client.models.entities.usuarios.Rol;
import dbManager.RolUsuarioConverter;
import lombok.Getter;
import lombok.Setter;
import services.georef.entities.Localizacion;

import java.io.IOException;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue
    @Column(name = "id_usuario")
    private int id_usuario;
    @Column(name = "clave")
    private String clave;
    @Column(name = "nombre")
    private String nombre;
    @OneToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;
    @OneToOne
    @JoinColumn(name = "id_localizacion",referencedColumnName = "id_localizacion")
    //TODO Ver la migracion de localizacion NoBorrar localizaciones
    private Localizacion localizacionInteres;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "lapso",referencedColumnName = "id_lapso_notificable")
    private LapsoNotificable lapso;
    @OneToMany(mappedBy = "usuario",cascade = CascadeType.ALL)
    private List<Miembro> membresias = new ArrayList<Miembro>();
    @Column(name = "mail")
    private String mail;
    //private List<Establecimiento> listaEstablecimientos;
    @Column(name = "telefono")
    private String telefono;

    public Usuario(String nombre, String clave, Rol rol, MedioReceptor medio, LapsoReceptor enumLapso, Localizacion unaLocalizacion, String mail){
        this.nombre = nombre;
        this.clave = clave;
        this.rol = rol;
        this.localizacionInteres = unaLocalizacion;
        if(enumLapso != null) this.lapso = enumLapso.notificar();
        this.mail = mail;
        this.membresias = new ArrayList<>();
    }

    public Usuario(String nombre, String clave, String mail, Rol rol, String telefono){
        this.nombre = nombre;
        this.clave = clave;
        this.rol = rol;
        this.mail = mail;
        this.telefono = telefono;
        this.membresias = new ArrayList<>();
        this.lapso = null;
    }

    public Usuario() {
        this.membresias = new ArrayList<>();
    }


    //TODO Servicios cercanos de un usuario
    /*
    public List<ServicioPorEstablecimiento> serviciosCercanos(){
        List<ServicioPorEstablecimiento> servicios = new ArrayList<ServicioPorEstablecimiento>();

        Recorrer todos los establecimientos y agregar los servicios disponibles que tenga, si su localizacion coincide con la del usuario

        return servicios;
    }*/
    public List<ServicioPorEstablecimiento> serviciosCercanos(List<Establecimiento> listaEstablecimientos){
        List<ServicioPorEstablecimiento> servicios = new ArrayList<ServicioPorEstablecimiento>();
        for(Establecimiento unEstablecimiento : this.establecimientosCercanos(listaEstablecimientos)){
            servicios.addAll(unEstablecimiento.getServiciosDisponibles());
        }




        // Recorrer todos los establecimientos y agregar los servicios disponibles que tenga, si su localizacion coincide con la del usuario

        return servicios;
    }




    //Establecimientos cercanos
    public List<Establecimiento> establecimientosCercanos(List<Establecimiento> listaEstablecimientos){
        List<Establecimiento> establecimientos = new ArrayList<Establecimiento>();
        for(Establecimiento unEstablecimiento : listaEstablecimientos){
            if(unEstablecimiento.getLocalizacion().getId() == this.localizacionInteres.getId()){
                establecimientos.add(unEstablecimiento);
            }
        }
        // Recorrer todos los establecimientos y agregar los servicios disponibles que tenga, si su localizacion coincide con la del usuario

        return establecimientos;
    }

    public void notificar(String destino) throws IOException {
        this.lapso = this.membresias.get(0).getLapso().notificar();
        this.lapso.notificarPorLapso(destino);
    }

    public void agregarMembresia(Miembro miembro){
        this.membresias.add(miembro);
    }


    public void removerMembresia(Miembro miembro) {
        this.membresias.remove(miembro);
    }
}
