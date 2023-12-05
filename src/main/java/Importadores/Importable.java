package Importadores;

import Prestadores.PrestadorDTO;

import java.util.List;

public interface Importable {
    public abstract List<PrestadorDTO> importarDatosPrestadoresServicios();
}
