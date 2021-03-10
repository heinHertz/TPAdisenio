package ar.edu.frba.dds.gesoc.Main;

import api.*;
import model.RepositorioCiudades;
import model.RepositorioMoneda;
import model.RepositorioProvincias;
import model.RepositorioUsuarios;
import dominio.usuario.TipoUsuario;
import dominio.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import java.util.List;

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {
	public static void main(String[] args) {
		new Bootstrap().run();
	}

	public void run() {

		withTransaction(() -> {
			persistirProvinciasYCiudades();

			persistirUsuarios();
		});

	}


	public void persistirProvinciasYCiudades() {
		List<Provincia> provincias = RepositorioProvincias.getProvinciasAPersistir(new ServicioDeUbicacionMercadoLibre());

		for (Provincia provincia : provincias) {
			persist(provincia);
		}

		for (Ciudad ciudad : RepositorioCiudades.getCiudadesAPersistir(new ServicioDeUbicacionMercadoLibre())) {
			persist(ciudad);
		}

		persist(RepositorioMoneda.getMonedasAPersistir(new ServicioDeMonedaMercadoLibre()));
	}


	public void persistirUsuarios() {
		RepositorioUsuarios.getInstance().agregarUsuario(new Usuario("brian", "a1b2c5h5j8e9c", TipoUsuario.STANDARD));
		RepositorioUsuarios.getInstance().agregarUsuario(new Usuario("fede", "1M2F3a5r8a7", TipoUsuario.STANDARD));

		for (Usuario usuario : RepositorioUsuarios.getInstance().getUsuariosAPersistir()) {
			persist(usuario);
		}

	}

}
