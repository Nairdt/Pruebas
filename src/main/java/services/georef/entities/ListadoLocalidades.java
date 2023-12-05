package services.georef.entities;

import lombok.Getter;

import java.util.List;
@Getter
public class ListadoLocalidades {
    public int cantidad;
    public int inicio;
    public List<Localidad> localidades;
    public int total;
}
