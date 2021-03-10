package controladores;


import model.RepositorioProvincias;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class HomeController {

	public ModelAndView getHome(Request request, Response response) {		
		Map<String, Object> model = new HashMap<>();		
		return new ModelAndView(null, "index.html.hbs");
	}
	
	public ModelAndView get404(Request request, Response response) {
		Map<String, Object> model = new HashMap<>();
		return new ModelAndView(model, "404.html.hbs");
	}
	
}