package services.georef.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class Provincia{
    @Column(name = "id_provincia")
    protected int id;
    @OneToOne
    @JoinColumn(name = "id_centroide_provincia", referencedColumnName = "id_centroide")
    protected Centroide centroide;
    @Column(name = "nombre_provincia")
    protected String nombre;
    public Provincia(int unId, Centroide unCentroide, String unNombre) {
        this.id = unId;
        this.centroide = unCentroide;
        this.nombre = unNombre;
    }

    public Provincia() {

    }
}
