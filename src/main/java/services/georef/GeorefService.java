package services.georef;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import services.georef.entities.*;

public interface GeorefService {
    @GET("provincias")
    Call<ListadoProvincias> provincias();

    @GET("municipios")
    Call<ListadoMunicipios> municipios();

    @GET("departamentos")
    Call<ListadoDepartamentos> departamentos();

    @GET("provincias")
    Call<ListadoProvincias> provincias(@Query("campos") String campos);

    @GET("municipios")
    Call<ListadoMunicipios> municipios(@Query("provincia") int idProvincia, @Query("max") int max);

    @GET("municipios")
    Call<ListadoMunicipios> municipios(@Query("provincia") int idProvincia, @Query("campos") String campos, @Query("max") int max);

    //llamadas para buscar un departamento o municipio especifico
    @GET("departamentos")
    Call<ListadoDepartamentos> departamentos(@Query("provincia") int idProvincia, @Query("exacto") String campoExacto, @Query("nombre") String nombreDepartamento);
    @GET("departamentos")
    Call<ListadoDepartamentos> departamentos(@Query("provincia") int idProvincia);
    @GET("municipios")
    Call<ListadoMunicipios> municipios(@Query("provincia") int idProvincia, @Query("exacto") String campoExacto, @Query("nombre") String nombreMunicipio);
    @GET("provincias")
    Call<ListadoProvincias> provincias(@Query("exacto") String campoExacto, @Query("nombre") String nombreProvincia);
    @GET("localidades")
    Call<ListadoLocalidades> localidades(@Query("campos") String camposLocalidades, @Query("provincia") int idProvincia, @Query("max") int max);
    @GET("localidades")
    Call<ListadoLocalidades> localidades(@Query("categoria") String categoria);
}
