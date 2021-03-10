package dominio;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import dominio.entidades.EntidadJuridica;
import dominio.notificacion.BandejaMensajes;
import dominio.operacion.Documento;
import dominio.operacion.Item;
import dominio.operacion.MedioPago;
import dominio.operacion.Operacion;
import dominio.operacion.Presupuesto;
import dominio.operacion.TipoDeDocumento;
import model.RepositorioEntidades;

public class TestBandejaMensajes {
	
	EntidadJuridica entidad1;
	Operacion operacion1;
	Operacion operacion2;
	Item muebles;
	EntidadJuridica entidad2;
	Item zapatillas;	
	public BandejaMensajes bandejaDeMensajes = new BandejaMensajes();
	Presupuesto presupuesto1 =  new Presupuesto();
	Presupuesto presupuesto2 = new Presupuesto();
	RepositorioEntidades repositorio;
	Presupuesto presupuesto = new Presupuesto();
	
	
	
	@Before   
	public void init() {

	       
		 Documento documento = new Documento("facturacion", TipoDeDocumento.FACTURA);
		 LocalDate fecha = LocalDate.of(2020, 6, 1);
		  operacion1 = new Operacion("muebles",fecha, "muebles", new BigDecimal(1000),MedioPago.EFECTIVO, documento,1);
	   
				
		Item muebles = new Item("muebles", new BigDecimal(1000));
		presupuesto.agregarItem(muebles);

		operacion1.asociarPresupuesto(presupuesto);
				
		operacion1.agregarObservadores(bandejaDeMensajes);
		
		
	}
	
	 @Test 
	 public void testOperacionAgregoObservadorYTieneUnMensaje() {
		 
		 assertEquals( true,  operacion1.validarCompras() );
		 
		 assertEquals( 1 ,  bandejaDeMensajes.getMensajes().size()    );
		 
	 }
	
	 @Test 
	 public void testAgregoOtroOperacionYBandejaDeMensajesTieneDosMensaje() {

	       
		 Documento documento = new Documento("facturacion", TipoDeDocumento.FACTURA);
		 LocalDate fecha = LocalDate.of(2020, 6, 1);
		  operacion2 = new Operacion("autopartes",fecha, "autos", new BigDecimal(1000),MedioPago.EFECTIVO, documento,1);
	   
		
				Item autopartes = new Item("autopartes", new BigDecimal(1000));
			
			Presupuesto otroPresupuesto = new Presupuesto();
			
			otroPresupuesto.agregarItem(autopartes);

			operacion2.asociarPresupuesto(otroPresupuesto);			
			
			operacion2.agregarObservadores(bandejaDeMensajes);
			
			 //hay que volver a validar todas las operaciones para mandar a todos los observadores			 
			operacion1.validarCompras();
			operacion2.validarCompras();

		 assertEquals( 2 ,  bandejaDeMensajes.getMensajes().size()    );

		 
	 }

	
	

}
