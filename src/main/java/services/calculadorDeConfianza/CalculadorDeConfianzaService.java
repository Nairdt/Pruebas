package services.calculadorDeConfianza;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import services.calculadorDeConfianza.entities.GradoDeConfianza;
import services.calculadorDeConfianza.entities.ListadoDeConfianza;
import services.calculadorDeConfianza.entities.PayloadDTO;

public interface CalculadorDeConfianzaService {
    @POST("/api/actualizacion")
    Call<ListadoDeConfianza> actualizarGradoDeConfianza(@Body PayloadDTO request);

    @GET("/")
    Call<Void> pruebaAPI();
}
