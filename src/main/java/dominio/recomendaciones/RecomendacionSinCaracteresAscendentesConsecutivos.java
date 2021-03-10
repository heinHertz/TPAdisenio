package dominio.recomendaciones;

public class RecomendacionSinCaracteresAscendentesConsecutivos implements Recomendacion {

	String descripcion = " que la contrasenia no tenga 2 caracteres ascendentes consecutivos";

	public Boolean esRecomendable(String contrasenia) {

		for (int x = 0; x < contrasenia.length() - 1; x++) {

			if (((int) contrasenia.charAt(x + 1)) == (((int) (contrasenia.charAt(x))) + 1)) {
				return false;
			}

		}
		return true;

	}

}
