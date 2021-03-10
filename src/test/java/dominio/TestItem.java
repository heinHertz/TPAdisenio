package dominio;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dominio.operacion.Item;

public class TestItem {

	Item heladera1;
	Item heladera2;
	Item heladera3;
	Item heladera4;
	
	@Before
	public void init() {
		heladera1 = new Item("heladera", new BigDecimal(30000));
		heladera2 = new Item("heladera", new BigDecimal(30000));
		heladera3 = new Item("heladera", new BigDecimal(40000));
		heladera4 = new Item("tele", new BigDecimal(30000));
	}

	@Test
	public void testOKEqualsMismoItem() {
		assertEquals(false, heladera1.equals(heladera2));
	}
	
	@Test
	public void testKOEqualsItemDistintoPrecio() {
		assertEquals(false, heladera1.equals(heladera3));
	}
	
	@Test
	public void testKOEqualsItemDistintoDetalle() {
		assertEquals(false, heladera1.equals(heladera4));
	}
	
}
