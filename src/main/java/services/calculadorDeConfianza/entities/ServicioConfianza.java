package services.calculadorDeConfianza.entities;

public class ServicioConfianza {
    public int id;
    public String nombre;
    public boolean estado;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
