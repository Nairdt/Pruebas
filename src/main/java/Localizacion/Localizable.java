package Localizacion;

import services.georef.entities.ListadoDepartamentos;
import services.georef.entities.ListadoLocalidades;
import services.georef.entities.ListadoProvincias;
import services.georef.entities.Municipio;

import java.io.IOException;

public interface Localizable {
    public ListadoProvincias listadoProvincias() throws IOException;
    public Municipio listadoMunicipiosDeProvincia(int idProvincia) throws IOException;
    public ListadoLocalidades localidadesDeProvincia(int idProvincia) throws IOException;
    public ListadoDepartamentos listadoDepartamentosDeProvincia(int idProvincia) throws IOException;
}
