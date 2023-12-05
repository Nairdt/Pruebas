package services.generadorDeRanking.entities;

public class ResponseEntidadDTO {
    public int idEntidad;
    public int nivelImpactoEntidad;
    public int puestoRanking;

    public int getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(int idEntidad) {
        this.idEntidad = idEntidad;
    }

    public int getNivelImpactoEntidad() {
        return nivelImpactoEntidad;
    }

    public void setNivelImpactoEntidad(int nivelImpactoEntidad) {
        this.nivelImpactoEntidad = nivelImpactoEntidad;
    }

    public int getPuestoRanking() {
        return puestoRanking;
    }

    public void setPuestoRanking(int puestoRanking) {
        this.puestoRanking = puestoRanking;
    }
}
