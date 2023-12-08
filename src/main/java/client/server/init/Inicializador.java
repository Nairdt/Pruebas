package client.server.init;

import Comunidad.RolUsuario;
import Comunidad.Usuario;
import client.models.entities.usuarios.Funcionalidad;
import client.models.entities.usuarios.Rol;
import dbManager.EntityManagerHelper;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class Inicializador extends EntityManagerHelper {
    public static void init() {
        new Inicializador()
                .iniciarTransaccion()
                .roles()
                .commitearTransaccion();
    }

    private Inicializador iniciarTransaccion() {
        entityManager().getTransaction().begin();
        return this;
    }

    private Inicializador commitearTransaccion() {
        entityManager().getTransaction().commit();
        return this;
    }


    private Inicializador roles() {

        Rol administrador = new Rol();
        administrador.setNombre("Administrador");
        administrador.setRolUsuario(RolUsuario.ADMINISTRADOR);
        entityManager().persist(administrador);

        Usuario admin = new Usuario("admin", "admin", "admin@gmail.com", administrador, null);
        entityManager().persist(admin);

        Rol usuario = new Rol();
        usuario.setNombre("Usuario");
        usuario.setRolUsuario(RolUsuario.MIEMBRO);
        entityManager().persist(usuario);

        Rol prestador = new Rol();
        prestador.setNombre("Prestador");
        prestador.setRolUsuario(RolUsuario.PRESTADOR);
        entityManager().persist(prestador);

        return this;
    }
}
