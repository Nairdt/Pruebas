package APIServicioEntities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Setter;

@Schema(name = "ResponseEntidadDTO")
@Setter
public class ResponseEntidadDTO {
    public Integer idEntidad;
    public Integer nivelImpactoEntidad;
    public Integer puestoRanking;
}

