package client.helpers;

import Comunidad.RolUsuario;
import Comunidad.Usuario;
import client.models.entities.usuarios.Rol;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class ControllerHelpers {
    private static final String attrNombre = "nombre";
    private static final String attrIdUsuario = "idUsuario";
    private static final String attrRolUsuario = "rolUsuario";
    public static RolUsuario getRolUsuarioFromSession(Context context) {
        Rol rol = context.sessionAttribute(attrRolUsuario);
        if(rol == null)
            return null;
        else
            return rol.getRolUsuario() != null ?
                    rol.getRolUsuario() :
                    null;
    }

    public static String getNombreFromSession(Context context){
        String nombre = context.sessionAttribute(attrNombre);
        return nombre != null? nombre : "";
    }

    public static Integer getIdUsuarioFromSession(Context context){
        Integer idUsuario = context.sessionAttribute(attrIdUsuario);
        return idUsuario != null? idUsuario : -1;
    }

    public static void setearSesion(Context context, Usuario usuarioLogueado) {
        context.sessionAttribute(attrIdUsuario,usuarioLogueado.getId_usuario());
        context.sessionAttribute(attrRolUsuario,usuarioLogueado.getRol());
        context.sessionAttribute(attrNombre,usuarioLogueado.getNombre());
    }

    public static Map<String,Object> generarMapModelBase(Context ctx) {
        Map<String,Object> mapBase = new HashMap<>();
        mapBase.put(attrIdUsuario,getIdUsuarioFromSession(ctx));
        mapBase.put(attrRolUsuario,getRolUsuarioFromSession(ctx));
        mapBase.put(attrNombre,getNombreFromSession(ctx));
        return mapBase;
    }
}
