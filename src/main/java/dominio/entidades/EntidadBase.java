package dominio.entidades;

import java.util.List;

import javax.persistence.*;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.usuario.Usuario;

@Entity
@Table(name = "EntidadesBase")
public class EntidadBase implements WithGlobalEntityManager {

	@Id
	@GeneratedValue
	public long id;

	private String nombreFicticio;
	private String descripcion;

	public EntidadBase() {
	} //NO SACAR

	public EntidadBase(String nombre, String descripcion) {

		this.nombreFicticio = nombre;

		this.descripcion = descripcion;

	}

	public Long getId() {
		return id;
	}

	public String getNombreFicticio() {
		return nombreFicticio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNombreFicticio(String nombreFicticio) {
		this.nombreFicticio = nombreFicticio;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
