package client.controllers;

import Comunidad.RolUsuario;
import Comunidad.Usuario;
import Validador.*;
import client.helpers.ControllerHelpers;
import client.models.ModelBase;
import client.models.entities.usuarios.RepositorioDeRoles;
import client.models.entities.usuarios.Rol;
import client.models.repositories.RepositorioDeUsuarios;
import client.server.utils.ICrudViewsHandler;

import dbManager.EntityManagerHelper;
import dbManager.RolUsuarioConverter;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static client.helpers.ControllerHelpers.setearSesion;
import static client.helpers.ControllerHelpers.getRolUsuarioFromSession;

public class UsuarioController extends EntityManagerHelper implements ICrudViewsHandler {
    RepositorioDeUsuarios repositorioDeUsuarios;
    Validador validadorPasswords;

    public UsuarioController(RepositorioDeUsuarios repositorioDeUsuarios){
        this.repositorioDeUsuarios = repositorioDeUsuarios;
        this.validadorPasswords  = new Validador();
        validadorPasswords.habilitarValidacion(new ValidacionMinuscula(), new ValidacionMayuscula(), new ValidacionNumeros());
    }

    public void mostrarLogin(Context context){
        context.render("login.hbs");
    }

    public void mostrarMenu(Context context) {
        ModelBase model = new ModelBase(((Rol) context.sessionAttribute("rolUsuario")).getRolUsuario());
        context.render("menu.hbs",model.getModel());
    }



    public void login(Context context) {
        String email = context.formParam("email");
        String password = context.formParam("password");
        Usuario usuario = new Usuario();
        usuario = this.repositorioDeUsuarios.usuariosCompatibles(email,password);
        if(usuario != null){
            Usuario usuarioLogueado = usuario;
            setearSesion(context,usuarioLogueado);

            context.res().setStatus(200);
            context.json("OK");
        }
        else {
            context.json("Usuario o contraseña incorrectos.");
        }
    }

    public void validarPassword(Context context) {
        String txtPassword = context.body();
        context.json(
                validadorPasswords.listarValidaciones(txtPassword)
        );

    }

    public void cerrarSesion(Context context) {
        context.req().getSession(false).invalidate();
        context.res().setStatus(200);

        context.json("Sesion cerrada");
    }

    public void signup(Context context) {
        String nombre = context.formParam("nombre");
        String email = context.formParam("email");
        String password = context.formParam("password");
        String tipoUsuario = context.formParam("tipoUsuario").toUpperCase();
        if(this.repositorioDeUsuarios.existeMail(email)){
            context.html("<script>alert('ERROR, Usuario ya existe');window.location.href='/login';</script>");
        }else{
            Rol rol = new RepositorioDeRoles().getRolSegunEnumRolUsuario(tipoUsuario);
            Usuario unUsuario = new Usuario(nombre, password, email, rol);
            entityManager().getTransaction().begin();
            entityManager().persist(unUsuario);
            entityManager().getTransaction().commit();

            setearSesion(context,unUsuario);

            ModelBase model = new ModelBase(rol.getRolUsuario());
            model.put("nombre", nombre);

            context.render("/menu.hbs", model.getModel());
        }
    }

    public void editarUsuario(Context context){
        int idUsuario = Integer.parseInt(context.pathParam("id_usuario"));
        this.repositorioDeUsuarios.entityManager().getTransaction().begin();

        String user = context.formParam("nombre");
        String mail = context.formParam("email");

        String clave = context.formParam("clave");

        Usuario usuario = (Usuario) this.repositorioDeUsuarios.buscarPorId(idUsuario);

        if(user != null && !user.isEmpty())
            usuario.setNombre(user);
        if(clave != null && !clave.isEmpty())
            usuario.setClave(context.formParam(clave));

        this.repositorioDeUsuarios.entityManager().persist(usuario);
        this.repositorioDeUsuarios.commit();
        this.repositorioDeUsuarios.entityManager().clear();
        context.redirect("/usuarios/");
    }

    public void usuarios(Context context){
        List<Usuario> usuarios = this.repositorioDeUsuarios.buscarTodos();
        ModelBase model = new ModelBase(getRolUsuarioFromSession(context));
        model.put("usuarios", usuarios);
        context.render("adminUsuarios.hbs", model.getModel());
    }

    public void usuario(Context context){
        int id_usuario = Integer.parseInt(context.pathParam("id_usuario"));
        Usuario usuario = (Usuario) this.repositorioDeUsuarios.buscarPorId(id_usuario);
        ModelBase model = new ModelBase(getRolUsuarioFromSession(context));
        model.put("usuario", usuario);
        context.render("usuario.hbs", model.getModel());
    }


    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {
        Usuario usuario = (Usuario) this.repositorioDeUsuarios.buscar(Long.parseLong(context.pathParam("id")));
        ModelBase model = new ModelBase(getRolUsuarioFromSession(context));
        model.put("usuario", usuario);
        context.render("Usuarios/Usuario.hbs", model.getModel());
    }

    @Override
    public void create(Context context) {
        Usuario usuario = null;
        ModelBase model = new ModelBase(getRolUsuarioFromSession(context));
        model.put("Usuario", usuario);
        context.render("Usuarios/Usuario.hbs", model.getModel());
    }

    @Override
    public void save(Context context) {
        Usuario usuario = new Usuario();
//        this.asignarParametros(usuario, context);
        this.repositorioDeUsuarios.guardar(usuario);
        context.status(HttpStatus.CREATED);
        context.redirect("/Usuarios");
    }

    @Override
    public void edit(Context context) {
        Usuario usuario = (Usuario) this.repositorioDeUsuarios.buscar(Long.parseLong(context.pathParam("id")));
        ModelBase model = new ModelBase(getRolUsuarioFromSession(context));
        model.put("Usuario", usuario);
        context.render("Usuarios/Usuario.hbs", model.getModel());
    }

    @Override
    public void update(Context context) {
        Usuario usuario = (Usuario) this.repositorioDeUsuarios.buscar(Long.parseLong(context.pathParam("id")));
        //this.asignarParametros(usuario, context);
        this.repositorioDeUsuarios.actualizar(usuario);
        context.redirect("/Usuarios");
    }

    @Override
    public void delete(Context context) {
        Usuario usuario = (Usuario) this.repositorioDeUsuarios.buscar(Long.parseLong(context.pathParam("id")));
        usuario.setLocalizacionInteres(null);
        this.repositorioDeUsuarios.eliminar(usuario);
        context.redirect("/Usuarios");
    }

    public void mostrarRegistro(Context context) {
        List<String> listadoRequerimientosPassword = this.validadorPasswords.listarValidaciones("");
        Map<String, Object> model = new HashMap<>();
        model.put("listadoRequerimientosPassword", listadoRequerimientosPassword);

        context.render("signup.hbs",model);
    }


//    private void asignarParametros(Usuario Usuario, Context context) {
//        if(!Object.equals(context.formParam("nombre"), "")) {
//            Usuario.setNombre(context.formParam("nombre"));
//        }
//    }

}
