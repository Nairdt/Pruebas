import Comunidad.Comunidad;
import Comunidad.Incidente;
import Comunidad.RolUsuario;
import Comunidad.Usuario;
import Comunidad.Miembro;
import Notificador.FechaHora;
import Organizaciones.Entidad;
import Organizaciones.Establecimiento;
import Servicios.Servicio;
import Servicios.ServicioPorEstablecimiento;
import Servicios.TipoServicio;
import client.models.entities.usuarios.Rol;
import client.models.repositories.*;
import dbManager.EntityManagerHelper;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ContextTest implements SimplePersistenceTest {

    @Test
    void contextUp() {
        assertNotNull(entityManager());
    }

    @Test
    void contextUpWithTransaction() throws Exception {
        withTransaction(() -> {
        });
    }
    @Test
    void testPersistenciaIncidente() {
        FechaHora fechaHoraInicio = new FechaHora(LocalDate.now(), LocalTime.now());
        FechaHora fechaHoraFin = new FechaHora(LocalDate.now(), LocalTime.now());
        Incidente unIncidente = new Incidente(null, "Prueba", null, null, fechaHoraInicio, true);
        persist(fechaHoraInicio);
        persist(fechaHoraFin);
        persist(unIncidente);
        commitTransaction();
    }

    @Test
    void testPersistenciaServicio() {
        Establecimiento unEstablecimiento = new Establecimiento();
        TipoServicio primerTipoServicio = new TipoServicio("Higiene");
        TipoServicio segundoTipoServicio = new TipoServicio("Movilidad");
        Servicio primerServicio = new Servicio("Baño UTN", primerTipoServicio);
        Servicio segundoServicio = new Servicio("Ascensor UTN", segundoTipoServicio);
        ServicioPorEstablecimiento unServicioPorEstablecimiento = new ServicioPorEstablecimiento("Baño UTN", unEstablecimiento, true, primerServicio);
        ServicioPorEstablecimiento otroServicioPorEstablecimiento = new ServicioPorEstablecimiento("Baño UTN", unEstablecimiento, true, primerServicio);
        ServicioPorEstablecimiento algunServicioPorEstablecimiento = new ServicioPorEstablecimiento("Baño UTN", unEstablecimiento, true, null);
        persist(primerTipoServicio);
        persist(segundoTipoServicio);
        persist(primerServicio);
        persist(segundoServicio);
        persist(unEstablecimiento);
        persist(unServicioPorEstablecimiento);
        //persist(algunServicioPorEstablecimiento);// Persiste correctamente al no tener PK duplicadas
        //persist(otroServicioPorEstablecimiento); //No deja crear otro servicioporestablecimiento igual por constraint funciona bien
        commitTransaction();
    }



    @Test
    void testSeleccion(){
//         List<Servicio> servicios = entityManager().createQuery("from " + Servicio.class.getName()).getResultList();
//         Servicio unServicio = (Servicio) entityManager().createQuery("from "+ Servicio.class.getName() + " where nombre like " + "'Baño UTN'").getResultList().get(0);
//         System.out.println(unServicio.getNombre());
//        String nombre = "Baño UTN";
//        RepositorioDeServicios repositorioDeServicios = new RepositorioDeServicios();
//        System.out.println((repositorioDeServicios.buscarPorNombreEstablecimiento(nombre)));
        List<Incidente> listaDeIncidentes = entityManager().createQuery("from "+ Incidente.class.getName() + " where resuelto = '" + 0 + "'").getResultList();
        listaDeIncidentes.forEach(i -> {
            System.out.println(i.getId_incidente());
        });

    }
    @Test
    void resolverIncidente(){
        Incidente unIncidente = (Incidente) entityManager().createQuery("from "+ Incidente.class.getName() + " where resuelto = '" + 0 + "'").getResultList().get(0);
        unIncidente.resolverIncidente(null);
        persist(unIncidente);
        persist(unIncidente.getTimestampFin());
        commitTransaction();
    }

    @Test
    void recuperarIncidente(){
        RepositorioDeIncidentes repositorioDeIncidentes = new RepositorioDeIncidentes();
        Incidente otroIncidente = (Incidente) repositorioDeIncidentes.buscarPorId(29);//Ok//me quedo aca en code with me xd
        System.out.println(otroIncidente);
        System.out.println(otroIncidente.getId_incidente());
        System.out.println(otroIncidente.getObservaciones());
    }

    @Test
    void buscarMail(){
//        Usuario usuario = new Usuario("Adrian", "1234","adtolaba@frba.utn.edu.ar",null);
//        Usuario usuario1 = new Usuario("Adrian2", "1234","adtolaba1@frba.utn.edu.ar",null);
//        Usuario usuario2 = new Usuario("Adrian3", "1234","adtolaba2@frba.utn.edu.ar",null);
//        Usuario usuario3 = new Usuario("Adrian4", "1234","adtolaba3@frba.utn.edu.ar",null);
//        persist(usuario);
//        persist(usuario1);
//        persist(usuario2);
//        persist(usuario3);
//        commitTransaction();

        //Assert.assertEquals(entityManager().createQuery("from "+ Usuario.class.getName() + " where mail like '" + "adtolaba@frba.utn.edu.ar" + "'").getResultList().size(),0);

        Assert.assertEquals(entityManager().createQuery("from "+ Usuario.class.getName() + " where mail like '" + "adtolaba@frba.utn.edu.ar" + "' and clave like '"+ "12345" +"'").getResultList().size()==1,true);
    }

    @Test
    void inicializarRoles(){
        Rol administrador = new Rol("Administrador",RolUsuario.ADMINISTRADOR);
        Rol miembro = new Rol("Miembro",RolUsuario.MIEMBRO);
        Rol prestador = new Rol("Prestador",RolUsuario.PRESTADOR);
        Rol proveedor = new Rol("Proveedor",RolUsuario.PROVEEDOR);

        persist(administrador);
        persist(miembro);
        persist(prestador);
        persist(proveedor);
        commitTransaction();
    }

    @Test
    void testEntidad(){
        RepositorioDeEntidades repositorioDeEntidades = new RepositorioDeEntidades();
        Entidad entidad = (Entidad) repositorioDeEntidades.buscarPorId(50);
        System.out.println(entidad.getEstablecimientos().size());
    }

    @Test
    void testRecuperacion(){
        Entidad unaEntidad = new Entidad("Prueba");
        Establecimiento primerEstablecimiento = new Establecimiento();
        Establecimiento segundoEstablecimiento = new Establecimiento();
        unaEntidad.agregarEstablecimiento(primerEstablecimiento);
        unaEntidad.agregarEstablecimiento(segundoEstablecimiento);
        segundoEstablecimiento.setEntidad(unaEntidad);
        primerEstablecimiento.setEntidad(unaEntidad);
        persist(segundoEstablecimiento);
        persist(primerEstablecimiento);
        persist(unaEntidad);
        commitTransaction();
    }
    @Test
    void testComunidadAgregar(){
        RepositorioDeComunidades repositorioDeComunidades = new RepositorioDeComunidades();
        RepositorioDeUsuarios repositorioDeUsuarios = new RepositorioDeUsuarios();
        Comunidad comunidad = (Comunidad) repositorioDeComunidades.buscarPorId(21);
        Usuario usuario = (Usuario) repositorioDeUsuarios.buscarPorId(18);
        //repositorioDeComunidades.entityManager().getTransaction().begin();
        Miembro miembro = new Miembro(usuario, comunidad, null, null);
        //miembro.setId_miembro(1);
        usuario.agregarMembresia(miembro);
        comunidad.getMiembros().add(miembro);
        persist(usuario);
        persist(comunidad);

        //persist(miembro);




        //persist(comunidad);




        commitTransaction();
    }
    @Test
    void tiposServicios(){
        TipoServicio unTipo = new TipoServicio("Baños");
        TipoServicio otroTipo = new TipoServicio("Paradas");
        TipoServicio algunTipo = new TipoServicio("Ascensor");
        persist(unTipo);
        persist(otroTipo);
        persist(algunTipo);
        commitTransaction();
    }

}