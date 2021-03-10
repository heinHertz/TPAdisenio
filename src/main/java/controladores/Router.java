package controladores;

import ar.edu.frba.dds.gesoc.Main.Bootstrap;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import spark.Spark;

import controladores.*;
import dominio.notificacion.PlanificadorTareas;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.notFound;
import static spark.Spark.redirect;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import spark.TemplateEngine;
import spark.template.handlebars.HandlebarsTemplateEngine;



public class Router implements WithGlobalEntityManager, TransactionalOps {

	public static void main(String[] args) {

		System.out.println("Iniciando Servidor...");

		Spark.port(getHerokuAssignedPort());
	
		
		Spark.staticFileLocation("public");

		//new Bootstrap().run();

		after((request, response) -> {
			PerThreadEntityManagers.getEntityManager();
			PerThreadEntityManagers.closeEntityManager();
		});

		TemplateEngine templateEngine = new HandlebarsTemplateEngine();

		HomeController homeController = new HomeController();
		LoginController loginController = new LoginController();
		OperacionesController operacionesController = new OperacionesController();
		BandejaMensajesController bandejaMensajesController = new BandejaMensajesController();
		EntidadesJuridicasController entidadesJuridicasController = new EntidadesJuridicasController();
		EntidadesBaseController entidadesBaseController = new EntidadesBaseController();
		PrincipalController principalController = new PrincipalController();

		before("/*", (request, response) -> {

			if (!request.pathInfo().equals("/login"))
				LoginController.asegurarseUsuarioEstaLoggeado(request, response);

		});
		
		
		//Home
		Spark.get("/", homeController::getHome, templateEngine);
		Spark.get("/404", homeController::get404, templateEngine);

		//Login
		Spark.get("/login", loginController::getLogin, templateEngine);
		Spark.post("/login", loginController::login, templateEngine);
		Spark.post("/logout", loginController::logout, templateEngine);

		//Entidades
		Spark.get("/entidadesBase", entidadesBaseController::getEntidadesBase, templateEngine);
		Spark.get("/entidadesJuridicas", entidadesJuridicasController::getEntidadesJuridicas, templateEngine);
		Spark.get("/entidadesJuridicas/nueva", entidadesJuridicasController::getEntidadJuridicaFormulario, templateEngine);
		Spark.get("/entidadesBase/nueva", entidadesBaseController::getEntidadBaseFormulario, templateEngine);
		Spark.get("/entidadesBase/:id", (req, res) -> entidadesBaseController.getDetalleEntidadBase(req, res, templateEngine));
		Spark.get("/entidadesJuridicas/:id", (req, res) -> entidadesJuridicasController.getDetalleEntidadJuridica(req, res, templateEngine));
		Spark.get("/entidadesJuridicas/:id/asociarCategoria", (req, res) -> entidadesJuridicasController.getFormularioCategoria(req, res, templateEngine));
		Spark.post("/entidadesJuridicas/asociarCategoria", entidadesJuridicasController::updateEntidadJuridica);
		Spark.post("/entidadesBase", entidadesBaseController::crearEntidadBase);
		Spark.post("/entidadesJuridicas", entidadesJuridicasController::crearEntidadJuridica);

		//operaciones
		Spark.get("/operaciones", operacionesController::listar, templateEngine);
		Spark.get("/operaciones/cargar", operacionesController::cargar, templateEngine);
		Spark.post("/operaciones/cargar", operacionesController::persistir, templateEngine);
		Spark.get("/operaciones/:id", operacionesController::obtener, templateEngine);
		Spark.get("/operaciones-error", operacionesController::mostrarError, templateEngine);
		Spark.get("/operaciones/:id/presupuesto", operacionesController::editarPresupuesto, templateEngine);
		Spark.post("/operaciones/:id/presupuesto", operacionesController::crearPresupuesto, templateEngine);

		//bandejamensajes
		Spark.get("/bandejamensajes", bandejaMensajesController::inicializar, templateEngine);
		Spark.post("/bandejamensajes", bandejaMensajesController::asociar, templateEngine);

		notFound((request, response) -> {
			response.redirect("/404");
			return null;
		});

		PlanificadorTareas planificadorScheduler = new PlanificadorTareas();
		planificadorScheduler.activarYEjecutarRevisorValidacionesCron();
	}

	static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}

		return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
	}

}