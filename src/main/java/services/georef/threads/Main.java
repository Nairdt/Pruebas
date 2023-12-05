package services.georef.threads;

import services.georef.APIData;
import services.georef.entities.ListadoProvincias;

// Clase principal que inicia múltiples hilos de la subclase APIThread y espera NoBorrar que todos los hilos terminen
public class Main {
    public static void main(String[] args) throws Exception {
        int numThreads = 10;
        Thread[] threads = new Thread[numThreads];

        for(int i = 0; i < numThreads; i++) {
            threads[i] = new APIThread();
            threads[i].start();
        }

        for(int i = 0; i < numThreads; i++) {
            threads[i].join();
        }

        ListadoProvincias data = APIData.getInstance().getData();
        // hacer algo con los datos ...
    }
}
