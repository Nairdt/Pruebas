package services.georef.entities;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "centroide")
@Getter
public class Centroide {
    @Id
    @GeneratedValue
    @Column(name = "id_centroide")
    private int id_centroide;
    @Column(name = "lat")
    private double lat;
    @Column(name = "lon")
    private double lon;
    public Centroide(double latitud, double longitud){
        this.lat = latitud;
        this.lon = longitud;
    }

    public Centroide() {

    }
}
