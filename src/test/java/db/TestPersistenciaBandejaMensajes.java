package db;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.notificacion.BandejaMensajes;
import dominio.operacion.Documento;
import dominio.operacion.TipoDeDocumento;

public class TestPersistenciaBandejaMensajes implements WithGlobalEntityManager {

	public Long idBandejaGlobal;
	EntityTransaction tx;

	@Before
	public void testPersistirDatosEnDb() {

		tx = entityManager().getTransaction();
		if (!tx.isActive()) {
			tx.begin();
		}

		BandejaMensajes bandejaDeMensajes = new BandejaMensajes();

		bandejaDeMensajes.addMensaje("operacion 1 realizada");
		bandejaDeMensajes.addMensaje("operacion 2 realizada");

		entityManager().persist(bandejaDeMensajes);

		idBandejaGlobal = bandejaDeMensajes.getId_bandeja();

		entityManager().flush();

	}

	@Test
	public void testObtenerListaMensajesBandejaDeDb() {
		List<String> mensajes = new BandejaMensajes().obtenerTodosMensajeDeUnaBandeja(idBandejaGlobal);

		assertEquals(2, mensajes.size());
	}

	@After
	public void rollbackDB() {
		tx.rollback();
		entityManager().close();
	}

}