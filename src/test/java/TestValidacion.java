import Validador.ValidacionComplejidad;
import Validador.ValidacionListaPeoresContrasenias;
import Validador.ValidacionLongitud;
import Validador.ValidacionNoTrivial;
import Validador.ValidacionMinuscula;
import Validador.ValidacionMayuscula;
import Validador.ValidacionNumeros;
import Validador.*;
import Validador.ValidacionSecuenciasNoTriviales;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

//TODO USAR UN TEST POR CADA STRING DE PASSWORD
public class TestValidacion {
    @Test
    public void testComplejidad()
    {
        String passwordCompleja = "aBc123";
        String passwordSimple = "Abc"; //No posee numeros
        ValidacionComplejidad validador = new ValidacionComplejidad(true, true, true);
        Assert.assertTrue(validador.cumpleValidacion(passwordCompleja));
        Assert.assertFalse(validador.cumpleValidacion(passwordSimple));

    }

    @Test
    public void testMayuscula() {
        String passwordSinMayuscula  = "abcdef";
        String passwordConMayuscula  = "aBcDeF";
        String passwordConMayuscula2 = "ABCDEF";
        ValidacionMayuscula validador = new ValidacionMayuscula();
        Assert.assertFalse(validador.cumpleValidacion(passwordSinMayuscula));
        Assert.assertTrue(validador.cumpleValidacion(passwordConMayuscula));
        Assert.assertTrue(validador.cumpleValidacion(passwordConMayuscula2));
    }

    @Test
    public void testMinuscula() {
        String passwordSinMinuscula  = "ABCDEF";
        String passwordConMinuscula  = "abcDEF";
        String passwordConMinuscula2 = "ABcDEF";
        ValidacionMinuscula validador = new ValidacionMinuscula();
        Assert.assertFalse(validador.cumpleValidacion(passwordSinMinuscula));
        Assert.assertTrue(validador.cumpleValidacion(passwordConMinuscula));
        Assert.assertTrue(validador.cumpleValidacion(passwordConMinuscula2));
    }

    @Test
    public void testNumeros() {
        String passwordSinNumeros  = "abcdef";
        String passwordConNumeros  = "48CD3F";
        String passwordConNumeros2 = "912018";
        ValidacionNumeros validador = new ValidacionNumeros();
        Assert.assertFalse(validador.cumpleValidacion(passwordSinNumeros));
        Assert.assertTrue(validador.cumpleValidacion(passwordConNumeros));
        Assert.assertTrue(validador.cumpleValidacion(passwordConNumeros2));
    }

    @Test
    public void testPeoresContrasenias() throws IOException {
        String password = "ma!Rix1!!!";
        ValidacionListaPeoresContrasenias validador = new ValidacionListaPeoresContrasenias();
        Assert.assertTrue(validador.cumpleValidacion(password));
    }

    @Test
    public void testLongitud()
    {
        String passwordLongitudMayor = "123456789";
        String passwordLongitudMenor = "12345";
        ValidacionLongitud validador = new ValidacionLongitud();
        Assert.assertTrue(validador.cumpleValidacion(passwordLongitudMayor));
        Assert.assertFalse(validador.cumpleValidacion(passwordLongitudMenor));
    }

    @Test
    public void testSecuenciaNoTrivial(){
        //String passwordTrivial1 = "aaaaaaaaaaa";
        String passwordTrivial2 = "12345678";
        String passwordTrivial3 = "abcdefghi";
        String passwordNoTrivial = "abc12345";
        ValidacionSecuenciasNoTriviales validador = new ValidacionSecuenciasNoTriviales();
        validador.agregarSecuencia("abcdefghijklmnopqrstuvwxyz");
        validador.agregarSecuencia("0123456789");
        Assert.assertTrue(validador.cumpleValidacion(passwordNoTrivial));
        //Assert.assertFalse(validador.cumpleValidacion(passwordTrivial1));
        Assert.assertFalse(validador.cumpleValidacion(passwordTrivial2));
        Assert.assertFalse(validador.cumpleValidacion(passwordTrivial3));
    }

    @Test
    public void testCaracteresNoIguales(){
        String passwordTrivial1 = "aaaaaaaaaa";
        String passwordNoCaracteresIguales = "abcdefg";
        ValidacionCaracteresNoIguales validador = new ValidacionCaracteresNoIguales();
        Assert.assertFalse(validador.cumpleValidacion(passwordTrivial1));
        Assert.assertTrue(validador.cumpleValidacion(passwordNoCaracteresIguales));
    }

    @Test
    public void TestIntegrador()
    {
        String password = "ma!Rix1!!!test!";
        String passwordNoCumpleLongitud = "ma!Rix";
        String passwordNoCumpleComplejidad = "ma!!!trix";
        String passwordNoCumplePeoresContrasenia = "vSjasnel12";
        String passwordNoCumpleNoTrivial = "efghijklmn";
        Validador validador = new Validador();
        try {
            ValidacionListaPeoresContrasenias validadorListaPeores = new ValidacionListaPeoresContrasenias();
            validador.habilitarValidacion(validadorListaPeores);
            validador.habilitarValidacion(new ValidacionLongitud());
            validador.habilitarValidacion(new ValidacionComplejidad(true, true, true));
            ValidacionNoTrivial validacionNoTrivial = new ValidacionNoTrivial();
            validacionNoTrivial.agregarSecuencia("abcdefghijklmnopqrstuvwxyz");
            validacionNoTrivial.agregarSecuencia("0123456789");
            validador.habilitarValidacion(validacionNoTrivial);
            Assert.assertTrue(validador.cumpleValidaciones(password));
            Assert.assertFalse(validador.cumpleValidaciones(passwordNoCumpleComplejidad));
            Assert.assertFalse(validador.cumpleValidaciones(passwordNoCumpleLongitud));
            Assert.assertFalse(validador.cumpleValidaciones(passwordNoCumplePeoresContrasenia));
            Assert.assertFalse(validador.cumpleValidaciones(passwordNoCumpleNoTrivial));
        }
        catch (Exception e) {
            System.out.println("Error al hacer Test Integrador, Error: " + e);
        }
    }
}
