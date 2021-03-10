package api;

import com.sun.jersey.api.client.ClientResponse;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServicioDeUbicacionMercadoLibreMock implements ServicioDeUbicacion, WithGlobalEntityManager {

	@Mock
	ServicioDeUbicacionMercadoLibre api = Mockito.mock(ServicioDeUbicacionMercadoLibre.class);

	public static ClientResponse requestAPI(String path) {
		ClientResponse response = Mockito.mock(ClientResponse.class);
		Mockito.when(response.getStatus()).thenReturn(200);
		Mockito.when(response.getEntity(String.class)).thenReturn("Argentina Colombia Peso argentino Peso colombiano");
		return response;
	}

	public List<Provincia> obtenerProvincias() {
		Provincia Provincia = new Provincia("Argentina", "Capital Federal");
		List<api.Provincia> listaProvincia = Arrays.asList(Provincia);

		Mockito.when(api.obtenerProvincias()).thenReturn(listaProvincia);
		return api.obtenerProvincias();
	}

	public List<Ciudad> obtenerCiudades() {
		List<Ciudad> listaCiudades = new ArrayList<>();
		listaCiudades.add(new Ciudad("Longschamps"));
		listaCiudades.add(new Ciudad("Laferrere"));

		Mockito.when(api.obtenerCiudades()).thenReturn(listaCiudades);
		return api.obtenerCiudades();
	}


}
