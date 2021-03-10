package model;

import api.Provincia;
import api.ServicioDeUbicacion;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepositorioProvincias implements WithGlobalEntityManager {

	private static RepositorioProvincias instance;

	//Solo usar para testear con mock
	public RepositorioProvincias() {
	}

	public static RepositorioProvincias getInstance() {
		if (instance == null)
			instance = new RepositorioProvincias();

		return instance;
	}

	public static List<Provincia> getProvinciasAPersistir(ServicioDeUbicacion api) {
		return api.obtenerProvincias();
	}

	public Boolean validarQueExisteProvincia(String provincia) {
		return listar().stream().anyMatch(direc -> direc.getNombre().equals(provincia));
	}

	public Provincia obtenerProvincia(String nombre) {
		return entityManager() //
				.createQuery("from Provincia c where c.nombre like :nombre", Provincia.class) //
				.setParameter("nombre", "%" + nombre + "%") //
				.getResultList().get(0);
	}

	public List<Provincia> listar() {
		return entityManager()
				.createQuery("from Provincia", Provincia.class)
				.getResultList();
	}

}
