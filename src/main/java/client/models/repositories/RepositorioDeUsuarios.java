package client.models.repositories;

import Comunidad.Miembro;
import Comunidad.Usuario;
import Servicios.Servicio;
import Organizaciones.Entidad;
import dbManager.EntityManagerHelper;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioDeUsuarios extends EntityManagerHelper implements ICrudRepository {
    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + Usuario.class.getName()).getResultList();
    }

    public Object buscarPorId(int id) {
        String query = "from " + Usuario.class.getName() + " where id_usuario = " + id;
        return entityManager().createQuery(query).getSingleResult();
    }

    public Object buscarMiembroPorIdUsuarioYComunidad(int id, int idComunidad) {
        String query = "from " + Miembro.class.getName() + " where id_usuario = " + id + " and comunidad = "+ idComunidad;
        return entityManager().createQuery(query).getSingleResult();
    }

    @Override
    public Object buscar(Long id) {
        return entityManager().find(Usuario.class, id);
    }
    public Boolean existeMail(String unMail){
        return entityManager().createQuery("from "+ Usuario.class.getName() + " where mail like '" + unMail + "'").getResultList().size()==1;
    }

    public Usuario usuariosCompatibles(String unMail, String unaContrasenia){
        try {
            return (Usuario) entityManager().createQuery("FROM " + Usuario.class.getName() + " WHERE (mail LIKE '" + unMail + "' OR nombre LIKE '" + unMail + "') AND clave LIKE '" + unaContrasenia + "'").getSingleResult();
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public void guardar(Object o) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().persist(o);
        tx.commit();
    }

    @Override
    public void actualizar(Object o) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().merge(o);
        tx.commit();
    }

    @Override
    public void eliminar(Object o) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().remove(o);
        tx.commit();
    }

    public Object buscarPorNombre(String nombreUsuario) {
        return entityManager().createQuery("FROM " + Usuario.class.getName() + " WHERE nombre LIKE '%" + nombreUsuario + "%'").getResultList();
    }
}
