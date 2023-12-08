package Organizaciones;
import Comunidad.Incidente;
import Prestadores.EntidadPrestadoraServicio;
import Prestadores.OrganismoDeControl;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
@Entity
@Table(name = "entidad")
@Getter
@Setter
public class Entidad {
    @Id
    @GeneratedValue
    @Column(name = "id_entidad")
    private int id_entidad;
    @Column(name = "nombre",length = 50)
    String nombre;
    @OneToMany(mappedBy = "entidad", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    List<Establecimiento> establecimientos;
    @OneToMany(mappedBy = "entidad", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Incidente> incidentes;
    @ManyToOne
    @JoinColumn(name = "id_organismo",referencedColumnName = "id_organismo")
    private OrganismoDeControl organismoDeControl;
    @OneToOne
    @JoinColumn(name = "id_prestadora",referencedColumnName = "id_prestadora")
    private EntidadPrestadoraServicio entidadPrestadora;

    public Entidad(String nombre) {
        this.nombre = nombre;
        this.establecimientos = new ArrayList<>();
        this.incidentes = new HashSet<>(); {
        };
    }

    public Entidad() {
        this.establecimientos = new ArrayList<>();
    }

    public void agregarEstablecimiento(Establecimiento establecimiento){
        this.establecimientos.add(establecimiento);
    }

    public void eliminarEstablecimiento(Establecimiento unEstablecimiento){
        establecimientos.remove(unEstablecimiento);
    }

    public double getPromedioCierreIncidentes() {
        //Obtengo la sumatoria de diferencias entre aperturas y cierres, y la divido por la cantidad de incidentes
        long promedio = incidentes.stream()
                .mapToLong(incidente->incidente.horasDiferenciaEntreAperturaYCierre())
                .sum()
                /
                incidentes.size();
        return promedio;//TODO Ver logica para obtener el promedio de cierre de incidentes
    }
    public int getCantidadIncidentesValidosParaRanking() {
        return (int) incidentes
                .stream().filter(incidente->
                        incidente.resuelto() || incidente.horasDiferenciaEntreAperturaYCierre() > 24
                )
                .count();
    }

    public int getCantidadIncidentes() {
        return incidentes.size();
    }
    public int getSumatoriaCierreIncidentes() {
        return (int) incidentes.stream()
                .mapToLong(incidente->incidente.horasDiferenciaEntreAperturaYCierre())
                .sum();
    }

    public void  addIncidente(Incidente incidente) {
        incidentes.add(incidente);
    }
}
