package services.calculadorDeConfianza.entities;

import java.util.ArrayList;
import java.util.List;

public class IncidenteConfianza {
    public int id;
    public UsuarioConfianza usuarioApertura;
    public UsuarioConfianza usuarioCierre;
    public String descripcion;
    public boolean estado;
    public PrestacionDeServicio prestacionDeServicio;
    public String fechaApertura;
    public String fechaCierre;

    public void setId(int id) {
        this.id = id;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public void setPrestacionDeServicio(PrestacionDeServicio prestacionDeServicio) {
        this.prestacionDeServicio = prestacionDeServicio;
    }

    public void setUsuarioApertura(UsuarioConfianza usuarioApertura) {
        this.usuarioApertura = usuarioApertura;
    }

    public void setUsuarioCierre(UsuarioConfianza usuarioCierre) {
        this.usuarioCierre = usuarioCierre;
    }
}
