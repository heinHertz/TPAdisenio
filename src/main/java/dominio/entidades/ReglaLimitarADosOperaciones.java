package dominio.entidades;

import java.util.List;

import javax.persistence.*;

import dominio.operacion.Operacion;

@Entity
@DiscriminatorValue("2")
public class ReglaLimitarADosOperaciones extends Regla {

	public ReglaLimitarADosOperaciones() {
		etiquetaRegla = "Limita a Dos Las Operaciones";
	}

	@Override
	public void aplicarRegla(EntidadJuridica entidad) {
		entidad.setCantidadLimiteDeOperaciones(2);
	}


}	
	
	