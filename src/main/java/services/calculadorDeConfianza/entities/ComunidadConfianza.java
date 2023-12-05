package services.calculadorDeConfianza.entities;

import java.util.ArrayList;
import java.util.List;

public class ComunidadConfianza {
    public int id;
    public double puntosDeConfianza;
    public GradoDeConfianza gradoDeConfianza;
    public List<UsuarioConfianza> usuarios = new ArrayList<>();

    public void agregarUsuarioAComunidad(UsuarioConfianza usuario) {
        this.usuarios.add(usuario);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPuntosDeConfianza(double puntos) {
        this.puntosDeConfianza = puntos;
    }

    public void setGradoDeConfianza(GradoDeConfianza grado) {
        this.gradoDeConfianza = grado;
    }
}
