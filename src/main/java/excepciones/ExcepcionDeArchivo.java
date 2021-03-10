package excepciones;

public class ExcepcionDeArchivo extends RuntimeException {

	//Atributo
	private String descipcion;

	//Metodo
	public ExcepcionDeArchivo(String string) {
		this.descipcion = string;
	}
}
