package Mappers;

import Organizaciones.Entidad;

import java.util.ArrayList;
import java.util.List;

public class MapperEntidad {

    public enum TipoRanking {
        MAYOR_CANT_INCIDENTES {
            public String convertirEntidad(Entidad entidad, MapperEntidad mapper) {
                return mapper.convertirEntidadAStringExportableCantidadIncidentes(entidad);
            }
        },
        MEJOR_TIEMPO_CIERRE {
            public String convertirEntidad(Entidad entidad, MapperEntidad mapper) {
                return mapper.convertirEntidadAStringExportableMejoresTiemposCierre(entidad);
            }
        };

        public abstract String convertirEntidad(Entidad entidad, MapperEntidad mapper);
    }


    public List<String> convertirListadoEntidadesAListadoStrings(List<Entidad> listadoEntidades,TipoRanking tipoRanking) {
        List<String> entidadesMapeadas = new ArrayList<>();
        MapperEntidad mapperEntidad = new MapperEntidad();

        for (Entidad entidad : listadoEntidades) {
            String entidadString = tipoRanking.convertirEntidad(entidad, mapperEntidad);
            entidadesMapeadas.add(entidadString);
        }

        return entidadesMapeadas;
    }

    public String convertirEntidadAStringExportableCantidadIncidentes(Entidad entidad) {
        String elemento;

        elemento = entidad.getNombre() + " - Incidentes: " + entidad.getCantidadIncidentesValidosParaRanking();

        return elemento;
    }

    public String convertirEntidadAStringExportableMejoresTiemposCierre(Entidad entidad) {
        String elemento;

        elemento = entidad.getNombre() + " - Promedio de cierre: " + entidad.getPromedioCierreIncidentes();

        return elemento;
    }
}
