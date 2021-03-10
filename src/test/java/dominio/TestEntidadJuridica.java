package dominio;



import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import api.Ciudad;
import api.Moneda;

import api.Provincia;
import dominio.entidades.Categoria;
import dominio.entidades.EntidadBase;
import dominio.entidades.EntidadJuridica;
import dominio.entidades.TipoEmpresa;
import dominio.operacion.DireccionPostal;
import dominio.operacion.Documento;
import dominio.operacion.Item;
import dominio.operacion.MedioPago;
import dominio.operacion.Operacion;
import dominio.operacion.Presupuesto;
import dominio.operacion.TipoDeDocumento;

public class TestEntidadJuridica {

	 EntidadJuridica entidad1;
	 
	public LocalDate fechaOperacion;    //DEPENDIENDO DE LAS FECHA SE VALIDAN LAS OPERACIONES

	@Before
	public void init() {
		

		Calendar cal = Calendar.getInstance();
		
	     cal.add(Calendar.DATE, -2);   
	       
	     fechaOperacion =  LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
	       

		
		Ciudad buenos_aires = new Ciudad(new Provincia("Argentina", "Buenos Aires"), "Capital Federal");
	  	
    	Provincia prov_buenos_aires = new Provincia("Ciudad Autonoma de Buenos Aires","Capital Federal");
    	
      	DireccionPostal direccionPostal = new DireccionPostal( "lacarra 180", buenos_aires, prov_buenos_aires, "argentina", new Moneda("$","Peso argentino" ));
	
		
		 entidad1 = new EntidadJuridica("chacho bros", "grupo chacho", "202210", direccionPostal, new Categoria("ONG"),TipoEmpresa.EMPRESA);

		
		
	}
	
	
	
	@Test
	 public void testEntidadTieneCategoriaJudicialAgrego3OperacionesPeroElLimiteDeCategoriaEsDOSDeberiaFuncionar()  { 

		
		   Presupuesto presupuesto = new Presupuesto();
	      
	       
			 Documento documento = new Documento("facturacion", TipoDeDocumento.FACTURA);
			 LocalDate fecha = LocalDate.of(2020, 6, 1);
			 Operacion operacion1 = new Operacion("muebles",fecha, "muebles", new BigDecimal(1000),MedioPago.EFECTIVO, documento,1);
		   

	        operacion1.setEtiqueta("Amoblamiento");
	        operacion1.setFechaOperacion(fechaOperacion);         
	        Item modular = new Item("modular", new BigDecimal(1000));
	        //operacion1.agregarItem(modular);
	        entidad1.agregarOperacion(operacion1);
	        

	        
			
			 Operacion  operacion2 = new Operacion("muebles",fecha, "muebles", new BigDecimal(1000),MedioPago.EFECTIVO, documento,1);
		   

	        operacion2.setEtiqueta("Reparacion");
	        operacion2.setFechaOperacion(fechaOperacion); 
	        Item cemento = new Item("cemento", new BigDecimal(1000));
	        //operacion2.agregarItem(cemento);
	        entidad1.agregarOperacion(operacion2);


	        
			
			 Operacion operacion3 = new Operacion("muebles",fecha, "muebles", new BigDecimal(1000),MedioPago.EFECTIVO, documento,1);
		   
	  
	        operacion3.setEtiqueta("Equipamiento");
	        operacion3.setFechaOperacion(fechaOperacion); 
	        Item lapiceras = new Item("Lapiceras", new BigDecimal(1000));
	        //operacion3.agregarItem(lapiceras);
	        entidad1.agregarOperacion(operacion3);      
			
	        
			assertEquals(entidad1.getOperaciones().size() , 2 , 1L);
		
}
	
	
	@Test
	 public void testEntidadTieneCategoriaONGAgrego3EntidadesBasePeroElLImiteONGesDeDOSDeberiaFuncionar()  { 

		
	        EntidadBase lavanderia = new EntidadBase("lavanderia", "lavandera SA");
			entidad1.agregarEntidadBase(lavanderia);

			EntidadBase cochera = new EntidadBase("cochera", "cochera SA");
			entidad1.agregarEntidadBase(cochera);

			EntidadBase pelotero = new EntidadBase("pelotero", "pelotero SA");
			entidad1.agregarEntidadBase(pelotero);


			assertEquals(entidad1.getEntidadesBases().size() , 2 , 1L);
		
	}
	@Test
	 public void testValidarOperaciones()  { 

		 Documento documento = new Documento("facturacion", TipoDeDocumento.FACTURA);
		 LocalDate fecha = fechaOperacion;
		 Operacion operacion1 = new Operacion("muebles",fecha, "muebles", new BigDecimal(1000),MedioPago.EFECTIVO, documento,1);
	   
      
        operacion1.setEtiqueta("Amoblamiento");
        operacion1.setFechaOperacion(fechaOperacion); 
        Item modular = new Item("modular", new BigDecimal(1000));
    
        entidad1.agregarOperacion(operacion1);
             
		
		 Operacion  operacion2 = new Operacion("muebles",fecha, "muebles", new BigDecimal(1000),MedioPago.EFECTIVO, documento,1);
	   
    
        operacion2.setEtiqueta("Reparacion");
        operacion2.setFechaOperacion(fechaOperacion); 
        Item cemento = new Item("cemento", new BigDecimal(1000));
       
        entidad1.agregarOperacion(operacion2);


        
		
		 Operacion operacion3 = new Operacion("muebles",fecha, "muebles", new BigDecimal(1000),MedioPago.EFECTIVO, documento,1);
	   
        
      
        operacion3.setEtiqueta("Equipamiento");
        operacion3.setFechaOperacion(fechaOperacion);
        Item lapiceras = new Item("Lapiceras", new BigDecimal(1000));
      
        entidad1.agregarOperacion(operacion3);      
		
        entidad1.generarReporteGastos();
        
        
        
        assertEquals(3 , entidad1.reporteGastos.size()  );
        
	}
	
	
		
}
