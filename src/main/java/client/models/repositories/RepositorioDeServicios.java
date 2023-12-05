package client.models.repositories;

import Comunidad.Incidente;
import Servicios.Servicio;
import Servicios.ServicioPorEstablecimiento;
import Servicios.TipoServicio;
import client.server.Server;
import dbManager.EntityManagerHelper;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class RepositorioDeServicios  extends EntityManagerHelper implements ICrudRepository{
    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + Servicio.class.getName()).getResultList();
    }

    public List buscarTodosPorEstablecimiento() {
        return entityManager().createQuery("from " + ServicioPorEstablecimiento.class.getName()).getResultList();
    }

    public List buscarTodosLosTiposDeServicios() {
        return entityManager().createQuery("from " + TipoServicio.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return null;
    }


    public Servicio buscarPorNombre(String unNombre) {
        return (Servicio) entityManager().createQuery("from "+ Servicio.class.getName() + " where nombre like \'" + unNombre + "\'").getSingleResult();
    }

    public ServicioPorEstablecimiento buscarServicioPorEstablicimientoPorId(int idServicioPorE) {
        return (ServicioPorEstablecimiento) entityManager().createQuery("FROM " + ServicioPorEstablecimiento.class.getName() + " WHERE id_servicio_por_establecimiento = " + idServicioPorE).getSingleResult();
    }

    public TipoServicio buscarTipoServicioPorId(int idTipo) {
        return (TipoServicio) entityManager().createQuery("FROM " + TipoServicio.class.getName() + " WHERE id_tipo_servicio = " + idTipo).getSingleResult();
    }
    public TipoServicio buscarTipoServicioPorNombre(String nombre) {
        return (TipoServicio) entityManager().createQuery("FROM " + TipoServicio.class.getName() + " WHERE nombre = " + nombre).getSingleResult();
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

//    public Object buscarPorId(int idServicio) {
//        return (ServicioPorEstablecimiento) entityManager().createQuery("FROM " + ServicioPorEstablecimiento.class.getName() + " WHERE id_servicio_por_establecimiento = " + idServicio).getSingleResult();
//    }
    public Object buscarPorId(int idServicio){
        return entityManager().find(ServicioPorEstablecimiento.class, idServicio);
    }
}
