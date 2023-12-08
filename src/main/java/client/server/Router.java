package client.server;

import Comunidad.RolUsuario;
import client.controllers.*;
import client.models.ModelBase;

import static client.helpers.ControllerHelpers.generarMapModelBase;

public class Router {
    public static void init() {
        //--------------------------------------LANDING---------------------------------------//
        Server.app().get("/", context -> {context.render("landing.hbs");});

        //--------------------------------------REGISTRO---------------------------------------//
        Server.app().get("/signup", ((UsuarioController) FactoryController.controller("Usuarios"))::mostrarRegistro);
        Server.app().post("/validarPassword", ((UsuarioController) FactoryController.controller("Usuarios"))::validarPassword);
        Server.app().post("/signup", ((UsuarioController) FactoryController.controller("Usuarios"))::signup);

        //--------------------------------------LOGIN---------------------------------------//
        Server.app().get("/login", ((UsuarioController) FactoryController.controller("Usuarios"))::mostrarLogin);
        Server.app().post("/login", ((UsuarioController) FactoryController.controller("Usuarios"))::login);
        Server.app().post("/logout", ((UsuarioController) FactoryController.controller("Usuarios"))::cerrarSesion);


        //--------------------------------------FUNCIONALIDADES USUARIO---------------------------------------//
        Server.app().get("/menu/", ((UsuarioController) FactoryController.controller("Usuarios"))::mostrarMenu);
        //Server.app().post("/borrarUsuario/{id_usuario}", ((UsuarioController) FactoryController.controller("Usuarios"))::login);
        Server.app().get("/usuarios", ((UsuarioController) FactoryController.controller("Usuarios"))::usuarios, RolUsuario.ADMINISTRADOR);
        Server.app().get("/usuarios/{id_usuario}", ((UsuarioController) FactoryController.controller("Usuarios"))::usuario);
        Server.app().post("/usuarios/{id_usuario}", ((UsuarioController) FactoryController.controller("Usuarios"))::editarUsuario);

        //--------------------------------------ENTIDADES---------------------------------------//
        Server.app().get("/entidades", ((EntidadController) FactoryController.controller("Entidad"))::listadoEntidades, RolUsuario.PRESTADOR, RolUsuario.ADMINISTRADOR);
        Server.app().get("/entidades/{id_entidad}", ((EntidadController) FactoryController.controller("Entidad"))::entidad);

        //--------------------------------------ESTABLECIMIENTOS---------------------------------------//
        Server.app().get("/entidades/establecimiento/nuevo/provincias/{id_provincia}", ((EstablecimientoController) FactoryController.controller("Establecimiento"))::provinciaElegidaNuevoEstablecimiento, RolUsuario.PRESTADOR, RolUsuario.ADMINISTRADOR);
        Server.app().post("/entidades/{id_entidad}/establecimientos/nuevoEstablecimiento", ((EstablecimientoController) FactoryController.controller("Establecimiento"))::nuevoEstablecimiento, RolUsuario.PRESTADOR, RolUsuario.ADMINISTRADOR);//"/establecimientos/nuevo" TODO REST
        Server.app().get("/establecimientos/{id_establecimiento}", ((EstablecimientoController) FactoryController.controller("Establecimiento"))::establecimiento, RolUsuario.PRESTADOR, RolUsuario.ADMINISTRADOR);
        Server.app().post("/establecimientos/{id_establecimiento}", ((EstablecimientoController) FactoryController.controller("Establecimiento"))::guardarEstablecimiento, RolUsuario.PRESTADOR, RolUsuario.ADMINISTRADOR);
        Server.app().post("/{id_entidad}/establecimientos/{id_establecimiento}/eliminar",((EstablecimientoController) FactoryController.controller("Establecimiento"))::eliminarEstablecimiento, RolUsuario.PRESTADOR, RolUsuario.ADMINISTRADOR); //TODO Completar

        //--------------------------------------SERVICIOS---------------------------------------//
        Server.app().post("/establecimiento/{id_establecimiento}/servicio/nuevo", ((ServicioController) FactoryController.controller("Servicio"))::nuevoServicio, RolUsuario.PRESTADOR, RolUsuario.ADMINISTRADOR);
        Server.app().get("establecimientos/{id_establecimiento}/servicios/{id_servicio}", ((ServicioController) FactoryController.controller("Servicio"))::servicio, RolUsuario.PRESTADOR, RolUsuario.ADMINISTRADOR);
        //Server.app().delete("/borrarServicio/{id_servicio}", ((ServicioController) FactoryController.controller("Servicio"))::cambiarEstadoServicio);
        Server.app().post("/servicios/cambiarEstadoServicio/{id_servicio}", ((ServicioController) FactoryController.controller("Servicio"))::cambiarEstadoServicio, RolUsuario.PRESTADOR, RolUsuario.ADMINISTRADOR);
        Server.app().post("/servicios/{id_servicio}/editar", ((ServicioController) FactoryController.controller("Servicio"))::editarServicio, RolUsuario.PRESTADOR, RolUsuario.ADMINISTRADOR);

        //--------------------------------------CARGA MASIVA ENTIDADES---------------------------------------//
        Server.app().get("/CargaMasivaEntidades", ((PrestadorController) FactoryController.controller("Prestador"))::cargaMasivaEntidades, RolUsuario.ADMINISTRADOR);
        Server.app().get("/CargaMasivaEntidades/DescargarPlantillaCsv", ((PrestadorController) FactoryController.controller("Prestador"))::descargarPlantillaCsv, RolUsuario.ADMINISTRADOR);
        Server.app().post("/CargaMasivaEntidades/EjecutarCargaMasiva", ((PrestadorController) FactoryController.controller("Prestador"))::ejecutarCargaMasiva, RolUsuario.ADMINISTRADOR);


        //--------------------------------------INCIDENTES---------------------------------------//
        Server.app().get("/incidentes/", ((IncidenteController) FactoryController.controller("Incidentes"))::mostrarIncidentes);
        Server.app().post("/incidentes/", ((IncidenteController) FactoryController.controller("Incidentes"))::nuevoIncidente); //"/incidents/" TODO REST
        Server.app().post("/incidentes/{id_incidente}/resolver/{id_comunidad}", ((IncidenteController) FactoryController.controller("Incidentes"))::resolverIncidente);// "/incidentes/{id_incidente}/resolver" TODO REST
        Server.app().get("/rankingIncidentes/", ((IncidenteController) FactoryController.controller("Incidentes"))::mostrarRanking);

        //--------------------------------------COMUNIDADES---------------------------------------//
        Server.app().get("/comunidades/", ((ComunidadController) FactoryController.controller("Comunidades"))::mostrarComunidades);
        Server.app().post("/comunidades/{id_comunidad}/unirse/", ((ComunidadController) FactoryController.controller("Comunidades"))::nuevoMiembro);
        Server.app().post("/comunidades/{id_comunidad}/salir/", ((ComunidadController) FactoryController.controller("Comunidades"))::salirComunidad);
        Server.app().post("/comunidades/nueva/", ((ComunidadController) FactoryController.controller("Comunidades"))::nuevaComunidad);

        //--------------------------------------RankingIncidentes---------------------------------------//
        Server.app().get("/incidentes/ranking", ((RankingController) FactoryController.controller("Ranking"))::generarRankingEntidades);
        Server.app().post("/incidentes/ranking/exportar", ((RankingController) FactoryController.controller("Ranking"))::exportarRanking);
        //--------------------------------------ERRORES---------------------------------------//
        Server.app().get("/michis", context -> {
                ModelBase modelBase = new ModelBase(generarMapModelBase(context));
                context.render("michis.hbs",modelBase.getModel());
        });
        Server.app().error(404, context -> {
                context.redirect("/michis");
        });
        Server.app().error(500, context -> {
            context.redirect("/michis");
        });
    }
}
