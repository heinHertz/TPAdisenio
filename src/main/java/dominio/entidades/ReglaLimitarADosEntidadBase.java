package dominio.entidades;

import java.util.List;

import javax.persistence.*;


@Entity
@DiscriminatorValue("4")
public class ReglaLimitarADosEntidadBase extends Regla {

	public ReglaLimitarADosEntidadBase() {
		etiquetaRegla = "Limita a Dos las Entidades Base";
	}

	@Override
	public void aplicarRegla(EntidadJuridica entidad) {
		entidad.setCantidadLimiteDeEntidadBase(2);
	}

}
	
	