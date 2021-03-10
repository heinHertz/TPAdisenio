package dominio.operacion;

import javax.persistence.*;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import api.Ciudad;
import api.Moneda;
import api.Provincia;


@Entity
@Table(name = "DireccionesPostales")
public class DireccionPostal implements WithGlobalEntityManager {

	@Id
	@GeneratedValue
	public Long id;

	public String direccion;

	@ManyToOne(cascade = {CascadeType.ALL}) //NECESARIO SI NO NO COMPILA unsaved transient instance
	public Ciudad ciudad;

	@ManyToOne(cascade = {CascadeType.ALL})
	public Provincia provincia;

	public String pais;

	@ManyToOne(cascade = {CascadeType.ALL})
	public Moneda moneda;

	public DireccionPostal() {
	}

	public DireccionPostal(String direccion, Ciudad ciudad, Provincia provincia, String pais, Moneda moneda) {
		this.direccion = direccion;
		this.ciudad = ciudad;
		this.provincia = provincia;
		this.pais = pais;
		this.moneda = moneda;

	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public String getDireccion() {
		return direccion;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public String getPais() {
		return pais;
	}
	
	

}
