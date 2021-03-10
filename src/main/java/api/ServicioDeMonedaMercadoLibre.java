package api;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;

public class ServicioDeMonedaMercadoLibre {

	private Client client = Client.create();
	private static final String CURRENCIES = "currencies/ARS";

	public ClientResponse requestAPI(String path) {
		ClientResponse response = this.client.resource("https://api.mercadolibre.com/").path(path)
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		return response;
	}

	public Moneda obtenerMoneda() {
		ClientResponse response = requestAPI(CURRENCIES);
		String info = response.getEntity(String.class);
		JSONObject json = new JSONObject(info);
		String simbolo = json.getString("symbol");
		String descripcion = json.getString("description");
		return new Moneda(simbolo, descripcion);
	}
}
