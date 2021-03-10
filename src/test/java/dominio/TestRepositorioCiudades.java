package dominio;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import api.Ciudad;
import api.ServicioDeUbicacionMercadoLibreMock;
import model.RepositorioCiudades;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import javax.persistence.EntityTransaction;
import java.util.List;

public class TestRepositorioCiudades implements WithGlobalEntityManager {

	EntityTransaction tx;

	@Before
	public void setUp() {
		tx = entityManager().getTransaction();
		tx.begin();

		List<Ciudad> ciudades = RepositorioCiudades.getCiudadesAPersistir(new ServicioDeUbicacionMercadoLibreMock());

	
	}


	@Test
	public void testComprobarQueBermejoExisteEnElRepo() {
		
		assertTrue(RepositorioCiudades.getInstace().validarQueExisteCiudad("Longschamps"));
	}

	@Test
	public void testComprobarQueChacabucoExisteEnElRepo() {
		assertTrue(RepositorioCiudades.getInstace().validarQueExisteCiudad("Laferrere"));
	}

	@Test
	public void testComprobarQueMadridNoExisteEnElRepo() {
		assertFalse(RepositorioCiudades.getInstace().validarQueExisteCiudad("Madrid"));
	}

	@After
	public void rollbackDB() {
		tx.rollback();
		entityManager().close();
	}
}
