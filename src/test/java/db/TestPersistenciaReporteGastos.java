package db;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.notificacion.ReporteGastos;
import org.junit.Before;


public class TestPersistenciaReporteGastos implements WithGlobalEntityManager {

	EntityTransaction tx;

	@Before
	public void testPersistiReporteGastosaDatosEnDb() {

		tx = entityManager().getTransaction();
		if (!tx.isActive()) {
			tx.begin();
		}

		ReporteGastos gasto = new ReporteGastos("Heladeras", new BigDecimal(1000));
		entityManager().persist(gasto);

		entityManager().flush();
	}

	@Test
	public void testObtenerDReporteGastosDeDb() {
		ReporteGastos gasto = entityManager().createQuery("select t from ReporteGastos t where t.etiqueta = 'heladeras' ", ReporteGastos.class).getResultList().get(0);

		assertEquals(gasto.getEtiqueta(), "Heladeras");
	}

	@After
	public void rollbackDB() {
		tx.rollback();
		entityManager().close();
	}

}



