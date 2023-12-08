package client.models;

import Comunidad.RolUsuario;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class ModelBase {
    @Getter
    Map<String, Object> model;
    public ModelBase(RolUsuario rolUsuario, String nombre, int idUsuario) {
        this.model = new HashMap<>();
        model.put("RolUsuario", rolUsuario);
        model.put("nombre", nombre);
        model.put("id_usuario", idUsuario);
    }

    public ModelBase(Map<String,Object> mapBase) {
        this.model = mapBase;
    }
    public void put(String s,Object o) {
        model.put(s,o);
    }
}
