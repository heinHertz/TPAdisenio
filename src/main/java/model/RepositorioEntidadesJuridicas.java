package model;

import dominio.entidades.EntidadJuridica;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepositorioEntidadesJuridicas implements WithGlobalEntityManager {

	public static RepositorioEntidadesJuridicas instancia = new RepositorioEntidadesJuridicas();

	public List<EntidadJuridica> listar() {
		return entityManager()
				.createQuery("from EntidadJuridica", EntidadJuridica.class)
				.getResultList();
	}

	public EntidadJuridica buscar(long id) {
		return entityManager().find(EntidadJuridica.class, id);
	}

	public void agregar(EntidadJuridica entidadJuridica) {
		entityManager().persist(entidadJuridica);
	}

}
