package services.generadorDeRanking.entities;

public class RequestEntidadDTO {
    private int idEntidad;
    private int sumatoriaTiemposResolucion;
    private int cantidadIncidentesNoResueltos;
    private int cantidadMiembrosAfectados;

    public RequestEntidadDTO(int id, int sumatoria, int cantidadIncidentes, int cantidadMiembros) {
        this.idEntidad = id;
        this.sumatoriaTiemposResolucion = sumatoria;
        this.cantidadIncidentesNoResueltos = cantidadIncidentes;
        this.cantidadMiembrosAfectados = cantidadMiembros;
    }

    public void setIdEntidad(int idEntidad) {
        this.idEntidad = idEntidad;
    }

    public int getIdEntidad() {
        return idEntidad;
    }

    public void setCantidadIncidentesNoResueltos(int cantidadIncidentesNoResueltos) {
        this.cantidadIncidentesNoResueltos = cantidadIncidentesNoResueltos;
    }

    public int getCantidadIncidentesNoResueltos() {
        return cantidadIncidentesNoResueltos;
    }

    public void setCantidadMiembrosAfectados(int cantidadMiembrosAfectados) {
        this.cantidadMiembrosAfectados = cantidadMiembrosAfectados;
    }

    public int getCantidadMiembrosAfectados() {
        return cantidadMiembrosAfectados;
    }

    public void setSumatoriaTiemposResolucion(int sumatoriaTiemposResolucion) {
        this.sumatoriaTiemposResolucion = sumatoriaTiemposResolucion;
    }

    public int getSumatoriaTiemposResolucion() {
        return sumatoriaTiemposResolucion;
    }
}

