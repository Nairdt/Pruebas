package dbManager;

import Comunidad.LapsoReceptor;
import Comunidad.MedioReceptor;

import javax.persistence.AttributeConverter;

public class LapsoReceptorConverter implements AttributeConverter<LapsoReceptor, String> {
    @Override
    public String convertToDatabaseColumn(LapsoReceptor lapsoReceptor){
        switch (lapsoReceptor){
            case CUANDO_SUCEDEN:
                return "CUANDO_SUCEDEN";
            case SIN_APUROS:
                return "SIN_APUROS";
            default:
                return "null";
        }
    }
    @Override
    public LapsoReceptor convertToEntityAttribute(String lapsoReceptor){
        switch (lapsoReceptor){
            case "SIN_APUROS":
                return LapsoReceptor.SIN_APUROS;
            case "CUANDO_SUCEDEN":
                return LapsoReceptor.CUANDO_SUCEDEN;
            default:
                return null;
        }
    }
}
