package model;

import java.util.ArrayList;
import java.util.List;

import dominio.entidades.EntidadJuridica;

public class RepositorioEntidades {

	public List<EntidadJuridica> entidades = new ArrayList<EntidadJuridica>();
	private static RepositorioEntidades INSTANCIA;

	public List<EntidadJuridica> entidades() {
		return this.entidades;
	}

	public static RepositorioEntidades getInstance() {
		if (INSTANCIA == null) RepositorioEntidades.INSTANCIA = new RepositorioEntidades();
		return INSTANCIA;
	}

	public void agregarEntidad(EntidadJuridica entidad) {
		this.entidades.add(entidad);
	}

	public void validarEntidades() {
		entidades.stream().forEach(entidad -> validarTodasOperacionesDeEntidad(entidad)); // con efecto para todas las
	}

	public void validarTodasOperacionesDeEntidad(EntidadJuridica entidad) {
		entidad.getOperaciones().stream().forEach(operacion -> operacion.validarCompras());
	}

}
