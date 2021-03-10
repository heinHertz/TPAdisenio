package dominio.operacion;

import java.util.List;

import javax.persistence.*;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.usuario.Usuario;

@Entity
@Table(name = "Proveedores")
public class Proveedor implements WithGlobalEntityManager {

	@Id
	@GeneratedValue
	public long id;

	public int dni;

	public String nombre;

	@OneToOne(cascade = {CascadeType.ALL}) //NECESARIO SI NO NO COMPILA
	private DireccionPostal direccionPostal;

	public Proveedor() {
	}

	public Proveedor(String nombre, int dni, DireccionPostal direccionPostal) {
		this.nombre = nombre;
		this.dni = dni;
		this.direccionPostal = direccionPostal;
	}

	public Long getId() {
		return id;
	}

	public int getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}

	public DireccionPostal getDireccionPostal() {
		return direccionPostal;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDireccionPostal(DireccionPostal direccionPostal) {
		this.direccionPostal = direccionPostal;
	}


	//OBTENER DE LA BASE DE DATOS 
	public Proveedor obtenerProveedorNombre(String nombre) {

		Proveedor Proveedor = (Proveedor) entityManager().createQuery("select t from Proveedor t where t.nombre LIKE '%" + nombre + "%' ", Proveedor.class).getResultList().get(0);

		return Proveedor;
	}

	public void persitir() {

		entityManager().persist(this);

	}

	public List<Proveedor> obtenerTodosLosProveedors() {

		List<Proveedor> Proveedors = entityManager().createQuery("select t from Proveedor t ", Proveedor.class).getResultList();

		return Proveedors;
	}

	public Proveedor obtenerProveedorID(Integer id) {

		Proveedor Proveedor = (Proveedor) entityManager().createQuery("select t from Proveedor t where t.id = " + id.toString(), Proveedor.class).getSingleResult();

		return Proveedor;
	}


}
