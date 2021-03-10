package dominio.entidades;

import java.util.List;

import javax.persistence.*;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.usuario.Usuario;


@Entity
@Table(name = "reglas")
@DiscriminatorColumn(name = "discriminador_tipo_regla", discriminatorType = DiscriminatorType.INTEGER, length = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Regla implements WithGlobalEntityManager {

	@Id
	@GeneratedValue
	public Long id;

	public String etiquetaRegla;

	public void aplicarRegla(EntidadJuridica entidad) {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEtiquetaRegla() {
		return etiquetaRegla;
	}

	public void setEtiquetaRegla(String etiquetaRegla) {
		this.etiquetaRegla = etiquetaRegla;
	}

}
