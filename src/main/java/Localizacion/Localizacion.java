package Localizacion;

import lombok.Getter;
import services.georef.entities.Departamento;
import services.georef.entities.Municipio;
import services.georef.entities.Provincia;
@Getter
public class Localizacion {

    private Departamento departamento;
    private Municipio municipio;
    private Provincia provincia;
    private Localizable localizable;
}
