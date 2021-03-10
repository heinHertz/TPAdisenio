package dominio;

import api.Moneda;
import api.ServicioDeMonedaMercadoLibre;
import model.RepositorioMoneda;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import javax.persistence.EntityTransaction;

import static org.junit.Assert.assertEquals;


public class TestRepositorioMoneda implements WithGlobalEntityManager {

	EntityTransaction tx;
	ServicioDeMonedaMercadoLibre api;
	

	@Before
	public void setUp() {
		tx = entityManager().getTransaction();
		tx.begin();

		 api = Mockito.mock(ServicioDeMonedaMercadoLibre.class);
		Mockito.when(api.obtenerMoneda()).thenReturn(new Moneda("$", "Peso Argentino"));

		entityManager().persist(RepositorioMoneda.getMonedasAPersistir(api));
		entityManager().flush();
	}

	@Test
	public void testObtenerDescripcionDeMoneda() {
	
		assertEquals("Peso Argentino", RepositorioMoneda.getInstance().getMonedasAPersistir(api).getDescripcion() );
		
	}

	@Test
	public void testObtenerSimboloDeMoneda() {
		assertEquals("Peso argentino", RepositorioMoneda.getInstance().buscarPorDescripcion("Peso argentino").getDescripcion() );
	}

	@After
	public void rollbackDB() {
		tx.rollback();
		entityManager().close();
	}

}
