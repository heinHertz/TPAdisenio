package api;

import java.util.List;

public interface ServicioDeUbicacion {

	List<Provincia> obtenerProvincias();

	List<Ciudad> obtenerCiudades();

	//List<Moneda> obtenerMonedas();
}
