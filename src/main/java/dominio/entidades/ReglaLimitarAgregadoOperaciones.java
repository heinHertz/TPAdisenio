package dominio.entidades;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("3")
public class ReglaLimitarAgregadoOperaciones extends Regla {

	public ReglaLimitarAgregadoOperaciones() {
		etiquetaRegla = "Limita IngresoDeNueva Operaciones Base";
	}

	@Override
	public void aplicarRegla(EntidadJuridica entidad) {
		entidad.setCantidadLimiteDeOperaciones(entidad.operaciones.size());
	}

}

