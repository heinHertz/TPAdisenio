package db;


import static org.junit.Assert.assertEquals;

import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import api.Ciudad;
import api.Moneda;
import api.Provincia;
import dominio.entidades.Categoria;
import dominio.entidades.EntidadBase;
import dominio.entidades.EntidadJuridica;
import dominio.entidades.TipoEmpresa;
import dominio.operacion.DireccionPostal;
import org.junit.Before;


public class TestPersistenciaEntidadBase implements WithGlobalEntityManager {

	EntityTransaction tx;

	@Before
	public void testPersistirOperacionestosEnDb() {
		tx = entityManager().getTransaction();
		if (!tx.isActive()) {
			tx.begin();
		}

		EntidadBase base = new EntidadBase("Macdonals", "muebleria");
		EntidadBase baseOtra = new EntidadBase("JuanSotelo", "bicicleteria");

		entityManager().persist(base);
		entityManager().persist(baseOtra);

		entityManager().flush();
	}

	@Test
	public void testObtenerEntidadJuridicaDeDb() {
		EntidadBase entidad = entityManager().createQuery("select t from EntidadBase t where t.nombreFicticio like '%macdonal%' ", EntidadBase.class).getResultList().get(0);

		assertEquals(entidad.getNombreFicticio(), "Macdonals");
	}

	@After
	public void rollbackDB() {
		tx.rollback();
		entityManager().close();
	}
}
