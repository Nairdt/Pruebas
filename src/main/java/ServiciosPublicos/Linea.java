package ServiciosPublicos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Linea {
    private String nombre;
    private List<Estacion> estaciones;
    private Estacion origen;
    private Estacion destino;
    private TipoMedioTransporte tipoMedioTransporte;

    public Linea(String nombre, Estacion origen, Estacion destino, TipoMedioTransporte tipoMedioTransporte){
        this.nombre = nombre;
        this.origen = origen;
        this.destino = destino;
        this.tipoMedioTransporte = tipoMedioTransporte;
    }
}
