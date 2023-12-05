package Servicios;

import Organizaciones.Establecimiento;
import lombok.Getter;
import lombok.Setter;
import services.georef.entities.Localizacion;

import javax.persistence.*;

@Entity
@Table(name = "servicio_por_establecimiento", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_establecimiento", "id_servicio"})})
@Getter
@Setter
@Cacheable(value = false)
public class ServicioPorEstablecimiento{
    @Id
    @GeneratedValue
    @Column(name = "id_servicio_por_establecimiento")
    private int id_servicio_por_establecimiento;
    //@EmbeddedId
    //ServicioPorEstablecimientoKey id;//todo id autogenerado, no embebido
    @ManyToOne
    @JoinColumn(name = "id_establecimiento")
    private Establecimiento establecimiento ;
    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Servicio servicio;
    @Column(name = "habilitado")
    private Boolean habilitado;
    @Column(name = "problematica",length = 50)
    private String problematica;
    @Column(name = "nombre",length = 50)
    private String nombre;

    @Transient
    private Tramo tramo;
    private boolean conIncidente;

    public ServicioPorEstablecimiento(String nombre, Establecimiento establecimiento,Boolean habilitado, Servicio servicio){
        this.nombre = nombre;
        this.establecimiento = establecimiento;
        this.habilitado = habilitado;
        this.servicio = servicio;
    }

    public ServicioPorEstablecimiento(String nombre, Establecimiento establecimiento, String problematica, Boolean habilitado, Servicio servicio){
        this.nombre = nombre;
        this.establecimiento = establecimiento;
        this.habilitado = habilitado;
        this.servicio = servicio;
        this.problematica = problematica;
    }

    public ServicioPorEstablecimiento() {

    }

    public void cambiarEstado(){
        this.habilitado = !habilitado;
    }

    public Localizacion localizacionServicio(){
        return this.establecimiento.getLocalizacion();
    }

    public boolean getActualmenteConIncidente() {
        return conIncidente;
    }
}
