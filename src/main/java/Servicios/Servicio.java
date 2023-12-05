package Servicios;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(name = "servicio")
public class Servicio {
    @Id
    @GeneratedValue
    @Column(name = "id_servicio")
    private int id_servicio;
    @Column(name = "nombre",length = 50)
    String nombre;

    @OneToOne
    @JoinColumn(name = "id_tipo_servicio",referencedColumnName = "id_tipo_servicio")
    TipoServicio tipoServicio;

    public Servicio(String nombre, TipoServicio tipoServicio) {
        this.nombre = nombre;
        this.tipoServicio = tipoServicio;
    }

    public Servicio() {

    }
}
