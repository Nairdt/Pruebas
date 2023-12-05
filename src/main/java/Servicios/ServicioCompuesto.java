package Servicios;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
public class ServicioCompuesto {
    @Id
    @GeneratedValue
    @Column(name = "id_compuesto")
    private String id_compuesto;
    @Column(name = "nomrbe")
    private String nombre;
    @OneToMany(mappedBy = "servicioCompuesto")
    private List<Servicio> servicios;

    public ServicioCompuesto(String nombre){
        this.nombre = nombre;
        this.servicios = new ArrayList<>();
    }

    public ServicioCompuesto() {

    }

    public void agregarServicio(ServicioPorEstablecimiento servicioPorEstablecimiento){
        //this.servicios.add(servicioPorEstablecimiento);
    }

}
