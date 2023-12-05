package client.helpers;

import com.github.jknack.handlebars.Helper;

import java.util.Objects;

public class HandlebarsHelpers {
    public static Helper<Object> esAdministradorHandlebars = ((context, options) -> {
        if(context != null) {
            if(context.toString().contains("ADMINISTRADOR"))
                return options.fn(options);
        }
        return options.inverse(options);
    });

    public static Helper<Object> esPrestadorHandlebars = ((context, options) -> {
        if(context != null) {
            if(context.toString().contains("PRESTADOR"))
                return options.fn(options);
        }
        return options.inverse(options);
    });
}
