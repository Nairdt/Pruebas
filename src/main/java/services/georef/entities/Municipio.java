package services.georef.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class Municipio{
    @Column(name = "id_municipio")
    protected int id;
    @OneToOne
    @JoinColumn(name = "id_centroide_municipio", referencedColumnName = "id_centroide")
    protected Centroide centroide;
    @Column(name = "nombre_municipio")
    protected String nombre;
    @Transient
    private Provincia provincia;

    public Municipio(int unId, Provincia provincia, Centroide unCentroide, String unNombre) {
        this.id = unId;
        this.provincia = provincia;
        this.centroide = unCentroide;
        this.nombre = unNombre;
    }

    public Municipio() {

    }
}
