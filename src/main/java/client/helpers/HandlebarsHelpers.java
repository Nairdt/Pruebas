package client.helpers;

import com.github.jknack.handlebars.Helper;

import java.util.Objects;

public class HandlebarsHelpers {
    public static Helper<Object> esAdministradorHandlebars = ((context, options) -> {
        if(context != null) {
            if(context.toString().contains("ADMINISTRADOR"))
                return true;
        }
        return false;
    });

    public static Helper<Object> esPrestadorHandlebars = ((context, options) -> {
        if(context != null) {
            if(context.toString().contains("PRESTADOR"))
                return true;
        }
        return false;
    });

    public static Helper<Object> esMiembroHandlebars = ((context, options) -> {
        if(context != null) {
            if(context.toString().contains("MIEMBRO"))
                return true;
        }
        return false;
    });
}
