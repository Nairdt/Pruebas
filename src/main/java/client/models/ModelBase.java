package client.models;

import Comunidad.RolUsuario;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class ModelBase {
    @Getter
    Map<String, Object> model;
    public ModelBase(RolUsuario rolUsuario) {
        this.model = new HashMap<>();
        model.put("RolUsuario", rolUsuario);
    }

    public void put(String s,Object o) {
        model.put(s,o);
    }
}
