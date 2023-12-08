package client.server.middleware;

import Comunidad.RolUsuario;
import client.models.entities.usuarios.Rol;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.security.RouteRole;

import java.util.Arrays;
import java.util.Set;

public class MiddlewareAutenticacion {
    private static final String[] urlsSinLogin = {
            "/login",
            "/",
            "/signup",
            "/validarPassword",
            "/logout"
    };
    public static void apply(JavalinConfig config) {
            config.accessManager(((handler, context, routeRoles) -> {

                if(!esUrlsSinLogin(context.matchedPath())) {
                    if(!existeSesion(context))
                    {
                        context.status(HttpStatus.UNAUTHORIZED);
                        context.req().getSession(false).invalidate();
                        context.redirect("/login");
                    }
                    if(!existeOPoseePermiso(routeRoles,context)) {
                        context.status(HttpStatus.NOT_FOUND);
                        context.redirect("/michis");
                    }
                }
                handler.handle(context);
            }));
    }

    private static boolean existeOPoseePermiso(Set<? extends RouteRole> routeRoles, Context context) {
        RolUsuario rolUsuario = getRolUsuario(context);
        return routeRoles.isEmpty() || (rolUsuario != null && routeRoles.contains(rolUsuario));
    }

    private static Integer getIdUsuario(Context context) {
        return context.req().getSession().getAttribute("idUsuario") != null ?
                (Integer) context.req().getSession().getAttribute("idUsuario") :
                null;
    }
    private static RolUsuario getRolUsuario(Context context) {
        return context.req().getSession().getAttribute("rolUsuario") != null ?
                ((Rol) context.req().getSession().getAttribute("rolUsuario")).getRolUsuario() :
                null;
    }

    private static String getNombreUsuario(Context context) {
        return context.req().getSession().getAttribute("nombre") != null ?
                (String) context.req().getSession().getAttribute("nombre") :
                null;
    }

    private static boolean existeSesion(Context context) {
        return getIdUsuario(context) != null && getRolUsuario(context) != null && getNombreUsuario(context) != null;
    }

    private static boolean esUrlsSinLogin(String url) {
        return Arrays.stream(urlsSinLogin).anyMatch(x->x.contentEquals(url));
    }
}
