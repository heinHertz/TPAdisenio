package api;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.usuario.Usuario;

@Entity
@Table(name = "provincias")
public class Provincia implements WithGlobalEntityManager {
	@Id
	@GeneratedValue
	public Long id;

	private String pais;
	private String nombre;

	public Provincia() {
	}  // necesario


	public Provincia(String pais, String provincia) {
		this.pais = pais;
		this.nombre = provincia;
	}

	public Provincia(Long id, String pais, String nombre) {
		this.id = id;
		this.pais = pais;
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}

