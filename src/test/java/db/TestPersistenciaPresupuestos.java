package db;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.operacion.Item;
import dominio.operacion.Presupuesto;

public class TestPersistenciaPresupuestos implements WithGlobalEntityManager {

	EntityTransaction tx;

	@Before
	public void testPersistirDatosItemsEnDb() {

		tx = entityManager().getTransaction();

		if (!tx.isActive()) {
			tx.begin();
		}

		Item heladera1 = new Item("heladera", new BigDecimal(1000));
		Item licuadora = new Item("licuadora", new BigDecimal(2000));

		Presupuesto presupuestoCocinaFravega = new Presupuesto();

		presupuestoCocinaFravega.agregarItem(heladera1);
		presupuestoCocinaFravega.agregarItem(licuadora);

		entityManager().persist(presupuestoCocinaFravega);

		entityManager().flush();
	}

	@Test
	public void testObtenerPersistidosDatosItemsEnDb() {
		Presupuesto presupuesto = entityManager().createQuery("select pr from Presupuesto pr inner join pr.items it where it.detalle = 'heladera'   ", Presupuesto.class).getResultList().get(0);

		assertEquals(presupuesto.items.get(0).detalle(), "heladera");
	}

	@After
	public void rollbackDB() {
		tx.rollback();
		entityManager().close();
	}

}
