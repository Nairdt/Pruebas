package services.generadorDeRanking;

import services.generadorDeRanking.entities.RequestEntidadDTO;
import services.generadorDeRanking.entities.ResponseEntidadDTO;

import java.io.IOException;
import java.util.*;

public class PruebaUsoGeneradorRanking {

    public static void main(String[] args) throws IOException {
        ServicioGeneradorDeRanking generadorDeRanking = ServicioGeneradorDeRanking.getInstancia();

        //Prueba de cambio de CNF
        System.out.println("1- Prueba de cambio de CNF\n");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingresá el nuevo CNF: ");
        int nuevoCnf = scanner.nextInt();

        generadorDeRanking.cambiarCnfRanking(nuevoCnf);

        scanner.close();

        //Prueba de generación de Ranking
        System.out.println("2- Prueba de generación de Rankings\n");

        RequestEntidadDTO entidad1 = new RequestEntidadDTO(1, 5, 3, 7);
        RequestEntidadDTO entidad2 = new RequestEntidadDTO(2, 3, 5, 8);
        RequestEntidadDTO entidad3 = new RequestEntidadDTO(3, 5, 9, 5);
        RequestEntidadDTO entidad4 = new RequestEntidadDTO(4, 9, 3, 5);
        RequestEntidadDTO entidad5 = new RequestEntidadDTO(5, 6, 7, 2);

        List<RequestEntidadDTO> requestRanking = new ArrayList<>();

        requestRanking.add(entidad1);
        requestRanking.add(entidad2);
        requestRanking.add(entidad3);
        requestRanking.add(entidad4);
        requestRanking.add(entidad5);

        List<ResponseEntidadDTO> responseRanking = new ArrayList<>();

        responseRanking = generadorDeRanking.generarRankingEntidades(requestRanking);

        responseRanking.forEach(x -> System.out.println(x.getPuestoRanking() + " - Entidad " + x.getIdEntidad()));
    }
}
