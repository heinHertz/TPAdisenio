package db;

import static org.junit.Assert.assertEquals;

import javax.persistence.*;

import org.junit.After;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import api.Ciudad;
import api.Moneda;
import api.Provincia;
import dominio.operacion.DireccionPostal;
import dominio.operacion.Proveedor;
import dominio.usuario.Usuario;
import model.RepositorioCiudades;
import model.RepositorioMoneda;
import model.RepositorioProvincias;

import org.junit.Before;

public class TestPersistenciaProveedor implements WithGlobalEntityManager {

	EntityTransaction tx;

	@Before
	public void test1PersistirProveedorEnDb() {

		tx = entityManager().getTransaction();

		if (!tx.isActive()) {
			tx.begin();
		}
		
		Provincia prov_buenos_aires = RepositorioProvincias.getInstance().obtenerProvincia("Buenos Aires");

		Ciudad buenos_aires = RepositorioCiudades.getInstace().buscarPorNombre("Buenos Aires");

		Moneda moneda = RepositorioMoneda.getInstance().getMoneda();

	
		DireccionPostal direccionPostal = new DireccionPostal("Lezama 500", buenos_aires, prov_buenos_aires, "Argentina", moneda);

		Proveedor proveedorMadera = new Proveedor("ariel", 2123, direccionPostal);
		entityManager().persist(proveedorMadera);

		entityManager().flush();
	}

	@Test
	public void testRecuperarDatoProveedorEnPersistidoDb() {
		Proveedor proveedor = entityManager()
				.createQuery("select t from Proveedor t where t.nombre like 'ariel' ", Proveedor.class)
				.getResultList()
				.get(0);

		assertEquals(proveedor.getDni(), 2123);
	}

	@After
	public void rollbackDB() {
		tx.rollback();
		entityManager().close();
	}

}
