package services.georef.entities;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "localizacion")
@Getter
 public class Localizacion {
    @Id
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

   public Localizacion(Centroide centroide, int id) {
      this.centroide = centroide;
      this.id = id;
   }

    public Localizacion() {

    }

    public Localizacion(Provincia provincia, Departamento departamento, Localidad localidadS, Municipio municipio){
       nombreProvincia = provincia;
       nombreMunicipio = municipio;
       nombreDepartamento = departamento;
       nombreLocalidad = localidadS;
    }
}
