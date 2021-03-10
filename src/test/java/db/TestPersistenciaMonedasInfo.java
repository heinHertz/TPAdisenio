package db;


import static org.junit.Assert.assertEquals;

import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import api.Ciudad;
import api.Moneda;
import org.junit.Before;


public class TestPersistenciaMonedasInfo implements WithGlobalEntityManager {

	EntityTransaction tx;

	@Before
	public void testPersistirMonedaDatosEnDb() {

		tx = entityManager().getTransaction();

		if (!tx.isActive()) {
			tx.begin();
		}

		Moneda monedaInfo = new Moneda("$", "Peso argentino");
		entityManager().persist(monedaInfo);

		entityManager().flush();
	}

	@Test
	public void testObtenerMonedaDeDb() {
		Moneda monedaInfo = entityManager().createQuery("select t from Moneda t where t.descripcion like 'Peso argentino' ", Moneda.class).getResultList().get(0);

		assertEquals(monedaInfo.getDescripcion(), "Peso argentino");
	}

	@After
	public void rollbackDB() {
		tx.rollback();
		entityManager().close();
	}

}