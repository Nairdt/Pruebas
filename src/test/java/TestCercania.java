import Comunidad.Usuario;
import Servicios.Servicio;
import Servicios.ServicioPorEstablecimiento;
import org.junit.Assert;
import org.junit.Before;
import Organizaciones.*;
import org.junit.Test;
import services.georef.entities.Localizacion;

import java.util.ArrayList;
import java.util.List;
/*
public class TestCercania {
    Establecimiento primerEstablecimiento;
    Establecimiento segundoEstablecimiento;
    Establecimiento tercerEstablecimiento;
    Localizacion primeraLocalizacion;
    Localizacion segundaLocalizacion;
    Localizacion terceraLocalizacion;
    Usuario primerUsuario;
    Usuario segundoUsuario;
    Usuario tercerUsuario;
    Usuario cuartoUsuario;
    Usuario quintoUsuario;
    List<Establecimiento> listaEstablecimientos;


    @Before
    public void init(){
        primeraLocalizacion = new Localizacion(null, 1);
        segundaLocalizacion = new Localizacion(null, 2);
        terceraLocalizacion = new Localizacion(null, 1);
        primerEstablecimiento = new Establecimiento(primeraLocalizacion);
        segundoEstablecimiento = new Establecimiento(segundaLocalizacion);
        tercerEstablecimiento = new Establecimiento(terceraLocalizacion);
        primerUsuario = new Usuario(null,null,null,null,null,primeraLocalizacion);
        segundoUsuario = new Usuario(null,null,null,null,null,segundaLocalizacion);
        tercerUsuario = new Usuario(null,null,null,null,null,primeraLocalizacion);
        cuartoUsuario = new Usuario(null,null,null,null,null,segundaLocalizacion);
        quintoUsuario = new Usuario(null,null,null,null,null,terceraLocalizacion);
        listaEstablecimientos = new ArrayList<>();
        listaEstablecimientos.add(primerEstablecimiento);
        listaEstablecimientos.add(segundoEstablecimiento);
        listaEstablecimientos.add(tercerEstablecimiento);
        primerEstablecimiento.agregarServiciosDisponibles(new ServicioPorEstablecimiento(null, null, null, null),new ServicioPorEstablecimiento(null, null, null, null),new ServicioPorEstablecimiento(null, null, null, null),new ServicioPorEstablecimiento(null, null, null, null));
        segundoEstablecimiento.agregarServiciosDisponibles(new ServicioPorEstablecimiento(null,null,null,null),new ServicioPorEstablecimiento(null, null, null, null));
        tercerEstablecimiento.agregarServiciosDisponibles(new ServicioPorEstablecimiento(null,null,null,null));
    }

    @Test
    public void testEstablecimientosCercanosPrimerUsuario(){
        Assert.assertEquals(primerUsuario.establecimientosCercanos(listaEstablecimientos).size(),2);
    }
    @Test
    public void testServiciosCercanosPrimerUsuario(){
        Assert.assertEquals(primerUsuario.serviciosCercanos(listaEstablecimientos).size(),5);
    }
    @Test
    public void testEstablecimientosCercanosSegundoUsuario(){
        Assert.assertEquals(segundoUsuario.establecimientosCercanos(listaEstablecimientos).size(),1);
    }
    @Test
    public void testEstablecimientosCercanosTercerUsuario(){
        Assert.assertEquals(tercerUsuario.establecimientosCercanos(listaEstablecimientos).size(),2);
    }

}
*/