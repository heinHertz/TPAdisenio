package model;

import dominio.entidades.Regla;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioReglas implements WithGlobalEntityManager {
	public static RepositorioReglas instancia = new RepositorioReglas();

	public void agregar(Regla regla){
		entityManager().persist(regla);
	}
}
