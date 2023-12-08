package services.georef.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Embeddable
@Getter
@Setter
public class Departamento{

    @Column(name = "id_departamento")
    protected int id;
    @OneToOne
    @JoinColumn(name = "id_centroide_departamento", referencedColumnName = "id_centroide")
    protected Centroide centroide;
    @Column(name = "nombre_departamento")
    protected String nombre;
    @Transient
    public Provincia provincia;
    public Departamento(int unId, Provincia provincia, Centroide unCentroide, String unNombre) {
        this.id = unId;
        this.provincia = provincia;
        this.centroide = unCentroide;
        this.nombre = unNombre;
    }

    public Departamento() {

    }
}
