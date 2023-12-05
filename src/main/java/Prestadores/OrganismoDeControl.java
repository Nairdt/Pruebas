package Prestadores;
import Comunidad.Usuario;
import Organizaciones.Entidad;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "organismo_control")
@Getter
public class OrganismoDeControl{
    @Id
    @GeneratedValue
    @Column(name = "id_organismo")
    private int id_organismo;
    @Column(name = "nombre",length = 50)
    private String nombre;
    @OneToMany(mappedBy = "organismoDeControl", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Entidad> listadoEntidades;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_usuario",referencedColumnName = "id_usuario")
    private Usuario usuario;
    public OrganismoDeControl(String nombre,List<Entidad> listadoEntidades,Usuario usuario) {
        this.nombre = nombre;
        this.listadoEntidades = listadoEntidades;
        this.usuario = usuario;
    }

    public OrganismoDeControl() {

    }
}
