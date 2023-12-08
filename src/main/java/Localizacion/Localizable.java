package Localizacion;

import services.georef.entities.*;

import java.io.IOException;

public interface Localizable {
    public ListadoProvincias listadoProvincias() throws IOException;
    public ListadoMunicipios listadoMunicipiosDeProvincia(int idProvincia) throws IOException;
    public ListadoLocalidades localidadesDeProvincia(int idProvincia) throws IOException;
    public ListadoDepartamentos listadoDepartamentosDeProvincia(int idProvincia) throws IOException;
}
