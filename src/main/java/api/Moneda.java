package api;


import java.util.List;

import javax.persistence.*;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.usuario.Usuario;

@Entity
@Table(name = "Monedas")
public class Moneda implements WithGlobalEntityManager {

	@Id
	@GeneratedValue
	public Long id;

	private String simbolo;

	private String descripcion;

	public Moneda() {
	} // HIBERNATE NECESITA

	public Moneda(String simbolo, String descripcion) {
		this.simbolo = simbolo;
		this.descripcion = descripcion;
	}


	public String getSimbolo() {
		return simbolo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

}
