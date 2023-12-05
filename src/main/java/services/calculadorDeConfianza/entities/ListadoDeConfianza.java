package services.calculadorDeConfianza.entities;

import java.util.ArrayList;
import java.util.List;

public class ListadoDeConfianza {
    public List<UsuarioConfianza> usuarios;
    public List<ComunidadConfianza> comunidades;
    public List<IncidenteConfianza> incidentes;

    public ListadoDeConfianza() {
        this.usuarios = new ArrayList<>();
        this.comunidades = new ArrayList<>();
        this.incidentes = new ArrayList<>();
    }
}
