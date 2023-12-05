package Prestadores;
import Comunidad.Usuario;
import Organizaciones.Entidad;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "entidad_prestadora_servicio")
@Getter
public class EntidadPrestadoraServicio{
    @Id
    @GeneratedValue
    @Column(name = "id_prestadora")
    private int id_prestadora;
    @Column(name = "nombre",length = 50)
    private String nombre;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_entidad",referencedColumnName = "id_entidad")
    private Entidad entidad;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_usuario",referencedColumnName = "id_usuario")
    private Usuario usuario;

    public EntidadPrestadoraServicio(String nombre,Entidad entidad, Usuario usuario) {
        this.nombre = nombre;
        this.entidad = entidad;
        this.usuario = usuario;
    }

    public EntidadPrestadoraServicio() {

    }
}