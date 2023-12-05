package client.server;

import Comunidad.RolUsuario;
import client.helpers.HandlebarsHelpers;
import client.server.init.Inicializador;
import client.server.middleware.MiddlewareAutenticacion;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.JavalinRenderer;

import java.io.IOException;
import java.util.function.Consumer;
public class Server {
    private static Javalin app = null;

    public static Javalin app() {
        if(app == null)
            throw new RuntimeException("App no inicializada");
        return app;
    }

    public static void init() {
        if(app == null) {
            Integer port = Integer.parseInt(System.getProperty("port", "8080"));
            app = Javalin.create(config()).start(port);
            initTemplateEngine();
            Router.init();
        }
            //Inicializador.init();
    }

    private static Consumer<JavalinConfig> config() {
        return config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "/public";
            });
            MiddlewareAutenticacion.apply(config);
        };
    }

    static RolUsuario getRolUsuario(Context context) {
        return (RolUsuario) context.req().getSession().getAttribute("rolUsuario");
    }

    private static void initTemplateEngine() {
        JavalinRenderer.register(
                (path, model, context) -> { // Función que renderiza el template
                    Handlebars handlebars = new Handlebars();
                    handlebars.registerHelper("validarRolAdmin", HandlebarsHelpers.esAdministradorHandlebars);
                    handlebars.registerHelper("validarRolPrestador", HandlebarsHelpers.esPrestadorHandlebars);
                    Template template = null;
                    try {
                        template = handlebars.compile(
                                "templates/" + path.replace(".hbs",""));
                        return template.apply(model);
                    } catch (IOException e) {
                        e.printStackTrace();
                        context.status(HttpStatus.NOT_FOUND);
                        return "No se encuentra la página indicada...";
                    }
                }, ".hbs" // Extensión del archivo de template
        );
    }
}