package ServiciosPublicos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Entrada {
    private String calle;
    private int altura;
    private String barrio;
    private String provincia;

    public Entrada(String calle, int altura, String barrio, String provincia){
        this.calle = calle;
        this.altura = altura;
        this.barrio = barrio;
        this.provincia = provincia;
    }
}
