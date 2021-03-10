package db;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import api.Ciudad;
import api.Moneda;
import api.Provincia;
import dominio.operacion.DireccionPostal;
import model.RepositorioCiudades;
import model.RepositorioMoneda;
import model.RepositorioProvincias;

import org.junit.Before;

public class TestPersistenciaDireccionPostal implements WithGlobalEntityManager {

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


		DireccionPostal direccionPostal = new DireccionPostal("lacarra 180", buenos_aires, prov_buenos_aires, "Argentina", moneda);
	
		entityManager().persist(direccionPostal);

		entityManager().flush();
	}

	@Test
	public void testObtenerDireccionPostalDeDb() {
		String direccion = "lacarra";
		
		DireccionPostal dir = entityManager()
				.createQuery("select t from DireccionPostal t where t.direccion like :direccion  ", DireccionPostal.class)
				.setParameter("direccion", "%" + direccion + "%")
				.getSingleResult();
		
		
		assertEquals(dir.direccion, "lacarra 180");
	}

	@After
	public void rollbackDB() {
		tx.rollback();
		entityManager().close();
	}
}
