package client.server;

import Comunidad.RolUsuario;
import client.controllers.*;

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
        Server.app().get("/usuarios", ((UsuarioController) FactoryController.controller("Usuarios"))::usuarios);
        Server.app().get("/usuarios/{id_usuario}", ((UsuarioController) FactoryController.controller("Usuarios"))::usuario);
        Server.app().post("/usuarios/{id_usuario}", ((UsuarioController) FactoryController.controller("Usuarios"))::editarUsuario);

        //--------------------------------------ENTIDADES---------------------------------------//
        Server.app().get("/entidades", ((EntidadController) FactoryController.controller("Entidad"))::listadoEntidades);
        Server.app().get("/entidades/{id_entidad}", ((EntidadController) FactoryController.controller("Entidad"))::entidad);

        //--------------------------------------ESTABLECIMIENTOS---------------------------------------//
        Server.app().post("/entidades/{id_entidad}/establecimientos/nuevoEstablecimiento", ((EstablecimientoController) FactoryController.controller("Establecimiento"))::nuevoEstablecimiento);//"/establecimientos/nuevo" TODO REST
        Server.app().get("/establecimientos/{id_establecimiento}", ((EstablecimientoController) FactoryController.controller("Establecimiento"))::establecimiento,RolUsuario.PRESTADOR,RolUsuario.ADMINISTRADOR);
        Server.app().post("/establecimientos/{id_establecimiento}", ((EstablecimientoController) FactoryController.controller("Establecimiento"))::guardarEstablecimiento);
        //Server.app().post("/",((EstablecimientoController) FactoryController.controller("Establecimiento"))::eliminarEstablecimiento); //TODO Completar

        //--------------------------------------SERVICIOS---------------------------------------//
        Server.app().post("/establecimiento/{id_establecimiento}/servicio/nuevo", ((ServicioController) FactoryController.controller("Servicio"))::nuevoServicio);
        Server.app().get("/servicios/{id_servicio}", ((ServicioController) FactoryController.controller("Servicio"))::servicio);
        //Server.app().delete("/borrarServicio/{id_servicio}", ((ServicioController) FactoryController.controller("Servicio"))::cambiarEstadoServicio);
        Server.app().post("/servicios/cambiarEstadoServicio/{id_servicio}", ((ServicioController) FactoryController.controller("Servicio"))::cambiarEstadoServicio);
        Server.app().post("/servicios/editarServicio/{id_servicio}", ((ServicioController) FactoryController.controller("Servicio"))::editarServicio);

        //--------------------------------------CARGA MASIVA ENTIDADES---------------------------------------//
        Server.app().get("/CargaMasivaEntidades", ((PrestadorController) FactoryController.controller("Prestador"))::cargaMasivaEntidades);
        Server.app().get("/CargaMasivaEntidades/DescargarPlantillaCsv", ((PrestadorController) FactoryController.controller("Prestador"))::descargarPlantillaCsv);
        Server.app().post("/CargaMasivaEntidades/EjecutarCargaMasiva", ((PrestadorController) FactoryController.controller("Prestador"))::ejecutarCargaMasiva);


        //--------------------------------------INCIDENTES---------------------------------------//
        Server.app().get("/incidents", ((IncidenteController) FactoryController.controller("Incidentes"))::mostrarIncidentes);
        Server.app().post("/newincident", ((IncidenteController) FactoryController.controller("Incidentes"))::nuevoIncidente); //"/incidents/" TODO REST
        Server.app().post("/resolveincident/{id_incidente}", ((IncidenteController) FactoryController.controller("Incidentes"))::resolverIncidente);// "/incidentes/{id_incidente}/resolver" TODO REST
        Server.app().get("/rankingIncidentes", ((IncidenteController) FactoryController.controller("Incidentes"))::mostrarRanking);

        //--------------------------------------COMUNIDADES---------------------------------------//
        Server.app().get("/comunidades/id_usuario", ((ComunidadController) FactoryController.controller("Comunidades"))::mostrarComunidades);
        Server.app().post("/comunidades/{id_comunidad}/unirse/{id_usuario}", ((ComunidadController) FactoryController.controller("Comunidades"))::nuevoMiembro);


        //--------------------------------------ERRORES---------------------------------------//
        Server.app().get("/michis", context -> {context.render("michis.hbs");});
        Server.app().error(404, context -> {
                context.redirect("/michis");
        });
        Server.app().error(500, context -> {
            context.redirect("/michis");
        });
    }
}
