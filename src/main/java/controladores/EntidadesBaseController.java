package controladores;

import dominio.entidades.EntidadBase;
import model.RepositorioEntidadesBase;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntidadesBaseController implements WithGlobalEntityManager, TransactionalOps {

	public ModelAndView getEntidadesBase(Request request, Response response) {
		Map<String, Object> modelo = new HashMap<>();

		List<EntidadBase> entidadesBaseAMostrar = RepositorioEntidadesBase.instancia.listar();
		modelo.put("entidadesBase", entidadesBaseAMostrar);

		return new ModelAndView(modelo, "entidadesBase.html.hbs");
	}

	public Object getDetalleEntidadBase(Request request, Response response, TemplateEngine engine) {
		String id = request.params(":id");
		try {
			EntidadBase entidadBase = RepositorioEntidadesBase.instancia.buscar(Integer.parseInt(id));//parser throw exception if is not a number
			return entidadBase != null ?
					engine.render(new ModelAndView(entidadBase, "detalle-entidadBase.html.hbs"))
					: null;
		} catch (NumberFormatException e) {
			response.status(400);
			System.out.println("El id ingresado (" + id + ") no es un número");
			return "Bad Request";
		}
	}

	public ModelAndView getEntidadBaseFormulario(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		return new ModelAndView(model, "formulario-entidadBase.html.hbs");
	}

	public Object crearEntidadBase(Request request, Response response) {
		String nombre = request.queryParams("nombre");
		String descripcion = request.queryParams("descripcion");

		if(nombre == null || descripcion == null){
			response.status(400);
			return "Bad Request";
		}

		EntidadBase nueva = new EntidadBase(nombre, descripcion);

		try {

			withTransaction(() -> {
				RepositorioEntidadesBase.instancia.agregar(nueva);
			});

			response.redirect("/entidadesBase"); //status 201
		}catch (Exception e){
			response.status(500);
		}

		return null;
	}

}
