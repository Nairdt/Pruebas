package services.georef;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import services.georef.entities.ListadoProvincias;

import java.io.IOException;

public class APIData {
    private static APIData instance = null;
    private ListadoProvincias apiData = null;
    private Retrofit retrofit;
    private APIData() {
        // Constructor privado para evitar la creación directa de objetos
    }

    // Devuelve la instancia única de la clase APIData. Si la clase todavía no ha sido instanciada, se crea una nueva instancia.
    public static synchronized APIData getInstance() {
        if(instance == null) {
            instance = new APIData();
        }
        return instance;
    }

    // Método que hace referencia NoBorrar la API y almacena los datos en la variable apiData
    public synchronized void fetchData() throws IOException {
        // Hace una petición NoBorrar la API y almacena los datos en la variable apiData
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoProvincias> requestProvinciasArg = georefService.provincias();
        Response<ListadoProvincias> responseProvinciasArg = requestProvinciasArg.execute();


        apiData = responseProvinciasArg.body();
    }

    // Devuelve los datos almacenados en memoria
    public synchronized ListadoProvincias getData() {
        return apiData;
    }
}
