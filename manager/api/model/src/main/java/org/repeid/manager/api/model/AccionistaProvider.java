package org.repeid.manager.api.model;

import java.math.BigDecimal;
import java.util.List;

import org.repeid.manager.api.model.provider.Provider;

public interface AccionistaProvider extends Provider {

	AccionistaModel create(PersonaJuridicaModel personaJuridica, PersonaNaturalModel personaNatural,
			BigDecimal porcentaje);

	AccionistaModel findById(String id);

	AccionistaModel findByPersonaJuridicaNatural(PersonaJuridicaModel personaJuridica,
			PersonaNaturalModel personaNatural);

	boolean remove(AccionistaModel accionista);

	List<AccionistaModel> getAll(PersonaJuridicaModel personaJuridica);

}
