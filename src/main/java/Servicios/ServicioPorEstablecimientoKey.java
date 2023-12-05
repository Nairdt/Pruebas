package Servicios;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ServicioPorEstablecimientoKey implements Serializable {
    @Column(name = "id_servicio")
    private int id_servicio;
    @Column(name = "id_establecimiento")
    private int id_establecimiento;
}
