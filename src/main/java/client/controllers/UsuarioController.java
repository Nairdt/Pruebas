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

import static client.helpers.ControllerHelpers.*;

public class UsuarioController extends EntityManagerHelper implements ICrudViewsHandler {
    RepositorioDeUsuarios repositorioDeUsuarios;
    RepositorioDeRoles repositorioDeRoles;
    
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
        ModelBase model = new ModelBase(generarMapModelBase(context));
        context.render("menu.hbs",model.getModel());
    }



    public void login(Context context) {
        String email = context.formParam("email");
        String password = context.formParam("password");
        Usuario usuario = usuario = repositorioDeUsuarios.usuariosCompatibles(email,password);
        if(usuario != null){
            setearSesion(context,usuario);
            context.res().setStatus(200);
            context.json("OK");
        }
        else {
            context.json("Usuario o contraseña incorrectos.");
        }
    }

    public void validarPassword(Context context) {
        String txtPassword = context.body();
        context.json(validadorPasswords.cumpleValidaciones(txtPassword));
    }

    public void cerrarSesion(Context context) {
        context.req().getSession(false).invalidate();
        context.res().setStatus(200);

        context.json("Sesion cerrada");
    }

    public void signup(Context context) {
        String nombre = context.formParam("nombre");
        String email = context.formParam("email");
        String telefono = context.formParam("telefono");
        String password = context.formParam("password");
        String tipoUsuario = context.formParam("tipoUsuario").toUpperCase();
        if(repositorioDeUsuarios.existeMail(email)){
            context.html("<script>alert('Ya existe un usuario con ese mail, usalo para iniciar sesión');window.location.href='/login';</script>");
        }else{
            Rol rol = repositorioDeRoles.getRolSegunEnumRolUsuario(tipoUsuario);
            Usuario unUsuario = new Usuario(nombre, password, email, rol, telefono);

            repositorioDeUsuarios.guardar(unUsuario);

            setearSesion(context,unUsuario);

            ModelBase model = new ModelBase(generarMapModelBase(context));;
            model.put("nombre", nombre);
            context.render("/menu.hbs", model.getModel());
        }
    }

    public void editarUsuario(Context context){
        int idUsuario = getIdUsuarioFromSession(context);
        String mensajesError = "OK";

        try {
            int idUsuarioEdicion = Integer.parseInt(context.pathParam("id_usuario"));
            String user = context.formParam("nombre");
            String telefono = context.formParam("telefono");
            String mail = context.formParam("email");
            String clave = context.formParam("password");
            String claveValidar = context.formParam("validarPassword");
            Boolean cambiaClave = Boolean.getBoolean(context.formParam("cambiaPassword"));

            Usuario usuario = (Usuario) this.repositorioDeUsuarios.buscarPorId(idUsuarioEdicion);

            Boolean editaUserPropio = idUsuario == idUsuarioEdicion;
            Boolean esAdmin = getRolUsuarioFromSession(context) == RolUsuario.ADMINISTRADOR;

            if(editaUserPropio || esAdmin) {

                if(cambiaClave)
                    if(!esAdmin ||
                      (!claveValidar.isEmpty() && editaUserPropio && !usuario.getClave().equals(claveValidar))
                    )
                        throw new Exception("La clave actual ingresada es incorrecta.");
                if(user == null || user.isEmpty())
                    throw new Exception("Falta completar el nombre de usuario.");
                if(mail == null || mail.isEmpty())
                    throw new Exception("Falta completar el mail del usuario.");
                if(telefono == null || telefono.isEmpty())
                    throw new Exception("Falta completar el telefono del usuario.");
                if(cambiaClave && clave != null && !clave.isEmpty())
                        usuario.setClave(clave);
                usuario.setNombre(user);
                usuario.setMail(mail);
                usuario.setTelefono(telefono);

                repositorioDeUsuarios.guardar(usuario);
            }
        }
        catch (Exception e) {
            mensajesError = e.getMessage();
        }
        context.res().setStatus(200);
        context.json(mensajesError);
    }

    public void usuarios(Context context){
        List<Usuario> usuarios = repositorioDeUsuarios.buscarTodos();
        ModelBase model = new ModelBase(generarMapModelBase(context));
        model.put("usuarios", usuarios);
        context.render("adminUsuarios.hbs", model.getModel());
    }

    public void usuario(Context context){
        int id_usuario = Integer.parseInt(context.pathParam("id_usuario"));
        Usuario usuario = (Usuario) repositorioDeUsuarios.buscarPorId(id_usuario);
        ModelBase model = new ModelBase(generarMapModelBase(context));
        model.put("usuario", usuario);
        context.render("usuario.hbs", model.getModel());
    }


    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {
        Usuario usuario = (Usuario) this.repositorioDeUsuarios.buscar(Long.parseLong(context.pathParam("id")));
        ModelBase model = new ModelBase(generarMapModelBase(context));
        model.put("usuario", usuario);
        context.render("Usuarios/Usuario.hbs", model.getModel());
    }

    @Override
    public void create(Context context) {
        Usuario usuario = null;
        ModelBase model = new ModelBase(generarMapModelBase(context));
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
        ModelBase model = new ModelBase(generarMapModelBase(context));
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

}
