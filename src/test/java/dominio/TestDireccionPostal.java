package dominio;

import static org.junit.Assert.assertEquals;

import api.Ciudad;
import api.Moneda;
import api.Provincia;
import org.junit.Before;
import org.junit.Test;
import dominio.operacion.DireccionPostal;

public class TestDireccionPostal {

	DireccionPostal direccionPostal;
	Ciudad buenos_aires;
	Provincia prov_buenos_aires;
	Moneda monedaInfo;
	
	@Before
	public void setUp(){
		
		
		Ciudad buenos_aires = new Ciudad(new Provincia("Argentina", "Buenos Aires"), "Capital Federal");
	  	
    	Provincia prov_buenos_aires = new Provincia("Ciudad Autonoma de Buenos Aires","Capital Federal");
    	
    	Moneda monedaInfo = new Moneda("$","Peso argentino" );
    
	}

	@Test//(expected = ExcepcionDireccionPostal.class)
	public void testCrearDireccionPostalConExcepcion(){
	
		
		
		DireccionPostal direccionPostal = new DireccionPostal( "lacarra 180", buenos_aires, prov_buenos_aires, "argentina" , monedaInfo );
	}
}
