package db;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.operacion.Documento;
import dominio.operacion.TipoDeDocumento;
import org.junit.Before;

public class TestPersistenciaDocumento implements WithGlobalEntityManager {

	EntityTransaction tx;

	@Before
	public void testPersistirDatosEnDb() {
		tx = entityManager().getTransaction();
		if (!tx.isActive()) {
			tx.begin();
		}

		Documento documento = new Documento("facturacion", TipoDeDocumento.RECIBO);
		entityManager().persist(documento);

		entityManager().flush();
	}

	@Test
	public void testObtenerDocumentoDeDb() {
		List<Documento> docus = entityManager().createQuery("select t from Documento t where t.tipoDocumento like '%recibo%' ", Documento.class).getResultList();

		assertTrue(docus.size() >= 1);
	}

	@After
	public void rollbackDB() {
		tx.rollback();
		entityManager().close();
	}

}
