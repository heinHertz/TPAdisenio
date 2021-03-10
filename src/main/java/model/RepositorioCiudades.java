package model;

import api.Ciudad;
import api.ServicioDeUbicacion;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.ArrayList;
import java.util.List;

public class RepositorioCiudades implements WithGlobalEntityManager {

	private static RepositorioCiudades instance;
	
	public static List<Ciudad> ciudades = new ArrayList<Ciudad>();

	public static RepositorioCiudades getInstace() {
		if (instance == null)
			instance = new RepositorioCiudades();

		return instance;
	}

	public static List<Ciudad> getCiudadesAPersistir(ServicioDeUbicacion api) {
		
		ciudades = api.obtenerCiudades();
		
		return ciudades;
	}

	public Boolean validarQueExisteCiudad(String ciudad) {
		
		return ciudades.stream().anyMatch(city -> city.getNombre().equals(ciudad));
		
	}

	public Ciudad buscarPorNombre(String nombre) {
		return entityManager()
				.createQuery("select c from Ciudad c where c.nombre like :nombre ", Ciudad.class)
				.setParameter("nombre", "%" + nombre + "%")
				.getResultList()
				.get(0);
	}

	public Ciudad buscar(long id) {
		return entityManager().find(Ciudad.class, id);
	}

	public List<Ciudad> listar(){
		return entityManager()
				.createQuery("select c from Ciudad c", Ciudad.class)
				.getResultList();
	}

}
