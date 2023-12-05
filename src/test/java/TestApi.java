import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.georef.ServicioGeoref;
import services.georef.entities.*;

import java.io.IOException;
import java.util.List;

public class TestApi {

    @Test
    public void testDepartamentoAPI()throws IOException{
        ServicioGeoref servicioGeoref = ServicioGeoref.getInstancia();
        Departamento undepartamento = servicioGeoref.departamentoAPartirDeNombreYProvincia("Comuna 14","Ciudad Autónoma de Buenos Aires");
        Assert.assertTrue(undepartamento.getId() == 2098);
    }
    @Test
    public void testMunicipioAPITieneProvinciaCorrecta()throws IOException{
        ServicioGeoref servicioGeoref = ServicioGeoref.getInstancia();
        Municipio unMunicipio = servicioGeoref.municipioAPartirDeNombreYProvincia("Bahia Blanca","Buenos Aires");
        Assert.assertTrue(unMunicipio.getProvincia().getNombre().equals("Buenos Aires"));
    }@Test
    public void testMunicipioAPI()throws IOException{
        ServicioGeoref servicioGeoref = ServicioGeoref.getInstancia();
        Municipio unMunicipio = servicioGeoref.municipioAPartirDeNombreYProvincia("Bahia Blanca","Buenos Aires");
        Assert.assertTrue(unMunicipio.getId() == 60056);
    }
    @Test
    public void testDepartamentoAPITieneProvinciaCorrecta()throws IOException{
        ServicioGeoref servicioGeoref = ServicioGeoref.getInstancia();
        Departamento undepartamento = servicioGeoref.departamentoAPartirDeNombreYProvincia("Comuna 14","Ciudad Autónoma de Buenos Aires");
        Assert.assertTrue(undepartamento.provincia.getNombre().equals("Ciudad Autónoma de Buenos Aires"));
    }

    @Test
    public void provinciaAPI() throws IOException{
        ServicioGeoref servicioGeoref = ServicioGeoref.getInstancia();
        Provincia unaProvincia = servicioGeoref.provinciaAPartirDeNombre("Buenos Aires");
        Assert.assertTrue(unaProvincia.getCentroide().getLat() == -36.6769415180527);
    }

    @Test
    public void testLocalidadApi() throws IOException{
        ServicioGeoref servicioGeoref = ServicioGeoref.getInstancia();
        ListadoLocalidades localidades = servicioGeoref.localidadesDeProvincia(14);
        for(Localidad unaLocalidad:localidades.getLocalidades())
        {
            System.out.println("Id: " + unaLocalidad.getId());
            System.out.println("Nombre: " + unaLocalidad.getNombre());
            System.out.println("Municipio: " + unaLocalidad.getMunicipio().getNombre());
        }
        Assert.assertEquals(localidades.getLocalidades().size(),568);
    }

    @Test
    public void listadoProvincia() throws IOException {
        ServicioGeoref servicioGeoref = ServicioGeoref.getInstancia();
        System.out.println(servicioGeoref.listadoProvincias().provincias);
    }
}
