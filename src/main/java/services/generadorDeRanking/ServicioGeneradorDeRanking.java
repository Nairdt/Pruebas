package services.generadorDeRanking;

import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import services.generadorDeRanking.entities.RequestEntidadDTO;
import services.generadorDeRanking.entities.ResponseEntidadDTO;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ServicioGeneradorDeRanking {
    private static ServicioGeneradorDeRanking instancia = null;
    private static final String urlAPI = System.getenv("URL_API");
    private Retrofit retrofit;

    private ServicioGeneradorDeRanking() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioGeneradorDeRanking getInstancia() {
        if(instancia == null) {
            instancia = new ServicioGeneradorDeRanking();
        }

        return instancia;
    }

    public void cambiarCnfRanking(int nuevoCnf) throws IOException {
        GeneradorDeRankingService servicioGeneradorDeRanking = this.retrofit.create(GeneradorDeRankingService.class);
        Call<Void> requestCambiarCnfRanking = servicioGeneradorDeRanking.cambiarCnf(nuevoCnf);
        Response<Void> responseCambiarCnfRanking = requestCambiarCnfRanking.execute();

        if(responseCambiarCnfRanking.isSuccessful()) {
            System.out.println("CNF cambiado exitosamente :)");
        } else {
            System.out.println("No se pudo cambiar el CNF :(");
        }
    }

    public List<ResponseEntidadDTO> generarRankingEntidades(List<RequestEntidadDTO> entidades) throws IOException {
        GeneradorDeRankingService servicioGeneradorDeRanking = this.retrofit.create(GeneradorDeRankingService.class);
        Call<List<ResponseEntidadDTO>> requestGenerarRanking = servicioGeneradorDeRanking.generarRanking(entidades);
        Response<List<ResponseEntidadDTO>> responseGenerarRanking = requestGenerarRanking.execute();
        List<ResponseEntidadDTO> ranking;

        if(responseGenerarRanking.isSuccessful()) {
            System.out.println("Ranking generado exitosamente :)");
            ranking = responseGenerarRanking.body();
        } else {
            System.out.println("El Ranking no se genero exitosamente :(");
            ranking = Collections.emptyList(); //TODO manejo de excepciones
        }

        return ranking;
    }
}
