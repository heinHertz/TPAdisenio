package model;

import dominio.entidades.EntidadBase;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepositorioEntidadesBase implements WithGlobalEntityManager {

	public static RepositorioEntidadesBase instancia = new RepositorioEntidadesBase();

	public List<EntidadBase> listar() {
		return entityManager()
				.createQuery("from EntidadBase", EntidadBase.class)
				.getResultList();
	}

	public EntidadBase buscar(long id) {
		return entityManager().find(EntidadBase.class, id);
	}

	public void agregar(EntidadBase entidadBase) {
		entityManager().persist(entidadBase);
	}
}
