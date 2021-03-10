package dominio.usuario;

import java.util.List;

import javax.persistence.*;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.notificacion.BandejaMensajes;
import dominio.recomendaciones.Recomendador;
import excepciones.ExcepcionUsuario;

@Entity
@Table(name = "Usuarios")
public class Usuario implements WithGlobalEntityManager {

	@Id
	@GeneratedValue
	public Long id_usuario;

	public String nombre;

	public String contrasenia;

	@Enumerated(EnumType.STRING)
	TipoUsuario tipo;

	@Transient
	Recomendador recomendador;

	@OneToOne(cascade = {CascadeType.ALL})
	BandejaMensajes bandeja = new BandejaMensajes();

	public Usuario() {
	}

	public Usuario(String nombreUsuario, String contrasenia, TipoUsuario tipoUsuario) { 

		Recomendador recomendador = new Recomendador();

		if (recomendador.esRecomendable(contrasenia)) {
			this.nombre = nombreUsuario;

			this.contrasenia = contrasenia;

			this.tipo = tipoUsuario;

		}
	}


	public Boolean tieneEstaContrasenia(String contraseniaPosible) {

		return this.contrasenia.equals(contraseniaPosible);
	}


	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipo = tipoUsuario;
	}


	public TipoUsuario getTipoUsuario() {
		return tipo;
	}


	public Long getId_usuario() {
		return id_usuario;
	}


	public String getNombreUsuario() {
		return nombre;
	}


	public String getContrasenia() {
		return contrasenia;
	}


	public Recomendador getRecomendador() {
		return recomendador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombre = nombreUsuario;
	}

	public void setBandeja(BandejaMensajes bandeja) {
		this.bandeja = bandeja;
	}

	public BandejaMensajes getBandeja() {
		return bandeja;
	}


	//	LEVANTAR DATOS DE LA DB
	public Usuario obtenerUsuarioNombre(String nombre) {

		Usuario usuario = (Usuario) entityManager().createQuery("select t from Usuario t where t.nombre LIKE '%" + nombre + "%' ", Usuario.class).getResultList().get(0);

		return usuario;
	}

	public void persitir() {

		entityManager().persist(this);

	}

	public List<Usuario> obtenerTodosLosUsuarios() {

		List<Usuario> usuarios = entityManager().createQuery("select t from Usuario t ", Usuario.class).getResultList();

		return usuarios;
	}

	public Usuario obtenerUsuarioID(Long id) {

		Usuario usuario = (Usuario) entityManager().createQuery("select t from Usuario t where t.id_usuario = " + id.toString(), Usuario.class).getSingleResult();

		return usuario;
	}

	public Usuario obtenerUsuariNombre(String nombre) {

		Usuario usuario = (Usuario) entityManager().createQuery("select t from Usuario t where t.nombre like '" + nombre + "' ", Usuario.class).getSingleResult();

		return usuario;
	}

	public boolean validarContrasenia(String password) {

		return this.contrasenia.equals(password);

	}


}

	