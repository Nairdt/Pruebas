package Comunidad;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Interes {
    private String descripcion;

    public Interes(String descripcion){
        this.descripcion = descripcion;
    }
}
