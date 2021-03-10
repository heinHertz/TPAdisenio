package dominio.operacion;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

@Entity
@Table(name = "Items")
public class Item implements WithGlobalEntityManager {

	@Id
	@GeneratedValue
	public long id_item;

	public String detalle;

	public BigDecimal valor;

	public Item() {
	}

	public Item(String detalle, BigDecimal valor) {
		this.detalle = detalle;
		this.valor = valor;
	}

	// GETTERS Y SEETER NECESARIOS
	public BigDecimal valor() {
		return valor;
	}

	public String detalle() {
		return detalle;
	}

	public long getId_item() {
		return id_item;
	}

	public void setId_item(long id_item) {
		this.id_item = id_item;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	//	PARA LEVANTAR LOS DATOS DE LA BD
	public void persitir() {

		entityManager().persist(this);

	}

	public List<Item> obtenerTodosLosItems() {

		List<Item> items = entityManager().createQuery("select t from Item t ", Item.class).getResultList();

		return items;
	}

	public Item obtenerItemID(Integer id) {

		Item Item = (Item) entityManager().createQuery("select t from Item t where t.id_Item = " + id.toString(), Item.class).getSingleResult();

		return Item;
	}


}
