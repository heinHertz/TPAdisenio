package controladores;

import java.util.*;
import java.util.stream.Collectors;

import api.Ciudad;
import dominio.entidades.*;
import dominio.operacion.DireccionPostal;
import model.RepositorioCiudades;
import model.RepositorioMoneda;
import model.*;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

public class EntidadesJuridicasController implements WithGlobalEntityManager, TransactionalOps {

	public ModelAndView getEntidadesJuridicas(Request request, Response response) {
		String nombreCategoria = request.queryParams("filtro");
		Map<String, Object> model = new HashMap<>();

		List<EntidadJuridica> entidadesAmostrar = nombreCategoria != null ?
				RepositorioEntidadesJuridicas.instancia.listar().stream()
						.filter(entidad -> entidad.getCategoria() != null && entidad.getCategoria().getNombreCategoria().equals(nombreCategoria))
						.collect(Collectors.toList()) :
				RepositorioEntidadesJuridicas.instancia.listar();


		model.put("entidadesJuridicas", entidadesAmostrar);

		return new ModelAndView(model, "entidadesJuridicas.html.hbs");
	}

	public Object getDetalleEntidadJuridica(Request request, Response response, TemplateEngine engine) {
		String id = request.params(":id");
		try {
			EntidadJuridica entidadJuridica = RepositorioEntidadesJuridicas.instancia.buscar(Integer.parseInt(id));
			return entidadJuridica != null ?
					engine.render(new ModelAndView(entidadJuridica, "detalle-entidadJuridica.html.hbs"))
					: null;
		} catch (NumberFormatException e) {
			response.status(400);
			System.out.println("El id ingresado (" + id + ") no es un número");
			return "Bad Request";
		}
	}

	public ModelAndView getEntidadJuridicaFormulario(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		model.put("ciudades", RepositorioCiudades.getInstace().listar());
		model.put("moneda", RepositorioMoneda.getInstance().getMoneda());
		return new ModelAndView(model, "formulario-entidadJuridica.html.hbs");
	}

	public Object crearEntidadJuridica(Request request, Response response) {
		String nombre = request.queryParams("nombre");
		String razonSocial = request.queryParams("razonSocial");
		String cuit = request.queryParams("cuit");
		String tipoEmpresa = request.queryParams("tipoEmpresaHelp");
		String direccion = request.queryParams("direccion");
		String ciudadId = request.queryParams("ciudadesHelp");
		//la moneda es unica

		if (nombre == null || razonSocial == null || cuit == null
		    || tipoEmpresa == null || direccion == null || ciudadId == null){
			response.status(400);
			return "Bad Request";
		}

		Ciudad ciudad = RepositorioCiudades.getInstace().buscar(Integer.parseInt(ciudadId));

		DireccionPostal direccionPostal = new DireccionPostal(direccion, ciudad, ciudad.getProvincia(),
				"Argentina", RepositorioMoneda.getInstance().getMoneda());

		EntidadJuridica entidadJuridica = new EntidadJuridica(nombre, razonSocial, cuit, direccionPostal, TipoEmpresa.valueOf(tipoEmpresa));

		try {
			withTransaction(() -> {
				RepositorioDireccionesPostales.instancia.agregar(direccionPostal);
				RepositorioEntidadesJuridicas.instancia.agregar(entidadJuridica);
				response.redirect("/entidadesJuridicas"); //status 201
			});
		}catch (Exception e){
			response.status(500);
		}

		return null;
	}

	public Object getFormularioCategoria(Request request, Response response, TemplateEngine engine) {
		String id = request.params(":id");
		Map<String, Object> model = new HashMap<>();

		if(id == null){
			response.status(400);
			return "Bad Request";
		}

		model.put("idEntidadJuridica", id);
		return engine.render(new ModelAndView(model, "formulario-categoria.html.hbs"));
	}

	public Object updateEntidadJuridica(Request request, Response response) {
		String idEntidadJuridica = request.queryParams("idEntidadJuridica");
		String nombreCategoria = request.queryParams("nombreCategoria");
		List<String> reglas = Arrays.asList(request.queryParamsValues("regla"));

		if(nombreCategoria == null || reglas.isEmpty()) {
			response.status(400);
			return "Bad Request";
		}

		try {

			EntidadJuridica entidadJuridica = RepositorioEntidadesJuridicas.instancia.buscar(Integer.parseInt(idEntidadJuridica));
			Categoria nuevaCategoria = new Categoria(nombreCategoria);

			withTransaction(() -> {
				RepositorioCategorias.instancia.agregar(nuevaCategoria);

				for (String regla : reglas) {
					Regla reglaToAdd = determinarRegla(regla);
					RepositorioReglas.instancia.agregar(reglaToAdd);
					nuevaCategoria.agregarRegla(reglaToAdd);
				}

				entidadJuridica.setCategoria(nuevaCategoria);

			});

			response.redirect("/entidadesJuridicas");
		}catch (Exception e){
			response.status(500);
			return "Error al Realizar la operacion con la BD";
		}

		return null;
	}


	public Regla determinarRegla(String identificador) {
		Regla reglanueva;
		switch (identificador) {
			case "1":
				reglanueva = new ReglaLimitarAgregadoEntidadesBase();
				break;
			case "2":
				reglanueva = new ReglaLimitarADosOperaciones();
				break;
			case "3":
				reglanueva = new ReglaLimitarAgregadoOperaciones();
				break;
			case "4":
				reglanueva = new ReglaLimitarADosEntidadBase();
				break;
			default:
				throw new RuntimeException("NO EXISTE LA REGLA");
		}
		return reglanueva;
	}

}
