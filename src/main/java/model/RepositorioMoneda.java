package model;

import api.Moneda;
import api.ServicioDeMonedaMercadoLibre;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class RepositorioMoneda implements WithGlobalEntityManager , TransactionalOps  {

	private static RepositorioMoneda instance;

	public static RepositorioMoneda getInstance() {
		if (instance == null)
			instance = new RepositorioMoneda();

		return instance;
	}

	public static Moneda getMonedasAPersistir(ServicioDeMonedaMercadoLibre api) {
		return api.obtenerMoneda();
	}

	public Moneda getMoneda() {
		String descripcion = "Peso argentino";
		
		return entityManager().createQuery("select c from Moneda c where c.simbolo LIKE \'$\' and c.descripcion like :descripcion ", Moneda.class)
				.setParameter("descripcion", "%" + descripcion + "%")
				.getSingleResult();
				
	}

	public Moneda buscarPorDescripcion(String descripcion){
		return entityManager()
				.createQuery("from Moneda m where m.descripcion like :descripcion ", Moneda.class)
				.setParameter("descripcion", "%" + descripcion + "%")
				.getResultList()
				.get(0);
	}

	
}
