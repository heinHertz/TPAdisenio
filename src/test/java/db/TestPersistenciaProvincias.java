package db;

import org.junit.After;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import api.Provincia;

import static org.junit.Assert.assertEquals;

import org.junit.Before;

import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;

public class TestPersistenciaProvincias implements WithGlobalEntityManager {

	EntityTransaction tx;

	@Before
	public void testPersistirProvinciaDatosEnDb() {

		tx = entityManager().getTransaction();

		if (!tx.isActive()) {
			tx.begin();
		}

		Provincia prov_chaco = new Provincia("Argentina", "Chaco");
		entityManager().persist(prov_chaco);

		entityManager().flush();
	}

	@Test
	public void testObtenerDProvinciaDeDb() {
		Provincia provincia = entityManager()
				.createQuery("select t from Provincia t where t.nombre like '%chaco%'  and  t.pais like '%Argentina%' ", Provincia.class)
				.getResultList()
				.get(0);

		assertEquals(provincia.getNombre(), "Chaco");
	}

	@After
	public void rollbackDB() {
		tx.rollback();
		entityManager().close();
	}

}
