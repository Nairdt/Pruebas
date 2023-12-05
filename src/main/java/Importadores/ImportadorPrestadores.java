package Importadores;

import Prestadores.PrestadorDTO;

import java.util.List;

public class ImportadorPrestadores {
    Importable importador;

    public ImportadorPrestadores (Importable importador) {
        this.importador = importador;
    }

    public List<PrestadorDTO> importarPrestadores (){
            List<PrestadorDTO> listadoPrestadores = importador.importarDatosPrestadoresServicios();
            return listadoPrestadores;
    }
}
