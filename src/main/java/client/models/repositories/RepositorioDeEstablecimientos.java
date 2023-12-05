package client.models.repositories;

import Organizaciones.Entidad;
import Organizaciones.Establecimiento;
import dbManager.EntityManagerHelper;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

import static dbManager.EntityManagerHelper.entityManager;

public class RepositorioDeEstablecimientos extends EntityManagerHelper implements ICrudRepository{
    public Object buscarPorId(int idEstablecimiento) {
        String query = "from " + Establecimiento.class.getName() + " where id_establecimiento = " + idEstablecimiento;
        return entityManager().createQuery(query).getSingleResult();
    }

    @Override
    public List buscarTodos() {
        return null;
    }

    @Override
    public Object buscar(Long id) {
        return null;
    }

    @Override
    public void guardar(Object o) {

    }

    @Override
    public void actualizar(Object o) {

    }

    @Override
    public void eliminar(Object o) {

    }
    public void eliminarEstablecimientoPorId(int id){

    }

}
