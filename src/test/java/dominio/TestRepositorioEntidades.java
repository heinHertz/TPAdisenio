package dominio;

import dominio.entidades.Categoria;
import dominio.entidades.EntidadJuridica;
import dominio.entidades.TipoEmpresa;
import dominio.notificacion.BandejaMensajes;
import dominio.operacion.DireccionPostal;
import dominio.operacion.Documento;
import dominio.operacion.Item;
import dominio.operacion.MedioPago;
import dominio.operacion.Operacion;
import dominio.operacion.Presupuesto;
import dominio.operacion.TipoDeDocumento;
import model.RepositorioEntidades;
import org.junit.Before;
import org.junit.Test;

import api.Ciudad;
import api.Moneda;
import api.Provincia;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class TestRepositorioEntidades {

	EntidadJuridica entidad1;
	Operacion operacion1;
	Item modular;
	EntidadJuridica entidad2;
	Operacion operacion2;
	Item indumentaria;
	Item muebles;
	BandejaMensajes bandejaDeMensajes = new BandejaMensajes();
	Presupuesto presupuesto1= new Presupuesto();
	Presupuesto presupuesto2 = new Presupuesto();
	RepositorioEntidades repositorioJuridicas = new RepositorioEntidades();
	Presupuesto presupuesto = new Presupuesto();

	@Before
	public void init() {
		Ciudad buenos_aires = new Ciudad(new Provincia("Argentina", "Buenos Aires"), "Capital Federal");
	  	Provincia prov_buenos_aires = new Provincia("Ciudad Autonoma de Buenos Aires","Capital Federal");
    	DireccionPostal direccionPostal = new DireccionPostal( "lacarra 180", buenos_aires, prov_buenos_aires, "argentina",new Moneda("$","Peso argentino" ) );
    	
    
		entidad1 = new EntidadJuridica("chacho bros", "grupo chacho", "202210", direccionPostal, new Categoria("ONG"), TipoEmpresa.EMPRESA);
		
	       
		 Documento documento = new Documento("facturacion", TipoDeDocumento.FACTURA);
		 LocalDate fecha = LocalDate.of(2020, 6, 1);
		  operacion1 = new Operacion("muebles",fecha, "muebles", new BigDecimal(1000),MedioPago.EFECTIVO, documento,1);
	   
	
		muebles = new Item("muebles", new BigDecimal(1000));
		presupuesto1.agregarItem(muebles);
		operacion1.asociarPresupuesto(presupuesto1);
				
		
		operacion1.agregarObservadores(bandejaDeMensajes);
		entidad1.agregarOperacion(operacion1);
		
		
		entidad2 = new EntidadJuridica("Messi y Amigos", "grupo nazareno", "12000", direccionPostal, new Categoria("ONG"), TipoEmpresa.EMPRESA);
		

	       
		 Documento documento2 = new Documento("facturacion", TipoDeDocumento.FACTURA);
		 LocalDate fecha2 = LocalDate.of(2020, 6, 1);
		  operacion2 = new Operacion("indumentaria",fecha2, "ropa", new BigDecimal(2000),MedioPago.EFECTIVO, documento,1);
	   
		 indumentaria = new Item("indumentaria", new BigDecimal(2000));
		presupuesto2.agregarItem(indumentaria);
		operacion2.asociarPresupuesto(presupuesto2);
		
		
		operacion2.agregarObservadores(bandejaDeMensajes);
		entidad2.agregarOperacion(operacion2);	
						
			
		repositorioJuridicas.getInstance().agregarEntidad(entidad1);
		
		
	}
	@Test
	public void testRepositorioAgregoEntidadDebeValerUno() {
			
					
		assertEquals( 1,  repositorioJuridicas.getInstance().entidades.size() );		
	}
	
	@Test
	public void testValidarComprasDebeHaberUnMensaje() {
		repositorioJuridicas.getInstance().validarEntidades();
		assertEquals(1, bandejaDeMensajes.getMensajes().size());		
	}
	
	@Test
	public void testValidarComprasDebeHaberDosMensajes() {
		repositorioJuridicas.getInstance().agregarEntidad(entidad2);
		repositorioJuridicas.getInstance().validarEntidades();
		assertEquals(2, bandejaDeMensajes.getMensajes().size());		
	}
	
	@Test
	public void testValidarComprasNoDebeHaberMensajesPorCompraTenerPresupuestoDemas() {
		operacion1.asociarPresupuesto(new Presupuesto());
		repositorioJuridicas.getInstance().validarEntidades();
		assertEquals(0, bandejaDeMensajes.getMensajes().size());		
	}
	
	@Test
	public void testValidarComprasDebeHaberUnMensajeUnaEntidadNoValida() {
		repositorioJuridicas.getInstance().agregarEntidad(entidad2);
	//	operacion1.asociarPresupuesto(new Presupuesto());
		repositorioJuridicas.getInstance().validarEntidades();
		
		assertEquals(2, bandejaDeMensajes.getMensajes().size());		
	}
	
}
