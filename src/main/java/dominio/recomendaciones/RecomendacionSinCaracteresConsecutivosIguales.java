package dominio.recomendaciones;

public class RecomendacionSinCaracteresConsecutivosIguales implements Recomendacion {

	String descripcion = " que la contrasenia no tenga 2 caracteres consecutivos repetidos";

	public Boolean esRecomendable(String contrasenia) {

		for (int x = 0; x < contrasenia.length() - 1; x++) {
			if (contrasenia.charAt(x) == contrasenia.charAt(x + 1)) {
				return false;
			}
		}
		return true;

	}

}
