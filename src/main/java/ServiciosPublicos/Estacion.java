package ServiciosPublicos;

import Servicios.ServicioPorEstablecimiento;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Estacion {
    private String nombre;
    private List<Entrada> entradas;
    private ServicioPorEstablecimiento servicioFaltante;

    public Estacion(String nombre, ServicioPorEstablecimiento servicioFaltante){
        this.nombre = nombre;
        this.servicioFaltante = servicioFaltante;
    }
}
