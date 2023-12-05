package Comunidad;

import io.javalin.security.RouteRole;

public enum RolUsuario implements RouteRole {
    MIEMBRO,
    ADMINISTRADOR,
    PRESTADOR,
    PROVEEDOR;

    public static boolean esAdministrador(RolUsuario rolUsuario) {
        return rolUsuario == RolUsuario.ADMINISTRADOR;
    }

    public static boolean esPrestador(RolUsuario rolUsuario) {
        return rolUsuario == RolUsuario.PRESTADOR;
    }
}


