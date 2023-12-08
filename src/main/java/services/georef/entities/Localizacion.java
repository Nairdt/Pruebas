package services.georef.entities;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "localizacion")
@Getter
 public class Localizacion {
    @Id
    @GeneratedValue
    @Column(name = "id_localizacion")
    protected int id;
    @OneToOne
    @JoinColumn(name = "id_centroide_localizacion", referencedColumnName = "id_centroide")
    protected Centroide centroide;
    @Column(name = "nombre")
    protected String nombre;
    @Embedded
    protected Departamento nombreDepartamento;
    @Embedded
    protected Municipio nombreMunicipio;
    @Embedded
    protected Provincia nombreProvincia;
    @Embedded
    protected Localidad nombreLocalidad;
    /*
    @Column(name = "provincia")
    private String provincia;
    @Column(name = "localidad")
    private String localidad;
    @Column(name = "municipio")
    private String municipio;
    @Column(name = "departamento")
    private String departamento;
*/
   public Localizacion(Centroide centroide, int id) {
      this.centroide = centroide;
      this.id = id;
   }
/*
    public Localizacion(String provincia, String localidad, String municipio, String departamento) {
        this.provincia = provincia;
        this.localidad = localidad;
        this.municipio = municipio;
        this.departamento = departamento;
    }
*/

    public Localizacion(){

    }
    public Localizacion(Provincia provincia, Departamento departamento, Localidad localidadS, Municipio municipio){
       nombreProvincia = provincia;
       nombreMunicipio = municipio;
       nombreDepartamento = departamento;
       nombreLocalidad = localidadS;
    }
}
