import Organizaciones.Entidad;
import Organizaciones.Establecimiento;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.georef.entities.Centroide;
import services.georef.entities.Localizacion;

import java.util.ArrayList;
import java.util.List;

public class TestLocalizaciones {
    //TODO Test comentado porque el metodo no se sabe si se implementa de esa forma, todavia
    /*
    Centroide primerCentroide;
    Centroide segundoCentroide;
    Centroide tercerCentroide;
    Centroide cuartoCentroide;
    Localizacion unaLocalizacion;
    Localizacion segundaLocalizacion;
    Localizacion terceraLocalizacion;
    Localizacion cuartaLocalizacion;
    List<Centroide> listaDeCentroides;
    Entidad unaEntidad;
    @Before
    public void init(){
        listaDeCentroides = new ArrayList<Centroide>();
        primerCentroide = new Centroide(52,25);
        segundoCentroide = new Centroide(33,44);
        tercerCentroide = new Centroide(14,25);
        cuartoCentroide = new Centroide(34,58);
        unaLocalizacion = new Localizacion(primerCentroide);
        segundaLocalizacion = new Localizacion(segundoCentroide);
        terceraLocalizacion = new Localizacion(tercerCentroide);
        cuartaLocalizacion = new Localizacion(cuartoCentroide);
        unaEntidad = new Entidad("ENRE");


    }

    @Test
    public void testListaCentroides(){
        unaEntidad.agregarEstablecimiento(new Establecimiento(unaLocalizacion),new Establecimiento(segundaLocalizacion),new Establecimiento(terceraLocalizacion),new Establecimiento(cuartaLocalizacion));
        listaDeCentroides = unaEntidad.espaciosEnActividad();
        Assert.assertEquals(listaDeCentroides.get(0).getLat(),52,0);
    }
     */
}
