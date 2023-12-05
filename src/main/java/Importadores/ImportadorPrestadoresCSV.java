package Importadores;
import Comunidad.RolUsuario;
import Comunidad.Usuario;
import Prestadores.EntidadPrestadoraServicio;
import Prestadores.OrganismoDeControl;
import Organizaciones.Entidad;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Prestadores.PrestadorDTO;
import client.models.entities.usuarios.Rol;
import org.springframework.context.annotation.ScopedProxyMode;

public class ImportadorPrestadoresCSV implements Importable{

    private String rutaArchivoCSV;
    private int contadorPrestadoresProcesados;
    public ImportadorPrestadoresCSV(String rutaArchivoCSV) {
        this.rutaArchivoCSV = rutaArchivoCSV;
        this.contadorPrestadoresProcesados = 0;
    }
    public PrestadorDTO convertirPrestador(String[] listadoTokensCSV) throws Exception {
        PrestadorDTO prestadorDTO = new PrestadorDTO();

        try {
            contadorPrestadoresProcesados++;
            prestadorDTO.setNumeroPrestador(contadorPrestadoresProcesados);
            /*
            Formato del CSV:
            [0] = TipoPrestador, [1] = Nombre prestador,
            [2] = Entidad/Entidades asociadas al prestador,
            [3] = Nombre Usuario, [4] = Clave usuario
            Nota: Dado el caso de que sea un organismo de control,
                  este contendra entre comas un listado de Entidades separadas mediante el caracter "|".
                  Por una cuestion de tokenizacion, el nombre de una entidad/organismo no puede tener comas
            */
            String tokenTipoPrestador = listadoTokensCSV[0];
            //Corregir posiciones CSV
            Usuario usuario = new Usuario(listadoTokensCSV[3], listadoTokensCSV[4], new Rol(RolUsuario.PRESTADOR), null, null, null,null); //Info de usuario

            if(tokenTipoPrestador.toLowerCase().contains("entidadprestadoraservicio")) {
                Entidad entidad = new Entidad(listadoTokensCSV[2]);
                EntidadPrestadoraServicio entidadPrestadoraServicio = new EntidadPrestadoraServicio(listadoTokensCSV[1],entidad,usuario);
                entidadPrestadoraServicio.getEntidad().setEntidadPrestadora(entidadPrestadoraServicio);//Fix para los JOINS de persistencia
                prestadorDTO.setEntidadPrestadora(entidadPrestadoraServicio);
                prestadorDTO.setOrganismoDeControl(null);
            }
            else if(tokenTipoPrestador.toLowerCase().contains("organismodecontrol")) {
                //Genero listado de entidades NoBorrar importar al ODC
                List<String> listadoNombresEntidadesCSV = Arrays.stream(listadoTokensCSV[2].split("[|]")).toList();
                List<Entidad> listadoEntidades = new ArrayList<>();
                listadoNombresEntidadesCSV.forEach(nombreEntidad->listadoEntidades.add(new Entidad(nombreEntidad)));

                OrganismoDeControl organismoDeControl = new OrganismoDeControl(listadoTokensCSV[1],listadoEntidades,usuario);
                organismoDeControl.getListadoEntidades().forEach(entidad->entidad.setOrganismoDeControl(organismoDeControl));//Fix para los JOINS de persistencia
                prestadorDTO.setOrganismoDeControl(organismoDeControl);
                prestadorDTO.setEntidadPrestadora(null);
            }
            else
                throw new Exception("Error al importar, no existe el tipo de prestador");
        }
        catch (Exception e) {
            String mensaje;
                mensaje = e.getMessage().contains("out of bounds") ?
                        "Revise que el prestador posea todos los campos" :
                        e.getMessage();
            prestadorDTO.setError("Ocurrio un error al procesar al prestador numero "
                    + prestadorDTO.getNumeroPrestador()
                    + " : "
                    + mensaje
            );
        }
        return prestadorDTO;
    }
    @Override
    public List<PrestadorDTO> importarDatosPrestadoresServicios(){
        String lineaCSV;
        List<PrestadorDTO> listadoPrestadores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivoCSV))) {
            while ((lineaCSV = br.readLine()) != null) {
                String[] tokensCSV = lineaCSV.split(",");
                try {
                    listadoPrestadores.add(convertirPrestador(tokensCSV));
                } catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Ocurrio un error al procesar el archivo CSV");
        }
        return listadoPrestadores;
    }
}