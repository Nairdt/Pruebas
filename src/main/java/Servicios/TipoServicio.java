package Servicios;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Entity
@Table(name = "tipo_servicio")
public class TipoServicio {
    @Id
    @GeneratedValue
    @Column(name = "id_tipo_servicio")
    private int id_tipo_servicio;
    @Column(name = "nombre")
    private String nombre;
    public TipoServicio(String nombre){
        this.nombre = nombre;
    }

    public TipoServicio() {

    }
}
