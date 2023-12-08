package client.models.repositories;

import Comunidad.Comunidad;
import Comunidad.Miembro;
import Comunidad.Incidente;
import dbManager.EntityManagerHelper;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioDeIncidentes extends EntityManagerHelper implements ICrudRepository{
    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + Incidente.class.getName()).getResultList();
    }
    public List buscarIncidentesNoResueltos(int idUsuario){
        return entityManager().createQuery("from "+ Incidente.class.getName() + " where resuelto = '" + 0 + "' AND id_comunidad IN (SELECT comunidad FROM "+ Miembro.class.getName()+" WHERE id_usuario = "+ idUsuario +")").getResultList();
    }

    public List buscarIncidentesResueltos(int idUsuario){
        return entityManager().createQuery("from "+ Incidente.class.getName() + " where resuelto = '" + 1 + "' AND id_comunidad IN (SELECT comunidad FROM "+ Miembro.class.getName()+" WHERE id_usuario = "+ idUsuario +")").getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return entityManager().find(Incidente.class, id);
    }

    public Incidente buscarPorId(int id) {
        return entityManager().find(Incidente.class, id);
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
}
