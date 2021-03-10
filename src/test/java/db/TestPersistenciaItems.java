package db;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.operacion.Documento;
import dominio.operacion.Item;
import dominio.operacion.TipoDeDocumento;
import org.junit.Before;

public class TestPersistenciaItems implements WithGlobalEntityManager {

	EntityTransaction tx;

	@Before
	public void testPersistirDatosItemsEnDb() {
		tx = entityManager().getTransaction();

		if (!tx.isActive()) {
			tx.begin();
		}

		Item heladera = new Item("heladera", new BigDecimal(1000));
		Item mueble = new Item("mueble", new BigDecimal(30000));

		entityManager().persist(heladera);
		entityManager().persist(mueble);

		entityManager().flush();
	}

	@Test
	public void testObtenerPersistidosDatosItemsEnDb() {
		Item item = entityManager().createQuery("select t from Item t where t.detalle like 'heladera' ", Item.class).getResultList().get(0);

		assertEquals(item.detalle(), "heladera");
	}

	@After
	public void rollbackDB() {
		tx.rollback();
		entityManager().close();
	}

}
	
