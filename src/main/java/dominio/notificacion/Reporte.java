package dominio.notificacion;

import java.math.BigDecimal;

public class Reporte {

	String etiqueta;

	BigDecimal valorTotal;

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

}
