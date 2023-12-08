package services.georef.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
public class Localidad {

    @Column(name = "id_localidad")
    protected int id;
    @OneToOne
    @JoinColumn(name = "id_centroide_localidad", referencedColumnName = "id_centroide")
    protected Centroide centroide;
    @Transient
    protected Departamento departamento;
    @Transient
    protected Municipio municipio;
    @Column(name = "nombre_localidad",length = 50)
    protected String nombre;
    @Transient
    protected Provincia provincia;


//    protected String nombreDepartamento;
//    protected String nombreMunicipio;
//    protected String nombreProvincia;


}
