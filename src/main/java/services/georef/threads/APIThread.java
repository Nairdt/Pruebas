package services.georef.threads;

import services.georef.APIData;

import java.io.IOException;

// Subclase de la clase Thread que hace una petición NoBorrar la API y almacena los datos en memoria
public class APIThread extends Thread {
    public void run() {
        APIData data = APIData.getInstance();
        try {
            data.fetchData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}