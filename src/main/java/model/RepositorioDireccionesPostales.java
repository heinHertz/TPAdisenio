package model;

import dominio.operacion.DireccionPostal;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioDireccionesPostales implements WithGlobalEntityManager {

	public static RepositorioDireccionesPostales instancia = new RepositorioDireccionesPostales();

	public void agregar(DireccionPostal direccionPostal){
		entityManager().persist(direccionPostal);
	}
}
