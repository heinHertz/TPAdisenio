package dominio.operacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.usuario.Usuario;

@Entity
@Table(name = "presupuestos")
public class Presupuesto implements WithGlobalEntityManager {

	@Id
	@GeneratedValue
	public long id_presupuesto;

	@OneToMany(cascade = {CascadeType.ALL}) //NECESARIO SI NO NO COMPILA 
	@JoinColumn(name = "id_presupuesto")
	public List<Item> items = new ArrayList<Item>();


	public BigDecimal valorTotalPresupuesto() {
		return items.stream().map(item -> item.valor()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public boolean tieneAlgunItemBasadoEnCompra(Operacion compra) {
		return this.items.stream().anyMatch(item -> item.detalle().equals(compra.detalle) && item.valor().equals(compra.valorTotal));
	}

	public void agregarItem(Item item) {
		this.items.add(item);
	}

	public boolean estaBasadoEnLaCompra(Operacion compra) {
		return tieneAlgunItemBasadoEnCompra(compra);
//		return tieneMismaCantidadDeItemsQueCompra(compra) && tieneAlgunItemBasadoEnCompra(compra)
//				&& esElMenorPresupuesto(compra);
	}

	public boolean esElMenorPresupuesto(Operacion compra) {
		BigDecimal valorPresupuesto = this.valorTotalPresupuesto();
		return compra.presupuestos().stream().allMatch(presupuesto -> presupuesto.valorTotalPresupuesto().compareTo(valorPresupuesto) >= 0);
	}

	public List<Item> items() {
		return this.items;
	}

	public long getId_presupuesto() {
		return id_presupuesto;
	}

	public void setId_presupuesto(long id_presupuesto) {
		this.id_presupuesto = id_presupuesto;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	//	PARA LEVANTAR LOS DATOS DE LA BD
	public void persitir() {

		entityManager().persist(this);

	}

	public List<Presupuesto> obtenerTodosLosPresupuestos() {

		List<Presupuesto> Presupuestos = entityManager().createQuery("select t from Presupuesto t ", Presupuesto.class).getResultList();

		return Presupuestos;
	}

	public Presupuesto obtenerPresupuestoID(Integer id) {

		Presupuesto Presupuesto = (Presupuesto) entityManager().createQuery("select t from Presupuesto t where t.id_presupuesto = " + id.toString(), Presupuesto.class).getSingleResult();

		return Presupuesto;
	}

}