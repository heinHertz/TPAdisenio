package model;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.operacion.Operacion;


public class RepositorioOperaciones implements WithGlobalEntityManager {

	private static RepositorioOperaciones instance;

	//Solo usar para testear con mock
	public RepositorioOperaciones() {
	}

	public static RepositorioOperaciones getInstance() {
		if (instance == null)
			instance = new RepositorioOperaciones();

		return instance;
	}


	public Operacion obtenerOperacion(String detalle) {
		return entityManager() //
				.createQuery("from Operacion c where c.detalle like :detalle", Operacion.class) //
				.setParameter("detalle", "%" + detalle + "%") //
				.getResultList().get(0);
	}

	public List<Operacion> listar() {
		return entityManager()
				.createQuery("from Operacion", Operacion.class)
				.getResultList();
	}

}
