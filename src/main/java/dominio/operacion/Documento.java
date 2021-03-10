package dominio.operacion;

import java.util.List;

import javax.persistence.*;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.usuario.Usuario;

@Entity
@Table(name = "Documentos")
public class Documento implements WithGlobalEntityManager {

	@Id
	@GeneratedValue
	public long id;

	String nombre;


	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	TipoDeDocumento tipoDocumento;


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


	public TipoDeDocumento getTipoDocumento() {
		return tipoDocumento;
	}


	public void setTipoDocumento(TipoDeDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public void setId(long id) {
		this.id = id;
	}

	//ckt
	public Documento(String nombre, TipoDeDocumento tipo) {
		this.nombre = nombre;
		this.tipoDocumento = tipo;

	}

	public Documento() {
	}

	;    //NO MODIFICAR


	public Documento obtenerDocumentoNombre(String nombre) {

		Documento Documento = (Documento) entityManager().createQuery("select t from Documento t where t.nombre LIKE '%" + nombre + "%' ", Documento.class).getResultList().get(0);

		return Documento;
	}

	public void persitir() {

		entityManager().persist(this);

	}

	public List<Documento> obtenerTodosLosDocumentos() {

		List<Documento> documentos = entityManager().createQuery("select t from Documento t ", Documento.class).getResultList();

		return documentos;
	}

	public Documento obtenerDocumentoID(Integer id) {

		Documento Documento = (Documento) entityManager().createQuery("select t from Documento t where t.id = " + id.toString(), Documento.class).getSingleResult();

		return Documento;
	}


}
