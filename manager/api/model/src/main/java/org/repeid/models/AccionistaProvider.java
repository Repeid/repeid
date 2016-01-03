package org.repeid.models;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import org.repeid.provider.Provider;

@Local
public interface AccionistaProvider extends Provider {

    AccionistaModel findById(String id);

    AccionistaModel findByPersonaJuridicaNatural(PersonaJuridicaModel personaJuridica,
            PersonaNaturalModel personaNatural);

    AccionistaModel create(PersonaJuridicaModel personaJuridica, PersonaNaturalModel personaNatural,
            BigDecimal porcentaje);

    boolean remove(AccionistaModel accionista);

    List<AccionistaModel> getAll(PersonaJuridicaModel personaJuridica);

}
