package dominio;

import dominio.entidades.Categoria;
import dominio.entidades.EntidadBase;
import dominio.entidades.EntidadJuridica;
import dominio.entidades.ReglaLimitarADosOperaciones;
import dominio.entidades.ReglaLimitarAgregadoEntidadesBase;
import dominio.entidades.ReglaLimitarAgregadoOperaciones;
import dominio.entidades.ReglaLimitarADosEntidadBase;
import dominio.entidades.TipoEmpresa;
import dominio.operacion.DireccionPostal;
import dominio.operacion.Documento;
import dominio.operacion.MedioPago;
import dominio.operacion.Operacion;
import dominio.operacion.TipoDeDocumento;

import org.junit.Before;
import org.junit.Test;

import api.Ciudad;
import api.Moneda;
import api.Provincia;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestCategorizacionEntidades {

	        Operacion operacion1;
	        Operacion operacion2;	      
	        Operacion operacion3;	        
	        Operacion operacion4;	   

	        Ciudad buenos_aires;	        
	        Provincia prov_buenos_aires; 	    	
	      	DireccionPostal direccionPostal; 
	      	
	      	EntidadBase entidadBase1;
	      	EntidadBase entidadBase2;
	      	EntidadBase entidadBase3;
	
		@Before
	  public void init() {		
			
			
			
		Ciudad buenos_aires = new Ciudad(new Provincia("Argentina", "Buenos Aires"), "Capital Federal");
    	  	
    	Provincia prov_buenos_aires = new Provincia("Ciudad Autonoma de Buenos Aires","Capital Federal");
    	
      	DireccionPostal direccionPostal = new DireccionPostal(  "lacarra 180", buenos_aires, prov_buenos_aires, "argentina" ,new Moneda("$","Peso argentino"));
    	   

        
		 Documento documento = new Documento("facturacion", TipoDeDocumento.FACTURA);
		 LocalDate fecha = LocalDate.of(2020, 6, 1);
		 Operacion operacion1 = new Operacion("muebles",fecha, "Amoblamiento", new BigDecimal(1000),MedioPago.EFECTIVO, documento,1);
	     
		
		 Operacion operacion2 = new Operacion("muebles",fecha, "Reparacion", new BigDecimal(1000),MedioPago.EFECTIVO, documento,1);
	   
		 Operacion operacion3 = new Operacion("muebles",fecha, "Equipamiento", new BigDecimal(1000),MedioPago.EFECTIVO, documento,1);
	   
	    
		  operacion4 = new Operacion("farmacia",fecha, "farmacia", new BigDecimal(1000),MedioPago.EFECTIVO, documento,1);
	   
		       
		         entidadBase1 = new EntidadBase("Alegre y Heller SA", "Constructora"  );

		         entidadBase2 = new EntidadBase("Dolf SA", "Muebleria"  );
		         
		         entidadBase3 = new EntidadBase("Hofdorf", "Siderurgica"  );
		        
	  }
	
	
	

    @Test //(expected = ExcepcionRegla.class)
    public void testEntidadConCategoriaQueNoPermiteAgregarMasDeDosOperaciones(){


        Categoria categoria =  new Categoria("Judicial");
        
        
        categoria.agregarRegla(new ReglaLimitarADosEntidadBase() );
        
        
      	
        EntidadJuridica entidad1 = new EntidadJuridica("chacho bros", "grupo chacho", "202210", direccionPostal, categoria,TipoEmpresa.EMPRESA);
   
        entidad1.aplicarReglasDeCategoria();    // HAY QUE APLICAR SINO NO FUNCIONA EL COSIFICADO DE REGLAS
        
        entidad1.agregarEntidadBase(entidadBase1);
        
        entidad1.agregarEntidadBase(entidadBase2);
        
        entidad1.agregarEntidadBase(entidadBase3);
           
        
		 assertEquals( 2  , entidad1.getEntidadesBases().size()  ); 
        
        
        
    }

    @Test//(expected = ExcepcionRegla.class)
    public void testEntidadJuridicaConCategoriaQueNoPermiteAgregarEntidadesBase(){

    	 Categoria categoria =  new Categoria("Judicial");
                 
         categoria.agregarRegla(new ReglaLimitarAgregadoEntidadesBase() );        
         
       	
         EntidadJuridica entidad1 = new EntidadJuridica("chacho bros", "grupo chacho", "202210", direccionPostal, categoria,TipoEmpresa.EMPRESA);
    

         
         entidad1.agregarEntidadBase(entidadBase1);
         
         entidad1.aplicarReglasDeCategoria();    // HAY QUE APLICAR SINO NO FUNCIONA EL COSIFICADO DE REGLAS
                  
         entidad1.agregarEntidadBase(entidadBase2);
         
         entidad1.agregarEntidadBase(entidadBase2);
         
      
        

 		 assertEquals( 1  , entidad1.getEntidadesBases().size()  ); 
         
    	

    }

    @Test//(expected = ExcepcionRegla.class)
    public void testEntidadJuridicaConCategoriaQuePermiteAgregarDosOperacionesPeroLuegoNoLoPermite(){

    	
    	 Categoria categoria =  new Categoria("Judicial");
         
         categoria.agregarRegla(new ReglaLimitarADosOperaciones() );        
         
       	
         EntidadJuridica entidad1 = new EntidadJuridica("chacho bros", "grupo chacho", "202210", direccionPostal, categoria,TipoEmpresa.EMPRESA);
     
         entidad1.aplicarReglasDeCategoria();    // HAY QUE APLICAR SINO NO FUNCIONA EL COSIFICADO DE REGLAS

         entidad1.agregarOperacion(operacion1);
         
         entidad1.agregarOperacion(operacion2);
         
         entidad1.agregarOperacion(operacion3);
         

      	assertEquals( 2  , entidad1.getOperaciones().size()  ); 
         
         
         
 
    }

    @Test
    public void testEntidadConCategoriaQueNoPermiteAgregarMasDeTresOperacionesCorrecto(){
    	
    	
    	
 	 Categoria categoria =  new Categoria("Judicial");
         
         categoria.agregarRegla(new ReglaLimitarAgregadoOperaciones() );        
         
       	
         EntidadJuridica entidad1 = new EntidadJuridica("chacho bros", "grupo chacho", "202210", direccionPostal, categoria,TipoEmpresa.EMPRESA);
     
       

         entidad1.agregarOperacion(operacion1);
         
         entidad1.agregarOperacion(operacion2);
         
         entidad1.agregarOperacion(operacion3);
         
         entidad1.aplicarReglasDeCategoria();    // HAY QUE APLICAR SINO NO FUNCIONA EL COSIFICADO DE REGLAS
           
         entidad1.agregarOperacion(operacion4);
         

      	assertEquals( 3  , entidad1.getOperaciones().size()  );   
      	
      	
      	
   

     
    }

    @Test
    public void testEntidadConCategoriaQueNoPermiteAgregarMasDeUnaOperacionePeroLuegoSeDesactivaYSeAgregaUnaMasQuedandoDosOperaciones(){


    		Categoria categoria =  new Categoria("Judicial");
            
            categoria.agregarRegla(new ReglaLimitarAgregadoOperaciones() );        
            
          	
            EntidadJuridica entidad1 = new EntidadJuridica("chacho bros", "grupo chacho", "202210", direccionPostal, categoria,TipoEmpresa.EMPRESA);
                  

            entidad1.agregarOperacion(operacion1);
                 
            entidad1.aplicarReglasDeCategoria();   // aplicar
            

      	   entidad1.agregarOperacion(operacion2); //intent
      	   
      	   entidad1.agregarOperacion(operacion3); //intent
      	  
            

         	assertEquals( 1  , entidad1.getOperaciones().size()  );   
                    	       	
         	
         	categoria.removerRegla(new ReglaLimitarAgregadoOperaciones() );
         	
         	 
            categoria.agregarRegla(new ReglaLimitarADosOperaciones() );  
            
            entidad1.aplicarReglasDeCategoria();   // aplicar
            

       	   entidad1.agregarOperacion(operacion2); //intent
       	   
       	   entidad1.agregarOperacion(operacion3); //intent
       	  
             
          	assertEquals( 2  , entidad1.getOperaciones().size()  );   
            
         
         	
         	
    	

    }
    
    
}
