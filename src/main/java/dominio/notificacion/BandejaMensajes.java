package dominio.notificacion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.usuario.Usuario;

@Entity
@Table(name = "Bandejas")
public class BandejaMensajes implements WithGlobalEntityManager {

	@Id
	@GeneratedValue
	public Long id_bandeja;

	@ElementCollection(targetClass = String.class)
	@CollectionTable(name = "MensajesDeBandeja", joinColumns = {@JoinColumn(name = "id_bandeja")})
	public List<String> mensajes = new ArrayList<String>();


	public List<String> getMensajes() {
		return mensajes;
	}

	public void addMensaje(String mensaje) {
		this.mensajes.add(mensaje);
	}

	public void sendMensajes(String mensaje) {

		this.mensajes.add(mensaje);

	}

	public Long getId_bandeja() {
		return id_bandeja;
	}

	public void setId_bandeja(Long id_bandeja) {
		this.id_bandeja = id_bandeja;
	}

	public void setMensajes(List<String> mensajes) {
		this.mensajes = mensajes;
	}

//		LEVANTAR DATOS DE LA DB

	public BandejaMensajes obtenerBandejaMensajesId(Integer idBandeja) {

		BandejaMensajes BandejaMensajes = (BandejaMensajes) entityManager().createQuery("select t from BandejaMensajes t where  t.id_bandeja = " + idBandeja.toString(), BandejaMensajes.class).getSingleResult();

		return BandejaMensajes;
	}

	public List<String> obtenerTodosMensajeDeUnaBandeja(Long long1) {

		List<String> mensajes = entityManager().createQuery("select p from BandejaMensajes u inner join u.mensajes p where u.id_bandeja = " + long1.toString(), String.class).getResultList();

		return mensajes;
	}

	public void persitir() {

		entityManager().persist(this);

	}

	public List<BandejaMensajes> obtenerTodosLosBandejaMensajess() {

		List<BandejaMensajes> BandejaMensajess = entityManager().createQuery("select t from BandejaMensajes t ", BandejaMensajes.class).getResultList();

		return BandejaMensajess;
	}

	 public BandejaMensajes obtenerBandejaMensajesID(Long id) {

		BandejaMensajes bandejaMensajes = (BandejaMensajes) entityManager().createQuery("select t from BandejaMensajes t where t.id_bandeja = " + id.toString(), BandejaMensajes.class).getSingleResult();

		return bandejaMensajes;
	}


}
