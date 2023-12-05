import org.junit.Assert;
import org.junit.Test;
import services.georef.GeorefService;
import services.georef.ServicioGeoref;
import services.georef.entities.Centroide;
import services.georef.entities.Departamento;
import services.georef.entities.Municipio;
import services.georef.entities.Provincia;

import java.io.IOException;

import static org.mockito.Mockito.*;
public class TestMockeados {

    @Test
    public void testProvincia() throws IOException{
        Centroide unCentroide = new Centroide(-38.5808546732895,-62.1656895327094);
        Provincia buenosAires = new Provincia(6,unCentroide,"Buenos Aires");
        ServicioGeoref servicioGeoref = mock(ServicioGeoref.class);
        when(servicioGeoref.provinciaAPartirDeNombre("Buenos Aires")).thenReturn(buenosAires);
        Assert.assertEquals(buenosAires.getNombre(), "Buenos Aires");
    }
    @Test
    public void testMunicipioMockeado() throws IOException {
        String unaProvincia = "Buenos Aires";
        Centroide unCentroide = new Centroide(-38.5808546732895,-62.1656895327094);
        Provincia buenosAires = new Provincia(6,unCentroide,"Buenos Aires");
        Municipio unMunicipio = new Municipio(60056,buenosAires,unCentroide,"Bahía Blanca");
        ServicioGeoref servicioGeoref = mock(ServicioGeoref.class);
        when(servicioGeoref.idDeProvincia(unaProvincia)).thenReturn(6);
        when(servicioGeoref.municipioAPartirDeNombreYProvincia("Bahia Blanca",unaProvincia)).thenReturn(unMunicipio);

        Municipio municipioBahiaBlanca = servicioGeoref.municipioAPartirDeNombreYProvincia("Bahia Blanca", unaProvincia);
        Assert.assertEquals(unMunicipio.getId(),60056);

    }

    @Test
    public void testDepartamentoMockeado() throws IOException {
        String unaProvincia = "Ciudad Autónoma de Buenos Aires";
        Centroide unCentroide = new Centroide(-34.6144934119689,-58.4458563545429);
        Provincia ciudadDeBuenosAires = new Provincia(2,unCentroide,"Ciudad Autónoma de Buenos Aires");
        Departamento unDepartamento = new Departamento(2098,ciudadDeBuenosAires,unCentroide,"Comuna 14");
        ServicioGeoref servicioGeoref = mock(ServicioGeoref.class);
        when(servicioGeoref.idDeProvincia(unaProvincia)).thenReturn(2);
        when(servicioGeoref.departamentoAPartirDeNombreYProvincia("Bahia Blanca",unaProvincia)).thenReturn(unDepartamento);

        Municipio municipioBahiaBlanca = servicioGeoref.municipioAPartirDeNombreYProvincia("Bahia Blanca", unaProvincia);
        Assert.assertEquals(unDepartamento.getId(),2098);

    }

}
