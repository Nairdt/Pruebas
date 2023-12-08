package GeneradorRankings;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntidadRanking {
    private int idEntidad;
    private String nombreEntidad;
    private int puestoRanking;
    private int nivelImpacto;

    public EntidadRanking(int idEntidad, String nombreEntidad, int puestoRanking, int nivelImpacto) {
        this.idEntidad = idEntidad;
        this.nombreEntidad = nombreEntidad;
        this.puestoRanking = puestoRanking;
        this.nivelImpacto = nivelImpacto;
    }
}
