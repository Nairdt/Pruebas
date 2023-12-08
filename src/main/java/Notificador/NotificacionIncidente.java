package Notificador;

import Comunidad.Miembro;
import Servicios.ServicioCompuesto;
import Servicios.ServicioPorEstablecimiento;

public class NotificacionIncidente {
    private Miembro autor;
    private String descripcion;
    private TipoNotificacion tipoNotificacion;
    private FechaHora fechaHora;
    private ServicioPorEstablecimiento servicioAfectado;
    private ServicioCompuesto compuestoAfectado;


    public NotificacionIncidente(Miembro autor, String descripcion, TipoNotificacion tipoNotificacion, FechaHora fechaHora, ServicioPorEstablecimiento servicioAfectado, ServicioCompuesto compuestoAfectado){

        this.autor = autor;
        this.descripcion = descripcion;
        this.tipoNotificacion = tipoNotificacion;
        this.fechaHora  = fechaHora;
        this.servicioAfectado = servicioAfectado;
        this.compuestoAfectado = compuestoAfectado;
    }


    public String informeNotificacion(){
        String servicio = "";
        String tipo = "";
        if(this.servicioAfectado != null){
            servicio = this.servicioAfectado.getNombre();
            tipo = "Base";
        }
        else{
            servicio = this.compuestoAfectado.getNombre();
            tipo = "Compuesto";
        }//todo delegar en metodos distintos


        String informe = "";
        informe += "NOMBRE AUTOR: " + autor.getNombre() + "\n" +
                    "SERVICIO: " + servicio + "\n" +
                    "TIPO SERVICIO: " + tipo + "\n" +
                    "FECHA: " + this.fechaHora.getFecha() + "\n" +
                    "HORA: " + this.fechaHora.getHora() + "\n" +
                    "TIPO: " + this.tipoNotificacion() + "\n" +
                    "DESCRIPCION: " + this.descripcion + "\n";

        return informe;
    }

    public String tipoNotificacion(){//todo delegar en metodos distintos
        switch (this.tipoNotificacion){
            case FUNCIONA_SERVICIO:
                return "Incidente resuelto";
            case NO_FUNCIONA_SERVICIO:
                return "Incidente informado";
            case REVISION_INCIDENTE:
                return "Revision incidente";
            default:
                return "Desconocido";
        }

    }
}
