package api;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import model.RepositorioProvincias;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;


public class ServicioDeUbicacionMercadoLibre implements ServicioDeUbicacion {

	private Client client = Client.create();
	private static final String API_MERCADOLIBRE = "https://api.mercadolibre.com/";
	private static final String LOCATIONSCOUNTRYS = "classified_locations/";

	public ClientResponse requestAPI(String path) {
		ClientResponse response = this.client.resource(API_MERCADOLIBRE).path(path)
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		return response;
	}

	public JSONObject obtenerJsonDe(String path) {
		ClientResponse response = requestAPI(LOCATIONSCOUNTRYS + path);
		String info = response.getEntity(String.class);
		return new JSONObject(info);
	}

	public List<String> getProvinciasID() {
		JSONObject json = this.obtenerJsonDe("countries/AR");
		JSONArray provincias = json.getJSONArray("states");
		List<String> listaProvincias = new ArrayList<String>();
		for (int i = 0; i < provincias.length(); i++) {
			listaProvincias.add(provincias.getJSONObject(i).getString("id"));
		}
		return listaProvincias;
	}

	public List<String> getCiudadesID() {
		List<String> ciudadesID = new ArrayList<>();
		List<String> provinciasID = this.getProvinciasID();
		for (int i = 0; i < provinciasID.size(); i++) {
			JSONObject json = this.obtenerJsonDe("states/" + provinciasID.get(i));
			JSONArray ciudades = json.getJSONArray("cities");
			for (int x = 0; x < ciudades.length(); x++) {
				ciudadesID.add(ciudades.getJSONObject(x).getString("id"));
			}
		}
		return ciudadesID;
	}

	public List<Provincia> obtenerProvincias() {
		List<Provincia> lista = new ArrayList<Provincia>();
		List<String> provinciasID = this.getProvinciasID();

		for (int i = 0; i < provinciasID.size(); i++) {
			JSONObject json = this.obtenerJsonDe("states/" + provinciasID.get(i));
			String pais = json.getJSONObject("country").getString("name");
			String provincia = json.getString("name");

			lista.add(new Provincia(pais, provincia));
		}

		return lista;
	}

	public List<Ciudad> obtenerCiudades() {
		List<Ciudad> lista = new ArrayList<>();
		List<String> ciudadesID = this.getCiudadesID();
		RepositorioProvincias repoProvincias = RepositorioProvincias.getInstance();

		for (int i = 0; i < ciudadesID.size(); i++) {
			JSONObject json = this.obtenerJsonDe("cities/" + ciudadesID.get(i));
			String provincia = json.getJSONObject("state").getString("name");
			String ciudad = json.getString("name");

			lista.add(new Ciudad(repoProvincias.obtenerProvincia(provincia), ciudad));
		}

		return lista;
	}


}