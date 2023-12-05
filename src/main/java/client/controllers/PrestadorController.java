package client.controllers;

import Comunidad.RolUsuario;
import Importadores.ImportadorPrestadores;
import Importadores.ImportadorPrestadoresCSV;
import Prestadores.PrestadorDTO;
import client.models.ModelBase;
import client.models.entities.usuarios.RepositorioDeRoles;
import client.models.entities.usuarios.Rol;
import client.models.repositories.RepositorioDeEntidades;
import client.models.repositories.RepositorioDeEntidadesPropietarias;
import client.models.repositories.RepositorioDeOrganismoDeControl;
import client.models.repositories.RepositorioDeUsuarios;
import dbManager.RolUsuarioConverter;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.javalin.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

import static client.helpers.ControllerHelpers.getRolUsuarioFromSession;

public class PrestadorController {

    RepositorioDeEntidades repositorioDeEntidades = new RepositorioDeEntidades();
    RepositorioDeEntidadesPropietarias repositorioDeEntidadesPropietarias = new RepositorioDeEntidadesPropietarias();
    RepositorioDeOrganismoDeControl repositorioDeOrganismoDeControl = new RepositorioDeOrganismoDeControl();
    RepositorioDeUsuarios repositorioDeUsuarios = new RepositorioDeUsuarios();

    public void cargaMasivaEntidades(Context context) {
        ModelBase model = new ModelBase(getRolUsuarioFromSession(context));
        context.render("/cargaMasivaEntidades.hbs", model.getModel());
    }

    public void descargarPlantillaCsv(Context context) {
        // Set the response headers to indicate it's a CSV file
        context.contentType("text/csv");
        context.header("Content-Disposition", "attachment; filename=PlantillaCargaMasiva.csv");

        // Your data, typically from a list or database
        List<String> csvData = List.of(
                "EntidadPrestadoraServicio,MiPrimerPrestador,MiPrimerEntidad,UsuarioAsociado,Clave123!\n"
                +
                "OrganismoDeControl,MiSegundoPrestador,MiSegundaEntidad|MiTerceraEntidad,OtroUsuario,Clave123!"
        );

        // Write the CSV data to the response output stream
        try {
            context.result(String.join("\n", csvData));
        } catch (Exception e) {
            context.status(500); // Handle the error appropriately
        }
    }

    public void ejecutarCargaMasiva(Context context) {
        UploadedFile streamCsv = context.uploadedFiles().get(0);
        String path = "uploads/" + streamCsv.filename();
        FileUtil.streamToFile(streamCsv.content(), path);
        RolUsuarioConverter converter = new RolUsuarioConverter();
        Rol rolPrestador = new RepositorioDeRoles().getRolSegunEnumRolUsuario(converter.convertToDatabaseColumn(RolUsuario.PRESTADOR));
        ImportadorPrestadores importador = new ImportadorPrestadores(new ImportadorPrestadoresCSV(path));
        List<PrestadorDTO> prestadores = importador.importarPrestadores();
        List<String> listadoErrores = new ArrayList<>();
        prestadores.forEach(prestadorDTO->{
            try {
                if(prestadorDTO.getError() != null)
                    throw new Exception();
                if(prestadorDTO.getEntidadPrestadora() != null) {
                    prestadorDTO.getEntidadPrestadora().getUsuario().setRol(rolPrestador);
                    if(repositorioDeUsuarios.buscarPorNombre(prestadorDTO.getEntidadPrestadora().getUsuario().getNombre()) == null)
                        listadoErrores.add("Ya existe un usuario con el nombre del prestador.");
                }
                else if(prestadorDTO.getOrganismoDeControl() != null) {
                    prestadorDTO.getOrganismoDeControl().getUsuario().setRol(rolPrestador);
                    if(repositorioDeUsuarios.buscarPorNombre(prestadorDTO.getOrganismoDeControl().getUsuario().getNombre()) == null)
                        listadoErrores.add("Ya existe un usuario con el nombre del prestador.");
                }
                this.procesarPrestador(prestadorDTO,rolPrestador);
            }
            catch(Exception e) {
                if(e.getMessage()==null)//TODO prestador.getError() != null, y el catch puede mantenerse para los casos de la linea 75.
                    listadoErrores.add(prestadorDTO.getError());
                else
                    listadoErrores.add("Ocurrio un error al guardar al prestador numero " + prestadorDTO.getNumeroPrestador());
            }
        });

        context.contentType("text/csv");
        context.header("Content-Disposition", "attachment; filename=ResultadosImportacion.csv");
        context.result(String.join("\n", listadoErrores));
    }

    public void procesarPrestador(PrestadorDTO prestadorDTO, Rol rolPrestador){
        if(prestadorDTO.getEntidadPrestadora() != null) {
                repositorioDeEntidadesPropietarias.guardar(prestadorDTO.getEntidadPrestadora());
        }
        else if(prestadorDTO.getOrganismoDeControl() != null) {
            repositorioDeOrganismoDeControl.guardar(prestadorDTO.getOrganismoDeControl());
        }
    }
}
