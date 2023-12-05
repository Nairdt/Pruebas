package services.calculadorDeConfianza.entities;

public class UsuarioConfianza {
    public int id;
    public double puntosDeConfianza;
    public GradoDeConfianza gradoDeConfianza;

    public void setPuntosDeConfianza(double v) {
        this.puntosDeConfianza = v;
    }

    public void setId(int i) {
        this.id = i;
    }

    public void setGradoDeConfianza(GradoDeConfianza grado) {
        this.gradoDeConfianza = grado;
    }
}
