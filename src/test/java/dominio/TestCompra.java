package dominio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dominio.notificacion.BandejaMensajes;
import dominio.operacion.Documento;
import dominio.operacion.Item;
import dominio.operacion.MedioPago;
import dominio.operacion.Operacion;
import dominio.operacion.Presupuesto;
import dominio.operacion.TipoDeDocumento;

public class TestCompra {

	Operacion compraCocina;
	Presupuesto presupuestoCocina;
	Presupuesto presupuestoOtraCocina;
	List<Operacion> compras;
	Operacion compraCocinaGarbarino;
	BandejaMensajes bandejaDeJuan;
	Item cocina;
	Item tenedor;

	@Before
	public void init() {


	       
		 Documento documento = new Documento("facturacion", TipoDeDocumento.FACTURA);
		 LocalDate fecha = LocalDate.of(2020, 6, 1);
		  compraCocina = new Operacion("cocina",fecha, "utilencios", new BigDecimal(1000),MedioPago.EFECTIVO, documento,1);
	   
		cocina = new Item("cocina", new BigDecimal(1000));
		tenedor = new Item("tenedor", new BigDecimal(500));
		presupuestoCocina = new Presupuesto();
		presupuestoOtraCocina = new Presupuesto();
		presupuestoCocina.agregarItem(cocina);
		compraCocina.asociarPresupuesto(presupuestoCocina);
		
		//compraCocina.agregarItem(cuchara);
		bandejaDeJuan = new BandejaMensajes();
		bandejaDeJuan.sendMensajes(new String("Compra Validada"));
		compraCocina.agregarObservadores(bandejaDeJuan);

	}

	@Test
	public void testCorrectoCalculoDelValorTotalDeUnaCompraDeUnItem() {
		assertEquals(new BigDecimal(1000), compraCocina.valorTotal());

	}

	@Test
	public void testIncorrectoCalculoDelValorTotalDeUnaCompraDeUnItem() {
		assertNotEquals(compraCocina.valorTotal(), new BigDecimal(100));
	}
	
	@Test
	public void testCorrectoCalculoDelValorTotalDeUnaCompraDeDosItem() {
		presupuestoCocina.agregarItem(tenedor);
	
		assertEquals(compraCocina.valorTotal(), new BigDecimal(1000));
	}
	
	@Test
	public void tesCorrectoCalculoDelValorTotalDeUnaCompraDeDosItem() {
		presupuestoCocina.agregarItem(tenedor);
	
		assertEquals(compraCocina.valorTotal(), new BigDecimal(1000));
	}
	
	@Test
	public void testCorrectaValidacionCompraTieneTodosSusPresupuestosPorCantidadCorrectaDePresupuestosCargados() {
		assertTrue(compraCocina.tieneCompraTodosSusPresupuestos());
	}
	
	@Test
	public void testFallaValidacionCompraTieneTodosSusPresupuestosPorCantidadIncorrecteDePresupuestosCargados() {
		compraCocina.asociarPresupuesto(presupuestoOtraCocina);
		assertFalse(compraCocina.tieneCompraTodosSusPresupuestos());
	}

	@Test
	public void testCorrectaValidacionCompraBasadaEnPresupuestoPorTenerMismosItems() {
		assertTrue(compraCocina.estaLaCompraBasadaEnAlgunPresupuesto());
	}
	
	@Test
	public void testFallaValidacionCompraBasadaEnPresupuestoPorTenerDistintosItems() {
		presupuestoCocina.agregarItem(tenedor);
		assertTrue(compraCocina.estaLaCompraBasadaEnAlgunPresupuesto());
	}
	
	@Test
	public void testValidarComprasVerdadero() {
		assertTrue(compraCocina.validarCompras());
	}
	
	@Test
	public void testFallaValidarComprasPorCompraNoTenerTodosSusPresupuestos() {
		compraCocina.asociarPresupuesto(presupuestoOtraCocina);
		assertFalse(compraCocina.validarCompras());
	}
	
	@Test
	public void testFallaValidarComprasPorTenerDistintosItemsQueSuPresupuesto() {
		presupuestoCocina.agregarItem(tenedor);
		
		assertTrue(compraCocina.validarCompras());
	}
	
	@Test
	public void testDeEnvioDeNotificacionPorResultadoCorrectoDeValidarCompras() {
		compraCocina.validarCompras();
		assertEquals(2, bandejaDeJuan.getMensajes().size());
	}

	@Test
	public void testDeNoEnvioDeNotificacionPorFallaValidarCompras() {
		compraCocina.setCantidadPresupuestosCompra(2); // para que sea ko
		compraCocina.validarCompras();
		assertEquals(1, bandejaDeJuan.getMensajes().size());
	}

	@Test
	public void testCorrectaRecepcionDelMensajeEnviadoPorNotificacion() {
		compraCocina.validarCompras();
		assertEquals("Compra Validada", bandejaDeJuan.getMensajes().get(0).toString());
	}
}