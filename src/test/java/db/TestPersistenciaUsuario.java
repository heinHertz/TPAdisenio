package db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityTransaction;

import org.junit.*;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.notificacion.BandejaMensajes;
import dominio.usuario.TipoUsuario;
import dominio.usuario.Usuario;


public class TestPersistenciaUsuario implements WithGlobalEntityManager {

	EntityTransaction tx;

	@Before
	public void testPersistirDatoEnDb() {
		tx = entityManager().getTransaction();

		if (!tx.isActive()) {
			tx.begin();
		}
	
		Usuario brian = new Usuario("brian", "a1b2c5h5j8e9c", TipoUsuario.STANDARD);

		Usuario fede = new Usuario("fede", "1M2F3a5r8a7", TipoUsuario.STANDARD);

		

		entityManager().persist(fede);
		entityManager().persist(brian);

		entityManager().flush();
	}

	
	@Test
	public void testDatoEnPersistidoDbForma() {
		Usuario fede = entityManager()
				.createQuery("select t from Usuario t where t.nombre LIKE '%fede%' ", Usuario.class)
				.getResultStream()
				.findFirst()
				.get();
		
		Usuario brian = entityManager().createQuery("select t from Usuario t where t.nombre LIKE '%brian%' ", Usuario.class)
				.getResultStream()
				.findFirst()
				.get();

		assertEquals(fede.getNombreUsuario(), "fede");
		assertEquals(brian.getNombreUsuario(), "brian");
	}

	@After
	public void rollbackDB() {
		tx.rollback();
		entityManager().close();
	}

}
