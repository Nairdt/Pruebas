import Comunidad.Comunidad;
import Comunidad.Miembro;
import Comunidad.Usuario;
import Notificador.*;
import Servicios.ServicioCompuesto;
import Servicios.ServicioPorEstablecimiento;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.quartz.SchedulerException;
import services.georef.ServicioGeoref;
import services.georef.entities.Departamento;
import Comunidad.RolUsuario;
import Comunidad.LapsoReceptor;
import Comunidad.MedioReceptor;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
/*
public class TestNotificacion {
    ServicioPorEstablecimiento baniosHombres;
    ServicioPorEstablecimiento baniosMujeres;
    ServicioPorEstablecimiento banioMixtos;
    ServicioPorEstablecimiento bondi;
    ServicioCompuesto banios;
    FechaHora fechaHora;
    NotificacionIncidente noFuncaBathroom;
    NotificacionIncidente pinchoRuedaElBondi;
    NotificacionIncidente funcaBathroom;
    @Before
    public void init(){
        baniosHombres = new ServicioPorEstablecimiento("ÑOBA HOMBRES",null, null, null);
        baniosMujeres = new ServicioPorEstablecimiento("ÑOBA MINAS",null, null, null);
        banioMixtos = new ServicioPorEstablecimiento("MIXTOS",null, null, null);
        bondi = new ServicioPorEstablecimiento("BONDI",null, null, null);

        ServicioCompuesto banios = new ServicioCompuesto("baños");
        banios.agregarServicio(baniosHombres);
        Miembro nico = new Miembro("Nico", "Aparicio", "nicolas.matias.aparicio@gmail.com", null, "1162917177",null);
        Miembro mariano = new Miembro("Mariano", "Iturriza", "nicolas.matias.aparicio@gmail.com", null, "3434484379",null);
        fechaHora = new FechaHora(LocalDate.now(), LocalTime.now());
        noFuncaBathroom = new NotificacionIncidente(nico, "No funcan los ñobas de la utn", TipoNotificacion.NO_FUNCIONA_SERVICIO, fechaHora, null, banios);
        pinchoRuedaElBondi = new NotificacionIncidente(nico, "El bondi palmo", TipoNotificacion.NO_FUNCIONA_SERVICIO, fechaHora, bondi, null);
        funcaBathroom = new NotificacionIncidente(nico, "Ya funca el de hombres", TipoNotificacion.FUNCIONA_SERVICIO, fechaHora, baniosHombres, null);
    }


    @Test
    public void testCuandoSucede()throws IOException{
        ServicioGeoref servicioGeoref = ServicioGeoref.getInstancia();
        Departamento undepartamento = servicioGeoref.departamentoAPartirDeNombreYProvincia("Comuna 14","Ciudad Autónoma de Buenos Aires");
        Assert.assertTrue(undepartamento.getId() == 2098);
    }

    @Test
    public void notificarCuandoSucedenWsp(){
        NotificarCuandoSuceden notificadorCuandoSuceden = new NotificarCuandoSuceden(noFuncaBathroom);
        MedioNotificable wsp = new NotificarPorWhatsapp();
        try {
            wsp.notificarPorMedio("1162917177", notificadorCuandoSuceden.armarMensaje());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void notificarSinApurosMail(){
        NotificarSinApuros notificadorSinApuros = new NotificarSinApuros();
        notificadorSinApuros.agregarNotificacion(noFuncaBathroom);
        notificadorSinApuros.agregarNotificacion(pinchoRuedaElBondi);
        notificadorSinApuros.agregarNotificacion(funcaBathroom);

        MedioNotificable mail = new NotificarPorMail();

        try {
            mail.notificarPorMedio("naparicio@frba.utn.edu.ar", notificadorSinApuros.armarMensaje());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void notificarMiembrosComunidad(){
        Comunidad team3 = new Comunidad("equipo tres", "ejemplo");
        Usuario userNico = new Usuario("Nicolas", "111", RolUsuario.ADMINISTRADOR, MedioReceptor.EMAIL, LapsoReceptor.SIN_APUROS, null);
        Usuario userAdri = new Usuario("Adrian", "111", RolUsuario.ADMINISTRADOR, MedioReceptor.EMAIL, LapsoReceptor.CUANDO_SUCEDEN, null);
        Usuario userJuan = new Usuario("Juan", "111", RolUsuario.ADMINISTRADOR, MedioReceptor.EMAIL, LapsoReceptor.CUANDO_SUCEDEN, null);
        Usuario userAgus = new Usuario("Agustin", "111", RolUsuario.ADMINISTRADOR, MedioReceptor.EMAIL, LapsoReceptor.CUANDO_SUCEDEN, null);
        Usuario userMariano = new Usuario("Mariano","111",RolUsuario.ADMINISTRADOR, MedioReceptor.EMAIL, LapsoReceptor.CUANDO_SUCEDEN, null);
        Miembro nico = new Miembro("Nicolas","Aparicio","naparicio@frba.utn.edu.ar",userNico,null, team3);
        Miembro adri = new Miembro("Adrian","Tolaba","adtolaba@frba.utn.edu.ar",userAdri, null,team3);
        Miembro juan = new Miembro("Juan","Nardi","jnardi@frba.utn.edu.ar", userJuan, null,team3);
        Miembro agus = new Miembro("Agustin","Quiroga","agquiroga@frba.utn.edu.ar",userAgus,null, team3);
        Miembro mariano = new Miembro("Mariano","Iturriza","miturrizasicardi@frba.utn.edu.ar",userMariano,null, team3);
        team3.agregarMiembros(nico, adri, juan, agus, mariano);

        nico.notificarIncidente("xd", TipoNotificacion.NO_FUNCIONA_SERVICIO, baniosHombres, null);

    }
//
//    public static void main(String[] args) throws SchedulerException, IOException {
//
//
//        NotificarCuandoSuceden notificadorCuandoSuceden = new NotificarCuandoSuceden(noFuncaBathroom);
//        NotificarSinApuros notificadorSinApuros = new NotificarSinApuros();
//        notificadorSinApuros.agregarNotificacion(noFuncaBathroom);
//        notificadorSinApuros.agregarNotificacion(pinchoRuedaElBondi);
//        notificadorSinApuros.agregarNotificacion(funcaBathroom);
//
//
//        System.out.println(notificadorSinApuros.armarMensaje());
//
//        //notificadorCuandoSuceden.notificarPorLapso("1162917177");
//        MedioNotificable wsp = new NotificarPorWhatsapp();
//        MedioNotificable mail = new NotificarPorMail();
//
//        //wsp.notificarPorMedio("1162917177", notificadorCuandoSuceden.armarMensaje());
//        //wsp.notificarPorMedio("1128591184", notificadorSinApuros.armarMensaje());
//        mail.notificarPorMedio("naparicio@frba.utn.edu.ar", notificadorSinApuros.armarMensaje());
//        //notificadorSinApuros.notificarPorLapso("3434484379");
//        //Notificador notificador = new Notificador(lapso);
//    }

}*/
