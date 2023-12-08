package dbManager;

import Comunidad.MedioReceptor;
import Comunidad.RolUsuario;

import javax.persistence.AttributeConverter;

public class MedioReceptorConverter implements AttributeConverter<MedioReceptor, String> {
    @Override
    public String convertToDatabaseColumn(MedioReceptor medioReceptor){
        if(medioReceptor != null){
            switch (medioReceptor){
                case EMAIL:
                    return "EMAIL";
                case WHATSAPP:
                    return "WHATSAPP";
                default:
                    return "null";
            }
        }
        else{
            return "null";
        }

    }
    @Override
    public MedioReceptor convertToEntityAttribute(String medioReceptor){
        switch (medioReceptor){
            case "EMAIL":
                return MedioReceptor.EMAIL;
            case "WHATSAPP":
                return MedioReceptor.WHATSAPP;
            default:
                return null;
        }
    }
}
