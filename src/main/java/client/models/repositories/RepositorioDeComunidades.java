package client.models.repositories;

import Comunidad.Comunidad;
import Comunidad.Miembro;
import Comunidad.Incidente;
import dbManager.EntityManagerHelper;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.springframework.validation.ObjectError;

import javax.persistence.EntityTransaction;
import java.util.Arrays;
import java.util.List;

public class RepositorioDeComunidades extends EntityManagerHelper implements ICrudRepository{
    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + Comunidad.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return entityManager().find(Comunidad.class, id);
    }

    public Comunidad buscarPorId(int id) {
        return entityManager().find(Comunidad.class, id);
    }
    public Object buscarMiembroPorId(int id, int idComunidad){
        return entityManager().createQuery("FROM " + Miembro.class.getName() + " WHERE id_usuario = " + id + " AND comunidad = " + idComunidad).getSingleResult();
    }

    public List buscarPorIdUsuario(int id){
        return entityManager().createQuery("FROM " + Comunidad.class.getName() + " WHERE id_comunidad IN (SELECT comunidad FROM "+ Miembro.class.getName() + " WHERE id_usuario = " + id + ")").getResultList();
    }

    @Override
    public void guardar(Object o) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().persist(o);
        tx.commit();
    }

    public void guardarElementos(Object... listadoObjetos) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();
        for(Object o : listadoObjetos){
            entityManager().persist(o);
        }
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
