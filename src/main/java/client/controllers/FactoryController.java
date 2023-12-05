package client.controllers;

import client.models.repositories.RepositorioDeIncidentes;
import client.models.repositories.RepositorioDeUsuarios;
import org.jetbrains.annotations.NotNull;

public class FactoryController {
    public static Object controller(@NotNull String nombre) {
        Object controller = null;
        switch (nombre) {
            case "Usuarios": controller = new UsuarioController(new RepositorioDeUsuarios()); break;
            case "Incidentes": controller = new IncidenteController(new RepositorioDeIncidentes()); break;
            case "Entidad":  controller = new EntidadController(); break;
            case "Prestador":  controller = new PrestadorController(); break;
            case "Establecimiento":  controller = new EstablecimientoController(); break;
            case "Servicio": controller = new ServicioController(); break;
            case "Comunidades": controller = new ComunidadController(); break;
            default: break;
        }
        return controller;
    }
}
