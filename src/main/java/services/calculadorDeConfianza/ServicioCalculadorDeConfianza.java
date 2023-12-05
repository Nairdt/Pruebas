package services.calculadorDeConfianza;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import services.calculadorDeConfianza.entities.ListadoDeConfianza;
import services.calculadorDeConfianza.entities.PayloadDTO;

import java.io.IOException;

public class ServicioCalculadorDeConfianza {
    private static ServicioCalculadorDeConfianza instancia = null;
    private static final String urlAPI = "http://localhost:8080";
    private Retrofit retrofit;

    private ServicioCalculadorDeConfianza() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioCalculadorDeConfianza getInstancia() {
        if(instancia == null) {
            instancia = new ServicioCalculadorDeConfianza();
        }

        return instancia;
    }

    public ListadoDeConfianza actualizarGradoDeConfianza(PayloadDTO listado) throws IOException {
        CalculadorDeConfianzaService servicioCalculadorDeConfianza = this.retrofit.create(CalculadorDeConfianzaService.class);
        Call<ListadoDeConfianza> requestActualizarGradoDeConfianza = servicioCalculadorDeConfianza.actualizarGradoDeConfianza(listado);
        Response<ListadoDeConfianza> responseActualizarGradoDeConfianza = requestActualizarGradoDeConfianza.execute();

        if(responseActualizarGradoDeConfianza.code() == 200) {
            return responseActualizarGradoDeConfianza.body();
        } else if(responseActualizarGradoDeConfianza.code() == 404) {
            System.out.println("\nNo se encontró nada :(\nCódigo de error: " + responseActualizarGradoDeConfianza.code());
            return null; //TODO manejo de excepciones, excepciones no chequeadas!!!
        } else {
            System.out.println("\nError en la solicitud: " + responseActualizarGradoDeConfianza.code());
            return null;
        }
    }

    public void pruebaAPI() throws IOException {
        CalculadorDeConfianzaService servicioCalculadorDeConfianza = this.retrofit.create(CalculadorDeConfianzaService.class);
        Call<Void> requestPruebaApi = servicioCalculadorDeConfianza.pruebaAPI();
        Response<Void> responsePruebaApi = requestPruebaApi.execute();

        if(responsePruebaApi.isSuccessful()) {
            System.out.println("Prueba, exitosa:\nHola mundo");
        } else {
            System.out.println("Prueba fallida :(");
        }
    }
}
