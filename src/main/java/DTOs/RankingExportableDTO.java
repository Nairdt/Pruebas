package DTOs;

import Comunidad.Incidente;
import Organizaciones.Entidad;
import lombok.Getter;

import java.util.List;

@Getter
public class RankingExportableDTO {
    List<Entidad> listadoEntidades;

    List<Incidente> listadoIncidentes;

    public void setearListadoEntidades(List<Entidad> listadoEntidades) {
        this.listadoIncidentes = null;
        this.listadoEntidades = listadoEntidades;
    }

    public void setearListadoIncidentes(List<Incidente> listadoIncidentes) {
        this.listadoEntidades = null;
        this.listadoIncidentes = listadoIncidentes;
    }
}
