package Prestadores;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PrestadorDTO {
    private EntidadPrestadoraServicio entidadPrestadora;
    private OrganismoDeControl organismoDeControl;

    private String error;
    private int numeroPrestador;
}
