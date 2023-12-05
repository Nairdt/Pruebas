import Importadores.ImportadorPrestadoresCSV;
import Organizaciones.Entidad;
import Prestadores.PrestadorDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import Importadores.ImportadorPrestadores;

import java.util.List;

public class TestImportador {
    ImportadorPrestadores importador;
    List<PrestadorDTO> listadoPrestadores;
    @Before
    public void init() {
        importador = new ImportadorPrestadores(new ImportadorPrestadoresCSV("Files/importablePrestadores.csv"));
        listadoPrestadores = importador.importarPrestadores();
    }
    @Test
    public void TestImportacionPrestadorTipoEntidadPrestadoras () {
        Assert.assertTrue(listadoPrestadores.get(0).getEntidadPrestadora().getNombre().equals("MiPrimerPrestador"));
        Assert.assertTrue(listadoPrestadores.get(0).getEntidadPrestadora().getUsuario().getNombre().equals("UsuarioAsociado"));
        Assert.assertTrue(listadoPrestadores.get(0).getEntidadPrestadora().getEntidad().getNombre().equals("MiPrimerEntidad")); //Perdon por el choclazo
    }
    @Test
    public void TestImportacionPrestadorTipoOrganismoDeControl () {
        Assert.assertTrue(listadoPrestadores.get(1).getOrganismoDeControl().getNombre().equals("MiSegundoPrestador"));
        Assert.assertTrue(listadoPrestadores.get(1).getOrganismoDeControl().getUsuario().getNombre().equals("OtroUsuario"));
        Assert.assertTrue(listadoPrestadores.get(1).getOrganismoDeControl().getListadoEntidades().size()==2); //Perdon por el choclazo
    }

    @Test
    //Test para la tokenizacion del listado de entidades de ODC
    public void TestImportacionEntidadesTipoOrganismoDeControl () {
        List<Entidad> entidadesDelODC = listadoPrestadores.get(1).getOrganismoDeControl().getListadoEntidades();
        Assert.assertTrue(entidadesDelODC.get(0).getNombre().equals("MiSegundaEntidad"));
        Assert.assertTrue(entidadesDelODC.get(1).getNombre().equals("MiTerceraEntidad"));
    }
}
