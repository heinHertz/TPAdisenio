package db;

import static org.junit.Assert.assertTrue;

import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import api.Ciudad;
import api.Moneda;
import api.Provincia;
import dominio.entidades.EntidadJuridica;
import dominio.entidades.TipoEmpresa;
import dominio.operacion.DireccionPostal;
import model.RepositorioCiudades;
import model.RepositorioMoneda;
import model.RepositorioProvincias;

import org.junit.Before;

public class TestPersistenciaEntidadesJuridicas implements WithGlobalEntityManager {

	EntityTransaction tx;

	@Before
	public void testPersistirOperacionestosEnDb() {

		tx = entityManager().getTransaction();
		if (!tx.isActive()) {
			tx.begin();
		}
		
		Provincia prov_buenos_aires = RepositorioProvincias.getInstance().obtenerProvincia("Buenos Aires");

		Ciudad buenos_aires = RepositorioCiudades.getInstace().buscarPorNombre("Buenos Aires");

		Moneda moneda = RepositorioMoneda.getInstance().getMoneda();

		DireccionPostal direccionPostal = new DireccionPostal("lacarra 180", buenos_aires, prov_buenos_aires, "argentina", moneda);
		entityManager().persist(direccionPostal);

		EntidadJuridica entidad1 = new EntidadJuridica("chacho bros", "grupo chacho", "202210", direccionPostal, TipoEmpresa.EMPRESA);
		entityManager().persist(entidad1);

		entityManager().flush();
	}

	@Test
	public void testObtenerEntidadJuridicaDeDb() {
		EntidadJuridica entidad = (EntidadJuridica) entityManager()
				.createQuery("select t from EntidadJuridica t where t.nombreFicticio like '%chacho%' ", EntidadJuridica.class)
				.getResultList()
				.get(0);

		assertTrue(entidad.getNombreFicticio().contains("chacho"));
	}

	@After
	public void rollbackDB() {
		tx.rollback();
		entityManager().close();
	}

}
