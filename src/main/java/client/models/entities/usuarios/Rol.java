package client.models.entities.usuarios;

import Comunidad.RolUsuario;
import Servicios.TipoServicio;
import client.models.repositories.RepositorioDeUsuarios;
import dbManager.RolUsuarioConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rol")
@Getter
@Setter
public class Rol {
    @Id
    @GeneratedValue
    private int idRol;

    @Column(name="nombre")
    private String nombre;

    @Convert(converter = RolUsuarioConverter.class)
    @Column(name="rolUsuario")
    private RolUsuario rolUsuario;

    @ManyToMany
    private Set<Funcionalidad> funcionalidades;

    public Rol(RolUsuario rolUsuario) {
        this.rolUsuario = rolUsuario;
    }
    public Rol(String nombre, RolUsuario rolUsuario) {
        this.nombre = nombre;
        this.rolUsuario = rolUsuario;
    }
    public Rol() {
        this.funcionalidades = new HashSet<>();
    }
    public void agregarFuncionalidad(Funcionalidad ... funcionalidades) {
        Collections.addAll(this.funcionalidades, funcionalidades);
    }
    public boolean poseeFuncionalidad(Funcionalidad funcionalidad) {
        return this.funcionalidades.contains(funcionalidad);
    }

    public boolean poseeFuncionalidad(String nombreInterno) {
        return this.funcionalidades.stream().anyMatch(p -> p.coincideConNombreInterno(nombreInterno));
    }
}
