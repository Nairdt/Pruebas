package services.generadorDeRanking;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import services.generadorDeRanking.entities.RequestEntidadDTO;
import services.generadorDeRanking.entities.ResponseEntidadDTO;

import java.util.List;

public interface GeneradorDeRankingService {
    @POST("/RankingImpactoIncidentes/GenerarRanking")
    Call<List<ResponseEntidadDTO>> generarRanking(@Body List<RequestEntidadDTO> request);

    @POST("/RankingImpactoIncidentes/CambiarCNF/{nuevoCnf}")
    Call<Void> cambiarCnf(@Path("nuevoCnf") int nuevoCnf);
}
