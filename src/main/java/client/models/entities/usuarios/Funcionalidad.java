package client.models.entities.usuarios;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Funcionalidad")
@Setter
@Getter
public class Funcionalidad {
    @Id
    @GeneratedValue
    private int idFuncionalidad;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "nombreInterno")
    private String nombreInterno;

    public boolean coincideConNombreInterno(String nombreInterno) {
        return this.nombreInterno.equals(nombre);
    }
}
