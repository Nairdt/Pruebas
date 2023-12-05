package services.calculadorDeConfianza.entities;

import java.util.ArrayList;
import java.util.List;

public class PayloadDTO {
    private List<UsuarioConfianza> usuarios;
    private List<ComunidadConfianza> comunidades;
    private List<IncidenteConfianza> incidentes;

    public PayloadDTO() {
        this.usuarios = new ArrayList<>();
        this.comunidades = new ArrayList<>();
        this.incidentes = new ArrayList<>();
    }

    public void agregarUsuario(UsuarioConfianza usuario) {
        this.usuarios.add(usuario);
    }

    public void agregarComunidad(ComunidadConfianza comunidad) {
        this.comunidades.add(comunidad);
    }

    public void agregarIncidente(IncidenteConfianza incidente) {
        this.incidentes.add(incidente);
    }

    public void setUsuarios(List<UsuarioConfianza> usuarios) {
        this.usuarios = usuarios;
    }
}
