package Organizaciones;
import java.util.ArrayList;
import java.util.List;

import ServiciosPublicos.Entrada;
import Servicios.ServicioPorEstablecimiento;
import lombok.Getter;
import lombok.Setter;
import services.georef.entities.Localizacion;

import javax.persistence.*;
@Getter
@Setter
@Entity
@Cacheable(value = false)
@Table(name = "establecimiento")
public class Establecimiento {
    @Id
    @GeneratedValue
    @Column(name = "id_establecimiento")
    private int id_establecimiento;
    @Transient
    private List<Entrada> entradas;
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_entidad", referencedColumnName = "id_entidad")
    private Entidad entidad;

    @Getter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "localizacion_id", referencedColumnName = "id_localizacion")
    private Localizacion localizacion;
    @Column(name = "nombre")
    String nombre;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id_establecimiento",referencedColumnName = "id_establecimiento")
    @Getter
    private List<ServicioPorEstablecimiento> serviciosDisponibles;
    @Transient
    private List<ServicioPorEstablecimiento> serviciosFaltantes;

    public Establecimiento(String nombre, Localizacion unaLocalizacion){
        this.nombre = nombre;
        this.localizacion = unaLocalizacion;
        this.serviciosDisponibles = new ArrayList<>();
    }

    public Establecimiento() {
        this.serviciosDisponibles = new ArrayList<>();
    }

    public void eliminarServicio(ServicioPorEstablecimiento unServicio){
        serviciosDisponibles.remove(unServicio);
    }

    public void agregarServiciosDisponibles(ServicioPorEstablecimiento ... servicios){
        serviciosDisponibles.addAll(List.of(servicios));
    }


}
