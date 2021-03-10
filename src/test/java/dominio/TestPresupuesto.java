package dominio;

import org.junit.Before;
import org.junit.Test;

import dominio.notificacion.BandejaMensajes;
import dominio.operacion.Documento;
import dominio.operacion.Item;
import dominio.operacion.MedioPago;
import dominio.operacion.Operacion;
import dominio.operacion.Presupuesto;
import dominio.operacion.TipoDeDocumento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestPresupuesto {

	Presupuesto presupuestoCocinaFravega;
	Item heladera;
	Item tele;
	Item cafetera;
	Operacion compraCocina;

	@Before
	public void init() {

		presupuestoCocinaFravega = new Presupuesto();
		heladera = new Item("samsung", new BigDecimal(40000));
		tele = new Item("lg", new BigDecimal(20000));
		cafetera = new Item("oster", new BigDecimal(5000));
		presupuestoCocinaFravega.agregarItem(heladera);
		presupuestoCocinaFravega.agregarItem(tele);
		

	       
		 Documento documento = new Documento("facturacion", TipoDeDocumento.FACTURA);
		 LocalDate fecha = LocalDate.of(2020, 6, 1);
		 compraCocina = new Operacion("Cocina",fecha, "utilencios", new BigDecimal(1000),MedioPago.EFECTIVO, documento,2);
	   
		
		compraCocina.asociarPresupuesto(presupuestoCocinaFravega);
	}

	@Test
	public void testOKPresupuestoTieneValorTotal() {
		assertEquals(new BigDecimal(60000), presupuestoCocinaFravega.valorTotalPresupuesto());
	}

	@Test
	public void testKOPresupuestoTieneValorTotal() {
		assertNotEquals(new BigDecimal(5000), presupuestoCocinaFravega.valorTotalPresupuesto());
	}
			
	@Test
	public void testOKesElMenorPresupuesto() {
		Presupuesto presupuesto2 = new Presupuesto();
		Item item2 = new Item("2",new BigDecimal(2));
		presupuesto2.agregarItem(item2);
		compraCocina.asociarPresupuesto(presupuesto2);
		assertEquals(true, presupuesto2.esElMenorPresupuesto(compraCocina));

	}
	
	@Test
	public void testKOesElMenorPresupuesto() {
		Presupuesto presupuesto2 = new Presupuesto();
		Item item2 = new Item("2",new BigDecimal(1000000));
		presupuesto2.agregarItem(item2);
		compraCocina.asociarPresupuesto(presupuesto2);
		assertEquals(false, presupuesto2.esElMenorPresupuesto(compraCocina));
	}
	
	@Test
	public void testKOEstaBasadoEnLaCompra() {
		presupuestoCocinaFravega.agregarItem(cafetera);
		///compraCocina.agregarItem(cafetera);
		assertEquals(3, presupuestoCocinaFravega.items().size());
	}
	
}