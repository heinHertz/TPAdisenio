package api;

import java.util.List;

import javax.persistence.*;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.usuario.Usuario;

@Entity
@Table(name = "Ciudades")
public class Ciudad implements WithGlobalEntityManager {

	@Id
	@GeneratedValue
	public Long id;

	@ManyToOne
	private Provincia provincia;

	private String nombre;

	public Ciudad() {
	}  // HIBERNATE NECESITA CONSTRUTOR VACIO

	public Ciudad(Provincia provincia, String nombre) {
		this.provincia = provincia;
		this.nombre = nombre;
	}

	public Ciudad(String nombre){
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

}