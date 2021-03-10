package utiles;

import java.io.*;
import java.net.URL;

public class LectorDeArchivo {


	// Metodo
	public static boolean existeEnElArchivo(String contrasenia, String nombreArchivo) throws RuntimeException {

		File file;

		ClassLoader classLoader = LectorDeArchivo.class.getClassLoader();

		URL resource = classLoader.getResource(nombreArchivo);
		if (resource == null) {
			throw new IllegalArgumentException("file is not found!");
		} else {
			file = new File(resource.getFile());
		}

		if (file == null) return false;

		try {
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);// {

			String line;
			while ((line = br.readLine()) != null) {
				if (line.contentEquals(contrasenia)) {
					br.close();
					return true;
				}
			}

			br.close();

		} finally {
			return false;
		}


	}
}
