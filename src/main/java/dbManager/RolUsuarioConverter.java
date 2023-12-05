package dbManager;

import Comunidad.RolUsuario;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RolUsuarioConverter implements AttributeConverter<RolUsuario, String> {
    @Override
    public String convertToDatabaseColumn(RolUsuario rolUsuario){
        if(rolUsuario == null){
            return "null";
        }
        switch (rolUsuario){
            case PRESTADOR:
                return "PRESTADOR";
            case MIEMBRO:
                return "MIEMBRO";
            case PROVEEDOR:
                return "PROVEEDOR";
            case ADMINISTRADOR:
                return "ADMINISTRADOR";
            default:
                return "null";
        }
    }
    @Override
    public RolUsuario convertToEntityAttribute(String rolUsuario){
        switch (rolUsuario){
            case "PRESTADOR":
                return RolUsuario.PRESTADOR;
            case "MIEMBRO":
                return RolUsuario.MIEMBRO;
            case "PROVEEDOR":
                return RolUsuario.PROVEEDOR;
            case "ADMINISTRADOR":
                return RolUsuario.ADMINISTRADOR;
            default:
                return null;
        }
    }
}
