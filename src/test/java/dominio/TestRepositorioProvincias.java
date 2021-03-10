package dominio;

import api.Provincia;
import api.ServicioDeUbicacionMercadoLibreMock;
import model.RepositorioProvincias;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import javax.persistence.EntityTransaction;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import static org.junit.Assert.assertEquals;

public class TestRepositorioProvincias implements WithGlobalEntityManager {

	EntityTransaction tx;

	@Before
	public void setUp() {
		tx = entityManager().getTransaction();
		tx.begin();

		List<Provincia> lista = RepositorioProvincias.getProvinciasAPersistir(new ServicioDeUbicacionMercadoLibreMock());

		for (Provincia provincia : lista) {
			entityManager().persist(provincia);
		}

		entityManager().flush();
	}


	@Test
	public void testComprobarQueExisteCapitalFederalEnElRepo() {
		assertTrue(RepositorioProvincias.getInstance().validarQueExisteProvincia("Capital Federal"));
	}

	@Test
	public void testComprobarQueNoExisteRomaEnElRepo() {
		assertFalse(RepositorioProvincias.getInstance().validarQueExisteProvincia("Roma"));
	}

	@After
	public void rollbackDB() {
		tx.rollback();
		entityManager().close();
	}
}
