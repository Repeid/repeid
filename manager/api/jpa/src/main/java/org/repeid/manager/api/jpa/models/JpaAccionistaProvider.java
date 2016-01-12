package org.repeid.manager.api.jpa.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Alternative;
import javax.persistence.TypedQuery;

import org.repeid.manager.api.jpa.entities.AccionistaEntity;
import org.repeid.manager.api.jpa.entities.PersonaJuridicaEntity;
import org.repeid.manager.api.jpa.entities.PersonaNaturalEntity;
import org.repeid.manager.api.model.AccionistaModel;
import org.repeid.manager.api.model.AccionistaProvider;
import org.repeid.manager.api.model.PersonaJuridicaModel;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;

import io.apiman.manager.api.jpa.AbstractStorage;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

@Alternative
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaAccionistaProvider extends AbstractStorage implements AccionistaProvider {

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public AccionistaModel create(PersonaJuridicaModel personaJuridica, PersonaNaturalModel personaNatural,
			BigDecimal porcentaje) {
		if (findByPersonaJuridicaNatural(personaJuridica, personaNatural) != null) {
			throw new ModelDuplicateException(
					"AccionistaEntity personaNatural y personaJuridica debe ser unico, se encontro otra entidad con personaJuridica="
							+ personaJuridica + "y personaNatural=" + personaNatural);
		}

		PersonaJuridicaEntity personaJuridicaEntity = getEntityManager().find(PersonaJuridicaEntity.class,
				personaJuridica.getId());
		PersonaNaturalEntity personaNaturalEntity = getEntityManager().find(PersonaNaturalEntity.class,
				personaNatural.getId());

		AccionistaEntity accionistaEntity = new AccionistaEntity();
		accionistaEntity.setPersonaNatural(personaNaturalEntity);
		accionistaEntity.setPersonaJuridica(personaJuridicaEntity);
		accionistaEntity.setPorcentajeParticipacion(porcentaje);
		getEntityManager().persist(accionistaEntity);
		return new AccionistaAdapter(getEntityManager(), accionistaEntity);
	}

	@Override
	public AccionistaModel findById(String id) {
		AccionistaEntity accionistaEntity = getEntityManager().find(AccionistaEntity.class, id);
		return accionistaEntity != null ? new AccionistaAdapter(getEntityManager(), accionistaEntity) : null;
	}

	@Override
	public AccionistaModel findByPersonaJuridicaNatural(PersonaJuridicaModel personaJuridica,
			PersonaNaturalModel personaNatural) {
		TypedQuery<AccionistaEntity> query = getEntityManager()
				.createNamedQuery("AccionistaEntity.findByIdPersonaJuridicaNatural", AccionistaEntity.class);
		query.setParameter("idPersonaJuridica", personaJuridica.getId());
		query.setParameter("idPersonaNatural", personaNatural.getId());
		List<AccionistaEntity> results = query.getResultList();
		if (results.isEmpty()) {
			return null;
		} else if (results.size() > 1) {
			throw new IllegalStateException("Mas de un AccionistaEntity con personaNatural=" + personaNatural.getId()
					+ " y personaJuridica=" + personaJuridica.getId() + ", results=" + results);
		} else {
			return new AccionistaAdapter(getEntityManager(), results.get(0));
		}
	}

	@Override
	public boolean remove(AccionistaModel accionistaModel) {
		AccionistaEntity accionistaEntity = getEntityManager().find(AccionistaEntity.class, accionistaModel.getId());
		if (accionistaEntity == null)
			return false;
		getEntityManager().remove(accionistaEntity);
		return true;
	}

	@Override
	public List<AccionistaModel> getAll(PersonaJuridicaModel personaJuridicaModel) {
		PersonaJuridicaEntity personaJuridicaEntity = getEntityManager().find(PersonaJuridicaEntity.class,
				personaJuridicaModel.getId());

		Set<AccionistaEntity> entities = personaJuridicaEntity.getAccionistas();
		List<AccionistaModel> models = new ArrayList<AccionistaModel>();
		for (AccionistaEntity accionistaEntity : entities) {
			models.add(new AccionistaAdapter(getEntityManager(), accionistaEntity));
		}

		return models;
	}

}
