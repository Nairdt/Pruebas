package Servicios;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tramo {
    private Double tramoAlAcceso;
    private Double tramoAlAnden;

    public Tramo(double tramoAlAcceso, double tramoAlAnden){

        this.tramoAlAcceso = tramoAlAcceso;
        this.tramoAlAnden = tramoAlAnden;
    }
}
