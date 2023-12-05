package services.georef;
import Localizacion.Localizable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import services.georef.entities.*;

import java.io.IOException;
import java.util.List;

public class ServicioGeoref implements Localizable {
    private static ServicioGeoref instancia = null;
    private static final String urlAPI = "https://apis.datos.gob.ar/georef/api/";
    private Retrofit retrofit;
    private ServicioGeoref(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioGeoref getInstancia(){
        if(instancia == null){
            instancia = new ServicioGeoref();
        }
        return instancia;
    }

    public ListadoProvincias listadoProvincias() throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoProvincias> requestProvinciasArg = georefService.provincias();
        Response<ListadoProvincias> responseProvinciasArg = requestProvinciasArg.execute();
        return responseProvinciasArg.body();
    }

    public Municipio listadoMunicipiosDeProvincia(int idProvincia) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoMunicipios> requestMunicipiosDeProvincia = georefService.municipios(idProvincia, "id, nombre", 200);
        Response<ListadoMunicipios> responseMunicipiosDeProvincia = requestMunicipiosDeProvincia.execute();
        return responseMunicipiosDeProvincia.body().municipios.get(0);
    }

    public ListadoLocalidades localidadesDeProvincia(int idProvincia) throws IOException{
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoLocalidades> requestLocalidadesProv = georefService.localidades("centroide,departamento,id,municipio,nombre,provincia", idProvincia, 600);
        Response<ListadoLocalidades> responseLocalidades = requestLocalidadesProv.execute();
        return responseLocalidades.body();
    }

    public ListadoDepartamentos listadoDepartamentosDeProvincia(int idProvincia) throws IOException{
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDepartamentos> requestDepartamentos = georefService.departamentos(idProvincia);
        Response<ListadoDepartamentos> responseDepartamentos = requestDepartamentos.execute();
        return responseDepartamentos.body();
    }








    public Departamento departamentoAPartirDeNombreYProvincia(String nombreDepartamento, String nombreProvincia) throws IOException {
        int idProvincia = idDeProvincia(nombreProvincia);
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDepartamentos> requestDepartamento = georefService.departamentos(idProvincia, "nombre", nombreDepartamento);
        Response<ListadoDepartamentos> responseDepartamentos = requestDepartamento.execute();
        return responseDepartamentos.body().departamentos.get(0);
    }

    public Municipio municipioAPartirDeNombreYProvincia(String nombreDepartamento, String nombreProvincia) throws IOException {
        int idProvincia = idDeProvincia(nombreProvincia);
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoMunicipios> requestMunicipio = georefService.municipios(idProvincia, "nombre", nombreDepartamento);
        Response<ListadoMunicipios> responseMunicipios = requestMunicipio.execute();
        return responseMunicipios.body().municipios.get(0);
    }
    public Provincia provinciaAPartirDeNombre(String nombreProvincia) throws IOException{
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoProvincias> requestProvinciasArg = georefService.provincias("nombre",nombreProvincia);
        Response<ListadoProvincias> responseProvincia = requestProvinciasArg.execute();
        return responseProvincia.body().provincias.get(0);
    }
    public int idDeProvincia(String nombreProvincia) throws IOException{
        return provinciaAPartirDeNombre(nombreProvincia).getId();
    }

}
