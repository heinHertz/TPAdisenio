package controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.notificacion.BandejaMensajes;
import dominio.operacion.Operacion;
import dominio.usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class BandejaMensajesController implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps  {

	

	 public ModelAndView inicializar(Request request, Response response) {
	
	        Map<String,Object> model = new HashMap<>();
	        
	 
	        Long idUsuario = request.session().attribute("idUsuario");
	    
	           
	        withTransaction( ()	-> {  
	    	   
	    	Usuario usuario = new Usuario().obtenerUsuarioID(idUsuario);
	    	   
	    	
	        Long idbandeja = usuario.getBandeja().id_bandeja;
	    	   
	       	
	      List<String> mensajes = (List<String>) new BandejaMensajes().obtenerTodosMensajeDeUnaBandeja( Long.parseLong("3") );
      	
	      
	        model.put("usuario", usuario );
	        model.put("id", idUsuario );
	        model.put("mensajes", mensajes );
	        model.put("sizemensajes", mensajes.size() );
	        
	        } );
	        
	        return new ModelAndView(model, "bandeja-mensajes-principal.html.hbs");
	        
	    }
	
	
	 
	 public ModelAndView asociar(Request request, Response response) {
		 

	        Long idUsuario = request.session().attribute("idUsuario");
		 
			Long id = Long.parseLong(  request.queryParams("id_operacion").toString() );
						
		  
		 
			withTransaction(() -> {

			Usuario usuario = new Usuario().obtenerUsuarioID(idUsuario);
		
		   Operacion operacion = new Operacion().obtenerOperacionID(id);
		   
		   BandejaMensajes bandejaUsuario = usuario.getBandeja();
		   
		   operacion.agregarObservadores(bandejaUsuario);
		   
		   merge(operacion);
		
			});
		 
	        Map<String,Object> model = new HashMap<>();
	               
	        
	        model.put("id", id);
	        
	        return new ModelAndView(model, "bandeja-mensajes-asociar.html.hbs");
	    }
	

	
}
