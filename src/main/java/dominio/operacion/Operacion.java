package dominio.operacion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.notificacion.BandejaMensajes;

@Entity
@Table(name = "Operaciones")
public class Operacion implements WithGlobalEntityManager  , TransactionalOps {

	@Id
	@GeneratedValue
	public Long id_operacion;

	public LocalDate fechaOperacion;
	public String etiqueta;

	@Enumerated(EnumType.STRING)
	public MedioPago medioPago; //lo guarda como entero sin @Enumerated

	public String detalle;
	public BigDecimal valorTotal;
	public Integer cantidadPresupuestosCompra;

	@OneToOne(cascade = {CascadeType.ALL})    //NO MODIFICAR
	private Documento documento;

	@OneToOne(cascade = {CascadeType.ALL})    //NO MODIFICAR
	public Proveedor proveedor;


	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name = "id_operacion")
	public List<Presupuesto> presupuestos = new ArrayList<Presupuesto>();

	public List<Presupuesto> getPresupuestos() {
		return presupuestos;
	}

	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name = "id_operacion")
	public List<BandejaMensajes> observadores = new ArrayList<BandejaMensajes>();


	public void setCantidadPresupuestosCompra(int cantidadPresupuestosCompra) {
		this.cantidadPresupuestosCompra = cantidadPresupuestosCompra;
	}

	public BigDecimal valorTotal() {
		return valorTotal;
		//return items.stream().map(item -> item.valor()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public Operacion() {
	}

	//Constructor
	public Operacion(String detalle, LocalDate fecha, String etiqueta, BigDecimal valorTotal, MedioPago medio, Documento documento, Integer cantidadPresupuestosCompra) {
		this.cantidadPresupuestosCompra = cantidadPresupuestosCompra;
		this.valorTotal = valorTotal;
		this.detalle = detalle;
		this.medioPago = medio;
		this.documento = documento;
		this.fechaOperacion = fecha;
		this.etiqueta = etiqueta;
	}

	public boolean validarCompras() {
		if (this.tieneCompraTodosSusPresupuestos() && this.estaLaCompraBasadaEnAlgunPresupuesto()) {
			notificarObservadores("Compra Validada");
			return true;
		}
		return false;
	}

	public void asociarPresupuesto(Presupuesto presupuesto) {
		this.presupuestos.add(presupuesto);
	}

	public boolean tieneCompraTodosSusPresupuestos() {

		return cantidadPresupuestosCompra == presupuestos.stream().count();

	}

	public boolean estaLaCompraBasadaEnAlgunPresupuesto() {

		return presupuestos.stream().anyMatch(presupuesto -> presupuesto.estaBasadoEnLaCompra(this));
	}

	public void notificarObservadores(String mensaje) {
		observadores.stream().forEach(observador -> observador.sendMensajes(mensaje));
	}

	public void agregarObservadores(BandejaMensajes bandeja) {

		observadores.add(bandeja);

	}

	public void removerObservadores(BandejaMensajes bandeja) {

		observadores.remove(bandeja);
	}

	public LocalDate getFechaOperacion() {
		return fechaOperacion;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public List<Presupuesto> presupuestos() {
		return presupuestos;
	}

	public void setFechaOperacion(int anio, int mes, int dia) {
		this.fechaOperacion = LocalDate.of(anio, mes, dia);
	}

	public Long getId_operacion() {
		return id_operacion;
	}

	public Documento getDocumento() {
		return documento;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setId_operacion(Long id_operacion) {
		this.id_operacion = id_operacion;
	}

	public void setFechaOperacion(LocalDate fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public void setMedioPago(MedioPago medioPago) {
		this.medioPago = medioPago;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public void setCantidadPresupuestosCompra(Integer cantidadPresupuestosCompra) {
		this.cantidadPresupuestosCompra = cantidadPresupuestosCompra;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public void setPresupuestos(List<Presupuesto> presupuestos) {
		this.presupuestos = presupuestos;
	}

	public void setObservadores(List<BandejaMensajes> observadores) {
		this.observadores = observadores;
	}

	public MedioPago getMedioPago() {
		return medioPago;
	}

	public String getDetalle() {
		return detalle;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}


	public Integer getCantidadPresupuestosCompra() {
		return cantidadPresupuestosCompra;
	}

	public List<BandejaMensajes> getObservadores() {
		return observadores;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}


	//	PARA LEVANTAR LOS DATOS DE LA BD
	public Operacion obtenerOperacionDetalle(String nombre) {

		Operacion Operacion = (Operacion) entityManager().createQuery("select t from Operacion t where t.detalle LIKE '%" + nombre + "%' ", Operacion.class).getResultList().get(0);

		return Operacion;
	}

	public void persitir() {

		entityManager().persist(this);

	}

/*	public List<Operacion> obtenerTodosLosOperaciones() {

		List<Operacion> Operacions = new ArrayList<Operacion>();
		//-------------
		EntityTransaction tx;
		
		tx = entityManager().getTransaction();

		if (!tx.isActive()) {
			tx.begin();
		}

		withTransaction(() -> {			
			
			List<Operacion> Operaciones = entityManager().createQuery("select t from Operacion t ", Operacion.class).getResultList();

		//	tx.commit();
		 
		  return Operaciones;
		
		});
		
		return Operacions;
	}*/

	public Operacion obtenerOperacionID(Long id) {

		Operacion Operacion = (Operacion) entityManager().createQuery("select t from Operacion t where t.id_operacion = " + id.toString(), Operacion.class).getSingleResult();

		return Operacion;
	}

	public boolean existeOperacionConID(Long id) {

		try {
			Operacion operacion = (Operacion) entityManager().createQuery("select t from Operacion t where t.id_operacion = " + id.toString(), Operacion.class).getSingleResult();
		} catch (NoResultException e) {
			return false;
		}

		return true;
	}
	public List<BandejaMensajes> obtenerTodasLasBandejasMensajesDeOperacionId(Long id) {

		List<BandejaMensajes> bandejasMensajes =  entityManager().createQuery("select p from Operacion t inner join t.observadores p where t.id_operacion = "+ id.toString() , BandejaMensajes.class).getResultList();

		return bandejasMensajes;
	}


}
