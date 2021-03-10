package db;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import api.Ciudad;
import api.Moneda;
import api.Provincia;

import org.junit.Before;

import dominio.operacion.DireccionPostal;
import dominio.operacion.Documento;
import dominio.operacion.Item;
import dominio.operacion.MedioPago;
import dominio.operacion.Operacion;
import dominio.operacion.Presupuesto;
import dominio.operacion.Proveedor;
import dominio.operacion.TipoDeDocumento;
import model.RepositorioCiudades;
import model.RepositorioMoneda;
import model.RepositorioProvincias;

public class TestPersistenciaOperaciones implements WithGlobalEntityManager {

	EntityTransaction tx;

	@Before
	public void testPersistirOperacionestosEnDb() {
		tx = entityManager().getTransaction();

		if (!tx.isActive()) {
			tx.begin();
		}

		Documento documento = new Documento("facturacion", TipoDeDocumento.FACTURA);
		LocalDate fecha = LocalDate.of(2020, 10, 10);
		Operacion operacion1 = new Operacion("muebles", fecha, "muebles", new BigDecimal(1000), MedioPago.EFECTIVO, documento, 1);

		Presupuesto presupuestoCocinaFravega = new Presupuesto();
		Item heladera = new Item("samsung", new BigDecimal(40000));
		Item tele = new Item("lg", new BigDecimal(20000));
		Item cafetera = new Item("oster", new BigDecimal(5000));
		presupuestoCocinaFravega.agregarItem(heladera);
		presupuestoCocinaFravega.agregarItem(tele);
		presupuestoCocinaFravega.agregarItem(cafetera);

		operacion1.asociarPresupuesto(presupuestoCocinaFravega);

		Presupuesto presupuestoCocinaGarbarino = new Presupuesto();
		Item heladeraPhillips = new Item("phillips", new BigDecimal(40000));
		Item noblex = new Item("noblex", new BigDecimal(20000));
		Item liliana = new Item("liliana", new BigDecimal(5000));
		presupuestoCocinaGarbarino.agregarItem(heladeraPhillips);
		presupuestoCocinaGarbarino.agregarItem(noblex);
		presupuestoCocinaGarbarino.agregarItem(liliana);

		operacion1.asociarPresupuesto(presupuestoCocinaGarbarino);

		Provincia prov_buenos_aires = RepositorioProvincias.getInstance().obtenerProvincia("Buenos Aires");
		
		Ciudad buenos_aires = RepositorioCiudades.getInstace().buscarPorNombre("Buenos Aires");
		

		Moneda moneda = RepositorioMoneda.getInstance().getMoneda();
		DireccionPostal direccionPostal = new DireccionPostal("Lezama 500", buenos_aires, prov_buenos_aires, "Argentina", moneda);
		entityManager().persist(direccionPostal);

		Proveedor proveedorMadera = new Proveedor("ariel", 2123, direccionPostal);
		entityManager().persist(proveedorMadera);

		operacion1.setProveedor(proveedorMadera);

		entityManager().persist(operacion1);

		entityManager().flush();
	}

	@Test
	public void testObtenerOperacionesDeDb() {
		String muebles = "muebles";
		
		Operacion operacion = entityManager()
				.createQuery("select t from Operacion t where t.etiqueta like :muebles ", Operacion.class)
				.setParameter("muebles", "%" + muebles + "%")				
				.getResultList().get(0);

		assertEquals(operacion.getEtiqueta(), "muebles");
	}

	@After
	public void rollbackDB() {
		tx.rollback();
		entityManager().close();
	}

}
