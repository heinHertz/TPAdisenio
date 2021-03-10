package dominio.notificacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.entidades.EntidadJuridica;
import dominio.usuario.Usuario;


@Entity
@Table(name = "ReportesGastos")
public class ReporteGastos implements WithGlobalEntityManager {

	@Id
	@GeneratedValue
	public long id_gastos;

	String etiqueta;

	BigDecimal valorTotal;

	public ReporteGastos() {
	}

	public ReporteGastos(String etiqueta, BigDecimal valorTotal) {

		this.etiqueta = etiqueta;
		this.valorTotal = valorTotal;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Long getId_gastos() {
		return id_gastos;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setId_gastos(Long id_gastos) {
		this.id_gastos = id_gastos;
	}


	// PARA LEVANTAR LOS DATOS DE LA DB0
	public ReporteGastos obtenerReporteGastosEtiqueta(String nombre) {

		ReporteGastos ReporteGastos = (ReporteGastos) entityManager().createQuery("select t from ReporteGastos t where t.nombre LIKE '%" + nombre + "%' ", ReporteGastos.class).getResultList().get(0);

		return ReporteGastos;
	}

	public void persitir() {

		entityManager().persist(this);

	}

	public List<ReporteGastos> obtenerTodosLosReporteGastoss() {

		List<ReporteGastos> ReporteGastoss = entityManager().createQuery("select t from ReporteGastos t ", ReporteGastos.class).getResultList();

		return ReporteGastoss;
	}

	public ReporteGastos obtenerReporteGastosID(Integer id) {

		ReporteGastos ReporteGastos = (ReporteGastos) entityManager().createQuery("select t from ReporteGastos t where t.id_gastos = " + id.toString(), ReporteGastos.class).getSingleResult();

		return ReporteGastos;
	}


}
