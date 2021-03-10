package db;

import api.Provincia;
import model.RepositorioCiudades;
import model.RepositorioMoneda;
import model.RepositorioProvincias;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import api.Ciudad;
import api.Moneda;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityTransaction;


public class TestPersistenciaCiudades implements WithGlobalEntityManager {
	//AL PERSISTIR NO HAY QUE PONER ID GENERA ERRORES TIPO JAVAHELL

	EntityTransaction tx;

	@Before
	public void testPersistirDireccionPostalDatosEnDb() {
		tx = entityManager().getTransaction();
		if (!tx.isActive()) {
			tx.begin();
		}

		
		Provincia prov_buenos_aires = RepositorioProvincias.getInstance().obtenerProvincia("Buenos Aires");

		Ciudad buenos_aires = RepositorioCiudades.getInstace().buscarPorNombre("Buenos Aires");

		Moneda moneda = RepositorioMoneda.getInstance().getMoneda();
		
		
		entityManager().flush();
	}

	@Test
	public void testObtenerCiudadesDeDb() {

		String ciudad = "Capital Federal";
		
		Ciudad capitalFederal = entityManager()
				.createQuery("from Ciudad where nombre like :ciudad", Ciudad.class) 
				.setParameter("ciudad",  "%" + ciudad + "%" )
				.getSingleResult();

		assertEquals("Capital Federal", capitalFederal.getNombre());

	}

	@Test
	public void testObtenerCiudadesDeRefactorDb() {
		Ciudad capitalFederal = RepositorioCiudades.getInstace().buscarPorNombre("Capital Federal");

		assertEquals("Capital Federal", capitalFederal.getProvincia().getNombre());
	}

	@After
	public void rollbackDB() {
		tx.rollback();
		entityManager().close();
	}

}
