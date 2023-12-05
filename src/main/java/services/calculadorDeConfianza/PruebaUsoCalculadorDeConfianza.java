package services.calculadorDeConfianza;

import Comunidad.Usuario;
import Organizaciones.Entidad;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import services.calculadorDeConfianza.entities.*;
import services.generadorDeRanking.ServicioGeneradorDeRanking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PruebaUsoCalculadorDeConfianza {
    public static void main(String[] args) throws IOException {
        ServicioCalculadorDeConfianza calculadorDeConfianza = ServicioCalculadorDeConfianza.getInstancia();

        //Prueba de función de la API
        System.out.println("Prueba si la API se conecta");
        calculadorDeConfianza.pruebaAPI();

        //Prueba de calculador de confianza
        System.out.println("Prueba de Calculador de Confianza\n");

        PayloadDTO payloadRequest = new PayloadDTO();
        GradoDeConfianza gradoConfianzaUsuario1 = new GradoDeConfianza();
        gradoConfianzaUsuario1.setPuntosMinimos(1.5);
        gradoConfianzaUsuario1.setPuntosMaximos(1.8);
        GradoDeConfianza gradoConfianzaUsuario2 = new GradoDeConfianza();
        gradoConfianzaUsuario2.setPuntosMinimos(1.3);
        gradoConfianzaUsuario2.setPuntosMaximos(2.0);
        GradoDeConfianza gradoConfianzaComunidad = new GradoDeConfianza();
        gradoConfianzaComunidad.setPuntosMinimos(1.5);
        gradoConfianzaComunidad.setPuntosMaximos(1.7);
        UsuarioConfianza usuarioConfianza1 = new UsuarioConfianza();
        usuarioConfianza1.setId(1);
        usuarioConfianza1.setPuntosDeConfianza(5);
        usuarioConfianza1.setGradoDeConfianza(gradoConfianzaUsuario1);
        UsuarioConfianza usuarioConfianza2 = new UsuarioConfianza();
        usuarioConfianza2.setId(2);
        usuarioConfianza2.setPuntosDeConfianza(5);
        usuarioConfianza2.setGradoDeConfianza(gradoConfianzaUsuario2);
        ComunidadConfianza comunidadConfianza = new ComunidadConfianza();
        comunidadConfianza.setId(1);
        comunidadConfianza.setPuntosDeConfianza(9);
        comunidadConfianza.setGradoDeConfianza(gradoConfianzaComunidad);
        comunidadConfianza.agregarUsuarioAComunidad(usuarioConfianza2);
        EntidadConfianza entidadConfianza = new EntidadConfianza();
        entidadConfianza.setId(11);
        entidadConfianza.setNombre("John Doe");
        EstablecimientoConfianza establecimientoConfianza = new EstablecimientoConfianza();
        establecimientoConfianza.setId(12);
        establecimientoConfianza.setNombre("Natalia Natalia");
        ServicioConfianza servicioConfianza = new ServicioConfianza();
        servicioConfianza.setId(13);
        servicioConfianza.setNombre("Jane Doe");
        servicioConfianza.setEstado(true);
        PrestacionDeServicio prestacionServicio = new PrestacionDeServicio();
        prestacionServicio.setEntidad(entidadConfianza);
        prestacionServicio.setEstablecimiento(establecimientoConfianza);
        prestacionServicio.setServicio(servicioConfianza);
        IncidenteConfianza incidenteConfianza = new IncidenteConfianza();
        incidenteConfianza.setId(10);
        incidenteConfianza.setDescripcion("Blabla");
        incidenteConfianza.setEstado(true);
        incidenteConfianza.setPrestacionDeServicio(prestacionServicio);
        incidenteConfianza.setFechaApertura("9-12-2018");
        incidenteConfianza.setFechaCierre("18-12-2022");

        payloadRequest.agregarUsuario(usuarioConfianza1);
        payloadRequest.agregarUsuario(usuarioConfianza2);
        payloadRequest.agregarComunidad(comunidadConfianza);
        payloadRequest.agregarIncidente(incidenteConfianza);

        ListadoDeConfianza responseListado = new ListadoDeConfianza();
        responseListado = calculadorDeConfianza.actualizarGradoDeConfianza(payloadRequest);
    }
}
