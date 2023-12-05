package client.models.entities.usuarios;

import Comunidad.RolUsuario;
import dbManager.EntityManagerHelper;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class RepositorioDeRoles extends EntityManagerHelper {
    public Rol getRolSegunEnumRolUsuario(String rolUsuario) {
        return (Rol) entityManager().createQuery("FROM " + Rol.class.getName() + " WHERE rolUsuario = \'" + rolUsuario + "\'").getSingleResult();
    }
}
