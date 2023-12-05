package Validador;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Validador {
    private List<Validable> validaciones;

    //TODO
    // Implementar con Set

    public Validador(){
            this.validaciones = new ArrayList<Validable>();
    }

    public void habilitarValidacion(Validable ... validacionesInsertables) {
        validaciones.addAll(Arrays.asList(validacionesInsertables));
    }

    public boolean cumpleValidaciones(String clave){
        return validaciones.stream().allMatch(validacion -> validacion.cumpleValidacion(clave)); //La idea era darle más declaratividad al códigoz
    }

    public List<String> listarValidaciones(String claveValidable) {
        List<String> listadoMensajesValidaciones = new ArrayList<>();
        validaciones.forEach(v -> {
            if(!v.cumpleValidacion(claveValidable))
                listadoMensajesValidaciones.add(v.getDescripcion());
        });
        return listadoMensajesValidaciones;
    }
}
