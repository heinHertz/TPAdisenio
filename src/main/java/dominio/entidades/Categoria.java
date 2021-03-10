package dominio.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import dominio.operacion.Operacion;
import dominio.entidades.EntidadJuridica;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;


@Entity
@Table(name = "Categorias")
public class Categoria implements WithGlobalEntityManager {

	@Id
	@GeneratedValue
	private Long id;

	private String nombreCategoria;

	@OneToMany(cascade = {CascadeType.ALL})    //NO MODIFICAR
	@JoinColumn(name = "categoria_id")
	List<Regla> reglas = new ArrayList<Regla>();

	public Categoria() {
	}

	public Categoria(String nombre) {
		this.nombreCategoria = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public void agregarRegla(Regla regla) {
		this.reglas.add(regla);
	}

	public void efectivizarReglas(EntidadJuridica entidad) {
		this.reglas.stream().forEach(regla -> regla.aplicarRegla(entidad));
	}

	public void removerRegla(Regla regla) {
		this.reglas.remove(regla);
	}

	public List<Regla> getReglas() {
		return reglas;
	}

	public void setReglas(List<Regla> reglas) {
		this.reglas = reglas;
	}

}

	

	
