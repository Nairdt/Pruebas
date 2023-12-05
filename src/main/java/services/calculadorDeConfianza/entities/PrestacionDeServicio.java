package services.calculadorDeConfianza.entities;

public class PrestacionDeServicio {
    public EntidadConfianza entidad;
    public EstablecimientoConfianza establecimiento;
    public ServicioConfianza servicio;

    public void setEntidad(EntidadConfianza entidad) {
        this.entidad = entidad;
    }

    public void setEstablecimiento(EstablecimientoConfianza establecimiento) {
        this.establecimiento = establecimiento;
    }

    public void setServicio(ServicioConfianza servicio) {
        this.servicio = servicio;
    }
}
