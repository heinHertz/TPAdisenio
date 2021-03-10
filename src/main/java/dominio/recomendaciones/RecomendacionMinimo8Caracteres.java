package dominio.recomendaciones;

public class RecomendacionMinimo8Caracteres implements Recomendacion {

	String descripcion = "que la contrase�a tenga al menos 8 caracteres";

	public Boolean esRecomendable(String contraseniaPosible) {

		return contraseniaPosible.length() > 8;

	}

}
