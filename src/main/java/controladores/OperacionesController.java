package controladores;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import api.Ciudad;
import api.Moneda;
import api.Provincia;
import dominio.operacion.DireccionPostal;
import dominio.operacion.Documento;
import dominio.operacion.Item;
import dominio.operacion.MedioPago;
import dominio.operacion.Operacion;
import dominio.operacion.Presupuesto;
import dominio.operacion.Proveedor;
import dominio.operacion.TipoDeDocumento;
import model.*;
import dominio.usuario.Usuario;

// withTransaction
public class OperacionesController implements WithGlobalEntityManager, TransactionalOps {


	public ModelAndView listar(Request request, Response response) {

		Map<String, Object> model = new HashMap<>();

		System.out.println("hans adolf hreinthadt");   //
		
		withTransaction(() -> {
		try {
			
			List<Operacion> repoOps	= RepositorioOperaciones.getInstance().listar();
			
			model.put("operaciones", repoOps);
	
		}
		catch (Exception e) {
			
			
		} 
		
		});
		
		return new ModelAndView(model, "operaciones-listar.html.hbs");
	}


	public ModelAndView obtener(Request request, Response response) {

		Long id = Long.parseLong(   request.params("id").toString()  );

		Map<String, Object> model = new HashMap<>();


		Boolean verificador = new Operacion().existeOperacionConID(id);


		if (verificador.equals(false)) {

			response.redirect("/operaciones-error?id=operacion ");

		}


		withTransaction(() -> {


			Operacion op = new Operacion().obtenerOperacionID(id);

			int cantidadPresupuestos = op.presupuestos.size();

			

			model.put("operacion", op);

			model.put("sizePresupuestos", cantidadPresupuestos);


		});


		return new ModelAndView(model, "operaciones-mostrar.html.hbs");

	}


	public ModelAndView cargar(Request request, Response response) {

		Map<String, Object> model = new HashMap<>();


	//	withTransaction(() -> {

			try {

			
				List<Ciudad> ciudades = RepositorioCiudades.getInstace().listar();
				
				
				Moneda moneda =  RepositorioMoneda.getInstance().getMoneda();

				model.put("ciudades", ciudades );
				model.put("moneda", moneda);

			} catch (Exception e) {

				response.redirect("/operaciones-error?id=ciudad ");

			}

	//	});

		return new ModelAndView(model, "operaciones-cargar.html.hbs");
	}


	public ModelAndView persistir(Request request, Response response) {
	

		Map<String, Object> model = new HashMap<>();


		try {


			String fechaString = request.queryParams("fecha");
			LocalDate fecha = LocalDate.parse(fechaString);

			String detalle = request.queryParams("detalle");
			int valor = Integer.parseInt(request.queryParams("valortotal"));
			BigDecimal valorTotal = new BigDecimal(valor);

			String medio = request.queryParams("mediopago");

			int cantPresupuestos = Integer.parseInt(request.queryParams("cantPresupuesto"));

			String etiqueta = request.queryParams("etiqueta");



			String nombredocumento = request.queryParams("nombredocumento");
			String tipodocu = request.queryParams("tipodocu");

			int dniproveedor = Integer.parseInt(request.queryParams("dniproveedor"));
			String nombreproveedor = request.queryParams("nombreproveedor");
			String direccionproveedor = request.queryParams("direccionproveedor");
			int ciudadproveedor = Integer.parseInt(request.queryParams("ciudadproveedor"));


			withTransaction(() -> {

				Documento documento = new Documento(nombredocumento, TipoDeDocumento.valueOf(tipodocu));


				Ciudad ciudadPersistir = RepositorioCiudades.getInstace().buscar(ciudadproveedor);
				Provincia provPersistir = ciudadPersistir.getProvincia();
				Moneda moneda = RepositorioMoneda.getInstance().buscarPorDescripcion("Peso argentino");

				DireccionPostal direccionPostal = new DireccionPostal(direccionproveedor, ciudadPersistir, provPersistir, "Argentina", moneda);


				Proveedor proveedor = new Proveedor(nombreproveedor, dniproveedor, direccionPostal);

				Operacion operacion = new Operacion(detalle, fecha, etiqueta, valorTotal, MedioPago.valueOf(medio), documento, cantPresupuestos);


				EntityTransaction tx = entityManager().getTransaction();

				if (!tx.isActive()) {
					tx.begin();
				}

				entityManager().persist(direccionPostal);

				entityManager().persist(proveedor);

				operacion.setProveedor(proveedor);

				entityManager().persist(operacion);

				entityManager().flush();

					tx.commit();


			});

		} catch (Exception e) {

			response.redirect("/operaciones-error?id=error");

		}

		model.put("estado", "Operacion Agregada Exitosamente");

		return new ModelAndView(model, "operaciones-error.html.hbs");
	}


	public ModelAndView editarPresupuesto(Request request, Response response) {
	

		Map<String, Object> model = new HashMap<>();

		try {
			
	Long id = Long.parseLong(  request.params(":id").toString()  );


			Operacion operacion = new Operacion().obtenerOperacionID(id);

			String estado;
			boolean editar;


			estado = "agregados presupuesto e items a la operacion";
			editar = true;
			model.put("estado", estado);
			model.put("editar", editar);
			model.put("operacion", operacion);


		} catch (Exception e) {

			response.redirect("/operaciones-error?id=presupuesto");

		}

		return new ModelAndView(model, "operaciones-presupuesto.html.hbs");
	}


	public ModelAndView crearPresupuesto(Request request, Response response) {


		Map<String, Object> model = new HashMap<>();

	
		Long id = Long.parseLong(  request.params(":id")  );
		

		withTransaction(() -> {
			Operacion operacion = new Operacion().obtenerOperacionID(id);

			Presupuesto presupuesto = new Presupuesto();


			String agregaritem1 = request.queryParams("agregaritem1");
			String agregaritem2 = request.queryParams("agregaritem2");
			String agregaritem3 = request.queryParams("agregaritem3");


			if (agregaritem1.equals("1")) {

				String detalle1 = request.queryParams("detalleitem1");
				int valor1 = Integer.parseInt(request.queryParams("valoritem1"));
				BigDecimal valorTotal1 = new BigDecimal(valor1);

				Item item1 = new Item(detalle1, valorTotal1);

				presupuesto.agregarItem(item1);
			}

			if (agregaritem2.equals("3")) {

				String detalle2 = request.queryParams("detalleitem2");
				int valor2 = Integer.parseInt(request.queryParams("valoritem2"));
				BigDecimal valorTotal2 = new BigDecimal(valor2);

				Item item2 = new Item(detalle2, valorTotal2);

				presupuesto.agregarItem(item2);
			}
			if (agregaritem3.equals("5")) {

				String detalle3 = request.queryParams("detalleitem3");
				int valor3 = Integer.parseInt(request.queryParams("valoritem3"));
				BigDecimal valorTotal3 = new BigDecimal(valor3);

				Item item3 = new Item(detalle3, valorTotal3);

				presupuesto.agregarItem(item3);
			}


			EntityTransaction tx = entityManager().getTransaction();

			if (!tx.isActive()) {
				tx.begin();
			}


			operacion.asociarPresupuesto(presupuesto);


			entityManager().merge(operacion);

			entityManager().flush();

			tx.commit();


		});


		model.put("estado", "Presupuesto Agregado con sus Items");

		return new ModelAndView(model, "operaciones-error.html.hbs");
	}


	public ModelAndView mostrarError(Request request, Response response) {

		String estado;

		String error = (String) request.queryParams("id");

		Map<String, Object> model = new HashMap<>();

		switch (error) {

			case "ciudad":
				estado = "Error  de valores de Ciudad no Validos o vacios";
				break;
			case "operacion":
				estado = " No se pudo obtener operacion id valida o no existe ese id";
				break;
			case "error":
				estado = " No se pudo determinar error";
				break;
			case "presupuesto":
				estado = " No se error con la carga de operaciones para presupuestos error";
				break;
			case "login":
				estado = "el usuario no esta logueado";
				break;
			default:
				estado = "Error  algo salio mal";
				break;

		}


		model.put("estado", estado);

		return new ModelAndView(model, "operaciones-error.html.hbs");

	}

	public boolean estaLogueado(Request request, Response response) {
		Optional<Usuario> usuario = getUsuarioLogueado(request);

		return usuario.isPresent();
	}

	public Optional<Usuario> getUsuarioLogueado(Request request) {
		Long idUsuario = request.session().attribute("idUsuario");

		Optional<Usuario> usuario = Optional.empty();

		if (idUsuario != null) {

			usuario = RepositorioUsuarios
					.getInstance()
					.usuariosLogueados()
					.stream()
					.filter(u -> u.getId_usuario().equals(idUsuario)).findFirst();
		}

		return usuario;

	}
	
}
