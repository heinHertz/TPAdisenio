package dominio.entidades;

import java.util.List;

import javax.persistence.*;


@Entity
@DiscriminatorValue("1")
public class ReglaLimitarAgregadoEntidadesBase extends Regla {

	public ReglaLimitarAgregadoEntidadesBase() {
		etiquetaRegla = "LimitaNuevoIngreso Entidades Base";
	}

	@Override
	public void aplicarRegla(EntidadJuridica entidad) {
		entidad.setCantidadLimiteDeEntidadBase(entidad.entidadesBases.size());
	}

}

