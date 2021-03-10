package controladores;


import model.RepositorioUsuarios;
import dominio.usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class LoginController implements WithGlobalEntityManager, TransactionalOps {

	public RepositorioUsuarios repo = new RepositorioUsuarios();

	public LoginController() {
		this.repo = new RepositorioUsuarios();
	}

	public ModelAndView getLogin(Request request, Response response) {	
		
		Map<String, Object> model = new HashMap<>();
		
		String respuesta = request.session().attribute("estado");
		
		if(respuesta !=null && respuesta.equals("logeado"))
		response.redirect("/operaciones");
		
		if(respuesta !=null && respuesta.equals("unlogin"))
		model.put("estadoMessage", "ingrese usuario o contraseña correctos");
		
		return new ModelAndView(model, "login.hbs");
	}

	public ModelAndView login(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();

		String username = request.queryParams("usuario");
		String password = request.queryParams("contrasenia");
		
		withTransaction(() -> {
		
		try {			
			
			Usuario usuario = RepositorioUsuarios.getInstance().getUsuarioUsernamePassword(username, password);
			
			
			RepositorioUsuarios.usuariosLogueados.add(usuario);

			request.session().attribute("idUsuario", usuario.getId_usuario());

			request.session().attribute("estado", "logeado" );

			response.redirect("/");
			
			

		} catch (Exception e) {
			
			request.session().attribute("estado", "unlogin");
			
			response.redirect("/login");

		}

		});
		
		return null;


	}

	public ModelAndView logout(Request request, Response response) {
		
		withTransaction(() -> {
			
		Long idUsuario = request.session().attribute("idUsuario");	
	
		RepositorioUsuarios.getInstance().removerUsuarioPorIdUsuario(idUsuario);
			
		request.session().removeAttribute("idUsuario");

		request.session().attribute("estado", "" );

		Map<String, Object> model = new HashMap<>();

		response.status(401);
		response.redirect("/login");
	});
		return null;

	}


	public static boolean estaLogueado(Request request, Response response) {
		Optional<Usuario> usuario = getUsuarioLogueado(request);

		return usuario.isPresent();
	}

	public static Optional<Usuario> getUsuarioLogueado(Request request) {
		
		Long idUsuario = request.session().attribute("idUsuario");

		Optional<Usuario> usuario = Optional.empty();

		if (idUsuario != null) {

		// usuario = RepositorioUsuarios.getInstance().getUsuarioUsernamePassword(username, password);
		 
			usuario = RepositorioUsuarios
					.getInstance()
					.usuariosLogueados()
					.stream()
					.filter(u -> u.getId_usuario().equals(idUsuario)).findFirst();
					
		}

		return usuario;

	}

	public static void asegurarseUsuarioEstaLoggeado(Request request, Response response) {

		if (!LoginController.estaLogueado(request, response)) {
			response.redirect("/login");
		}

	
	}

}
