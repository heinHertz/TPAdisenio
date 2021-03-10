package db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import api.Ciudad;
import api.Moneda;
import api.Provincia;
import dominio.entidades.Categoria;
import dominio.entidades.EntidadBase;
import dominio.entidades.Regla;
import dominio.entidades.ReglaLimitarADosEntidadBase;
import dominio.entidades.ReglaLimitarADosOperaciones;
//import dominio.entidades.ReglaLimitarADosOperaciones;
import dominio.operacion.DireccionPostal;
import dominio.operacion.Operacion;

public class TestPersistenciaReglas implements WithGlobalEntityManager {

	EntityTransaction tx;

	@Before
	public void init() {

		tx = entityManager().getTransaction();
		if (!tx.isActive()) {
			tx.begin();
		}

		Categoria categoria = new Categoria("Judicial");

		categoria.agregarRegla(new ReglaLimitarADosOperaciones());
		categoria.agregarRegla(new ReglaLimitarADosEntidadBase());

		entityManager().persist(categoria);

		// Regla independiente
		Regla regla = new ReglaLimitarADosEntidadBase();

		entityManager().persist(regla);

		entityManager().flush();
	}

	@Test
	public void testObtenerCategoriaDeDb() {
		List<Categoria> categorias = entityManager().createQuery("select t from Categoria t where t.nombreCategoria LIKE 'Judicial' ", Categoria.class).getResultList();

		assertTrue(categorias.size() >= 1);
	}

	@Test
	public void testObtenerReglasDeDb() {
		Regla regla = entityManager().createQuery("select t from Regla t where t.etiquetaRegla LIKE '%Limita%' ", Regla.class).getResultList().get(0);

		assertTrue(regla.etiquetaRegla.contains("Limita"));
	}

	@Test
	public void testObtenerListaReglasDeDb() {
		List<Regla> reglas = entityManager().createQuery("select t from Regla t where t.etiquetaRegla LIKE '%Limita%' ", Regla.class).getResultList();

		assertTrue(reglas.size() >= 1);
	}

	@After
	public void rollbackDB() {
		tx.rollback();
		entityManager().close();
	}

}
