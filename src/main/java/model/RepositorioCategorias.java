package model;

import dominio.entidades.Categoria;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioCategorias implements WithGlobalEntityManager {
	public static RepositorioCategorias instancia = new RepositorioCategorias();

	public void agregar(Categoria categoria){
		entityManager().persist(categoria);
	}
}
