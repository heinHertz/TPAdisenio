package dominio.recomendaciones;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utiles.LectorDeArchivo;

public class Recomendador {

	private List<Recomendacion> recomendaciones = new ArrayList<Recomendacion>();

	static final String MOST_COMMON_PASSWORD_FILE = "10k-most-common.txt";

	public Recomendador() {

		this.recomendaciones.add(new RecomendacionMinimo8Caracteres());

		this.recomendaciones.add(new RecomendacionSinCaracteresConsecutivosIguales());

		this.recomendaciones.add(new RecomendacionSinCaracteresAscendentesConsecutivos());

	}

	public Boolean isCumpleRecomendacionesOWASP(String contrasenia) {

		return recomendaciones.stream().allMatch(p -> p.esRecomendable(contrasenia));

	}

	public boolean esRecomendable(String contrasenia) throws RuntimeException {

		return this.esContraseniaFuerte(contrasenia) &&
				this.isCumpleRecomendacionesOWASP(contrasenia);

	}

	public void agregarRecomendacion(Recomendacion recomendacion) {

		recomendaciones.add(recomendacion);
	}

	public boolean esContraseniaFuerte(String contrasenia) throws RuntimeException {

		//si el string esta en el archivo, no es fuerte
		return !LectorDeArchivo.existeEnElArchivo(contrasenia, this.MOST_COMMON_PASSWORD_FILE);


	}

}
