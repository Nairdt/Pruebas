package client.server.init;

import Comunidad.RolUsuario;
import client.models.entities.usuarios.Funcionalidad;
import client.models.entities.usuarios.Rol;
import dbManager.EntityManagerHelper;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class Inicializador extends EntityManagerHelper {
    public static void init() {
        new Inicializador()
                .iniciarTransaccion()
                .funcionalidades()
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

    private Inicializador funcionalidades() {
        String[][] funcionalidades = {
                { "Ver servicios", "ver_servicios" },
                { "Crear servicios", "crear_servicios" },
                { "Editar servicios", "editar_servicios" },
                { "Cambiar estado servicios", "cambiar_estado_servicios" },
                { "Ver usuarios", "ver_usuarios" },
                { "Editar usuarios", "editar_usuarios" },
                { "Ver establecimientos", "ver_establecimientos" },
                { "Crear establecimientos", "crear_establecimientos" },
                { "Editar establecimientos", "editar_establecimientos" },
                { "Ver entidades", "ver_entidades" },
                { "Editar entidades", "editar_entidades" },
                { "Crear entidades", "crear_entidades" },
                { "Ver incidentes", "ver_incidentes" },
                { "Crear incidentes", "crear_incidentes" },
                { "Resolver incidentes", "resolver_incidentes" },
                { "Carga Masiva", "carga_masiva"}
        };
        for(String[] unaFuncionalidad: funcionalidades) {
            try{
                Funcionalidad funcionalidad = new Funcionalidad();
                funcionalidad.setNombre(unaFuncionalidad[0]);
                funcionalidad.setNombreInterno(unaFuncionalidad[1]);
                entityManager().persist(funcionalidad);
                entityManager().flush();
            }
            catch (Exception e) {
                e.getMessage();
            }
        }

        return this;
    }

    private Inicializador roles() {
        BuscadorDeFuncionalidades buscadorDePermisos = new BuscadorDeFuncionalidades() {};

        Rol administrador = new Rol();
        administrador.setNombre("Administrador");
        administrador.setRolUsuario(RolUsuario.ADMINISTRADOR);
        administrador.agregarFuncionalidad(
                buscadorDePermisos.buscarPermisoPorNombre("ver_usuarios"),
                buscadorDePermisos.buscarPermisoPorNombre("editar_usuarios"),
                buscadorDePermisos.buscarPermisoPorNombre("carga_masiva")
        );
        entityManager().persist(administrador);

        Rol usuario = new Rol();
        usuario.setNombre("Usuario");
        usuario.setRolUsuario(RolUsuario.MIEMBRO);
        usuario.agregarFuncionalidad(
                buscadorDePermisos.buscarPermisoPorNombre("crear_incidentes"),
                buscadorDePermisos.buscarPermisoPorNombre("ver_incidentes"),
                buscadorDePermisos.buscarPermisoPorNombre("resolver_incidentes")
        );
        entityManager().persist(usuario);

        Rol prestador = new Rol();
        prestador.setNombre("Prestador");
        prestador.setRolUsuario(RolUsuario.PRESTADOR);
        prestador.agregarFuncionalidad(
                buscadorDePermisos.buscarPermisoPorNombre("ver_servicios"),
                buscadorDePermisos.buscarPermisoPorNombre("crear_servicios"),
                buscadorDePermisos.buscarPermisoPorNombre("editar_servicios"),
                buscadorDePermisos.buscarPermisoPorNombre("cambiar_estado_servicios"),
                buscadorDePermisos.buscarPermisoPorNombre("ver_establecimientos"),
                buscadorDePermisos.buscarPermisoPorNombre("crear_establecimientos"),
                buscadorDePermisos.buscarPermisoPorNombre("editar_establecimientos"),
                buscadorDePermisos.buscarPermisoPorNombre("ver_entidades"),
                buscadorDePermisos.buscarPermisoPorNombre("crear_entidades"),
                buscadorDePermisos.buscarPermisoPorNombre("editar_entidades"),
                buscadorDePermisos.buscarPermisoPorNombre("ver_incidentes")
        );
        entityManager().persist(prestador);

        return this;
    }

    public class BuscadorDeFuncionalidades extends EntityManagerHelper{
        private Funcionalidad buscarPermisoPorNombre(String nombre) {
            return (Funcionalidad) entityManager()
                    .createQuery("FROM " + Funcionalidad.class.getName() + " WHERE nombreInterno = \'" + nombre + "\'")
                    //.setParameter("nombre", nombre)
                    .getSingleResult();
        }
    }
}
