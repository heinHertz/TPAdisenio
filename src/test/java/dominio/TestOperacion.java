package dominio;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Test;

import api.Ciudad;
import api.Moneda;
import api.Provincia;
import dominio.entidades.EntidadJuridica;
import dominio.entidades.TipoEmpresa;
import dominio.entidades.Categoria;
import dominio.operacion.DireccionPostal;
import dominio.operacion.Documento;
import dominio.operacion.Item;
import dominio.operacion.MedioPago;
import dominio.operacion.Operacion;
import dominio.operacion.Presupuesto;
import dominio.operacion.Proveedor;
import dominio.operacion.TipoDeDocumento;

public class TestOperacion {

    @Test
    public void testTieneCompraTodosSusPresupestosDeberiaFuncionar() {

    	Ciudad buenos_aires = new Ciudad(new Provincia("Argentina", "Buenos Aires"), "Capital Federal");
	  	
    	Provincia prov_buenos_aires = new Provincia("Ciudad Autonoma de Buenos Aires","Capital Federal");
    	
      	DireccionPostal direccionPostal = new DireccionPostal( "lacarra 180", buenos_aires, prov_buenos_aires, "argentina",new Moneda("$","Peso argentino" ) );
      	
        EntidadJuridica entidad = new EntidadJuridica("chacho bros", "grupo chacho", "202210", direccionPostal, new Categoria("ONG"), TipoEmpresa.EMPRESA);

        Documento documento = new Documento("facturacion", TipoDeDocumento.FACTURA);
		LocalDate fecha = LocalDate.of(2020, 6, 1);
     
		
        Operacion operacion1 = new Operacion("muebles",fecha, "muebles", new BigDecimal(1000),MedioPago.EFECTIVO, documento,1);
     
        operacion1.setEtiqueta("Amoblamiento");

        operacion1.setFechaOperacion(2020, 6, 1);

        Item modular = new Item("modular", new BigDecimal(1000));

  

        operacion1.setCantidadPresupuestosCompra(1);

        Presupuesto presupuesto = new Presupuesto();

        presupuesto.agregarItem(modular);

        operacion1.asociarPresupuesto(presupuesto);

        assertEquals(operacion1.tieneCompraTodosSusPresupuestos(), true);

    }

    @Test
    public void testTieneCompraTodosSusPresupestosDeberiaFallar() {

    	
    	Ciudad buenos_aires = new Ciudad(new Provincia("Argentina", "Buenos Aires"), "Capital Federal");
	  	
    	Provincia prov_buenos_aires = new Provincia("Ciudad Autonoma de Buenos Aires","Capital Federal");
    	
      	DireccionPostal direccionPostal = new DireccionPostal( "lacarra 180", buenos_aires, prov_buenos_aires, "argentina",new Moneda("$","Peso argentino" ) );
      	

        EntidadJuridica entidad = new EntidadJuridica("chacho bros", "grupo chacho", "202210", direccionPostal, new Categoria("ONG"),TipoEmpresa.EMPRESA);


        Documento documento = new Documento("facturacion", TipoDeDocumento.FACTURA);
      		LocalDate fecha = LocalDate.of(2020, 6, 1);
              
              Operacion operacion1 = new Operacion("muebles",fecha, "muebles", new BigDecimal(1000),MedioPago.EFECTIVO, documento,1);
           

        operacion1.setEtiqueta("Amoblamiento");

        operacion1.setFechaOperacion(2020, 6, 1);

        Item modular = new Item("modular", new BigDecimal(1000));


        operacion1.setCantidadPresupuestosCompra(2);

        Presupuesto presupuesto = new Presupuesto();

        presupuesto.agregarItem(modular);

        operacion1.asociarPresupuesto(presupuesto);

        assertEquals(operacion1.tieneCompraTodosSusPresupuestos(), false);

    }


}
