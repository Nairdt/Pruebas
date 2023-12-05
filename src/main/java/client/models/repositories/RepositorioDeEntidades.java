package client.models.repositories;

import Organizaciones.Entidad;
import Prestadores.EntidadPrestadoraServicio;
import Prestadores.OrganismoDeControl;
import dbManager.EntityManagerHelper;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioDeEntidades extends EntityManagerHelper implements ICrudRepository{
    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + Entidad.class.getName()).getResultList();
    }

    public List buscarEntidadesPrestador(Integer idUsuario){

        OrganismoDeControl organismoDeControlPorUsuario = (OrganismoDeControl) entityManager().createQuery("FROM " + OrganismoDeControl.class.getName() + " WHERE id_usuario = " + idUsuario)
                .getResultStream()
                .findFirst()
                .orElse(null);
        if(organismoDeControlPorUsuario != null) {
            return entityManager().createQuery("FROM " + Entidad.class.getName() + " WHERE id_organismo = " + organismoDeControlPorUsuario.getId_organismo()).getResultList();
        }
        EntidadPrestadoraServicio entidadPrestadoraServicioPorUsuario = (EntidadPrestadoraServicio) entityManager().createQuery("FROM " + EntidadPrestadoraServicio.class.getName() + " WHERE id_usuario = " + idUsuario)
                .getResultStream()
                .findFirst()
                .orElse(null);
        if(entidadPrestadoraServicioPorUsuario != null)
            return entityManager().createQuery("FROM " + Entidad.class.getName() + " WHERE id_prestadora = " + entidadPrestadoraServicioPorUsuario.getId_prestadora()).getResultList();
        else return null;
    }
    @Override
    public Object buscar(Long id) {
        return entityManager().find(Entidad.class, id);
    }

    public Object buscarPorId(int id) {
        String query = "from " + Entidad.class.getName() + " where id_entidad = " + id;
        return entityManager().createQuery(query).getSingleResult();
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
