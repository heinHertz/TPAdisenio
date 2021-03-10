package dominio.entidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.CollectionType;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import javax.persistence.*;

import dominio.notificacion.ReporteGastos;
import dominio.operacion.DireccionPostal;
import dominio.operacion.Operacion;
import dominio.usuario.Usuario;

@Entity
@Table(name = "EntidadesJuridicas")
public class EntidadJuridica implements WithGlobalEntityManager {

	@Id
	@GeneratedValue
	private Long id;

	private String nombreFicticio;
	private String razonSocial;
	private String CUIT;

	@OneToOne(cascade = {CascadeType.ALL})
	public DireccionPostal direccionPostal;

	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name = "id_entidad_juridica")
	public List<EntidadBase> entidadesBases = new ArrayList<EntidadBase>();

	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name = "id_entidad_juridica")
	public List<Operacion> operaciones = new ArrayList<Operacion>();

	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name = "id_entidad_juridica")
	public List<ReporteGastos> reporteGastos = new ArrayList<ReporteGastos>();

	@OneToOne
	private Categoria categoria;

	@Enumerated(EnumType.STRING)
	public TipoEmpresa tipoEmpresa;

	@Transient
	public int cantidadLimiteDeEntidadBase = 10;

	@Transient
	public int cantidadLimiteDeOperaciones = 10;

	public EntidadJuridica() {
	}

	public EntidadJuridica(String nombreFicticio, String razonSocial,
												 String CUIT, DireccionPostal direccionPostal, TipoEmpresa tipoEmpresa) {
		this.nombreFicticio = nombreFicticio;
		this.razonSocial = razonSocial;
		this.CUIT = CUIT;
		this.direccionPostal = direccionPostal;
		this.tipoEmpresa = tipoEmpresa;
	}

	public EntidadJuridica(String unNombreFicticio, String unaRazonSocial, String unCUIT, DireccionPostal string, Categoria categoria, TipoEmpresa tipoEmpresa) {
		this.nombreFicticio = unNombreFicticio;
		this.razonSocial = unaRazonSocial;
		this.CUIT = unCUIT;
		this.direccionPostal = string;
		this.entidadesBases = new ArrayList<EntidadBase>();
		this.categoria = categoria;
		this.tipoEmpresa = tipoEmpresa;
	}


	public void aplicarReglasDeCategoria() {
		this.categoria.efectivizarReglas(this);
	}

	public void agregarOperacion(Operacion operacion) {
		if (cantidadLimiteDeOperaciones > this.operaciones.size()) {
			this.operaciones.add(operacion);
		}
	}

	public void agregarEntidadBase(EntidadBase entidad) {
		if (cantidadLimiteDeEntidadBase > this.entidadesBases.size()) {
			this.entidadesBases.add(entidad);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreFicticio() {
		return nombreFicticio;
	}

	public void setNombreFicticio(String nombreFicticio) {
		this.nombreFicticio = nombreFicticio;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getCUIT() {
		return CUIT;
	}

	public void setCUIT(String CUIT) {
		this.CUIT = CUIT;
	}

	public DireccionPostal getDireccionPostal() {
		return direccionPostal;
	}

	public void setDireccionPostal(DireccionPostal direccionPostal) {
		this.direccionPostal = direccionPostal;
	}

	public List<EntidadBase> getEntidadesBases() {
		return entidadesBases;
	}

	public void setEntidadesBases(List<EntidadBase> entidadesBases) {
		this.entidadesBases = entidadesBases;
	}

	public List<Operacion> getOperaciones() {
		return operaciones;
	}

	public void setOperaciones(List<Operacion> operaciones) {
		this.operaciones = operaciones;
	}

	public List<ReporteGastos> getReporteGastos() {
		return reporteGastos;
	}

	public void setReporteGastos(List<ReporteGastos> reporteGastos) {
		this.reporteGastos = reporteGastos;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public int getCantidadLimiteDeEntidadBase() {
		return cantidadLimiteDeEntidadBase;
	}

	public void setCantidadLimiteDeEntidadBase(int cantidadLimiteDeEntidadBase) {
		this.cantidadLimiteDeEntidadBase = cantidadLimiteDeEntidadBase;
	}

	public int getCantidadLimiteDeOperaciones() {
		return cantidadLimiteDeOperaciones;
	}

	public void setCantidadLimiteDeOperaciones(int cantidadLimiteDeOperaciones) {
		this.cantidadLimiteDeOperaciones = cantidadLimiteDeOperaciones;
	}

	public void generarReporteGastos() {

		Calendar calendario = Calendar.getInstance();

		this.getOperaciones().stream()
				.filter(operaciones -> operaciones.getFechaOperacion().getMonthValue() == calendario.get(Calendar.MONTH)
						&& operaciones.getFechaOperacion().getYear() == calendario.get(Calendar.YEAR))
				.collect(Collectors.groupingBy(Operacion::getEtiqueta,
						Collectors.reducing(BigDecimal.ZERO, Operacion::valorTotal, BigDecimal::add)))
				.forEach((getEtiqueta, valorTotal) -> {

					ReporteGastos gasto = new ReporteGastos(getEtiqueta, (BigDecimal) valorTotal);
					this.reporteGastos.add(gasto);

				});
	}


}

