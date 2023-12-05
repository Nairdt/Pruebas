package client.helpers;

import Comunidad.RolUsuario;
import Comunidad.Usuario;
import client.models.entities.usuarios.Rol;
import io.javalin.http.Context;

public class ControllerHelpers {

    public static RolUsuario getRolUsuarioFromSession(Context context) {
        Rol rol = context.sessionAttribute("rolUsuario");
        return rol.getRolUsuario() != null ?
                rol.getRolUsuario() :
                null;
    }

    public static void setearSesion(Context context, Usuario usuarioLogueado) {
        context.sessionAttribute("idUsuario",usuarioLogueado.getId_usuario());
        context.sessionAttribute("rolUsuario",usuarioLogueado.getRol());
    }
}
