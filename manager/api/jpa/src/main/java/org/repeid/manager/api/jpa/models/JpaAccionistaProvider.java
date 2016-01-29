/*******************************************************************************
 * Repeid, Home of Professional Open Source
 *
 * Copyright 2015 Sistcoop, Inc. and/or its affiliates.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.repeid.manager.api.jpa.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.repeid.manager.api.jpa.AbstractJpaStorage;
import org.repeid.manager.api.jpa.entities.AccionistaEntity;
import org.repeid.manager.api.jpa.entities.PersonaJuridicaEntity;
import org.repeid.manager.api.jpa.entities.PersonaNaturalEntity;
import org.repeid.manager.api.model.AccionistaModel;
import org.repeid.manager.api.model.AccionistaProvider;
import org.repeid.manager.api.model.KeycloakSession;
import org.repeid.manager.api.model.PersonaJuridicaModel;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

public class JpaAccionistaProvider extends AbstractJpaStorage implements AccionistaProvider {

	private final KeycloakSession session;
	private EntityManager em;

	public JpaAccionistaProvider(KeycloakSession session, EntityManager em) {
		super(em);
		this.session = session;
		this.em = em;
	}

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

		PersonaJuridicaEntity personaJuridicaEntity = em.find(PersonaJuridicaEntity.class, personaJuridica.getId());
		PersonaNaturalEntity personaNaturalEntity = em.find(PersonaNaturalEntity.class, personaNatural.getId());

		AccionistaEntity accionistaEntity = new AccionistaEntity();
		accionistaEntity.setPersonaNatural(personaNaturalEntity);
		accionistaEntity.setPersonaJuridica(personaJuridicaEntity);
		accionistaEntity.setPorcentajeParticipacion(porcentaje);
		em.persist(accionistaEntity);
		em.flush();
		return new AccionistaAdapter(session, em, accionistaEntity);
	}

	@Override
	public AccionistaModel findById(String id) {
		AccionistaEntity accionistaEntity = em.find(AccionistaEntity.class, id);
		return accionistaEntity != null ? new AccionistaAdapter(session, em, accionistaEntity) : null;
	}

	@Override
	public AccionistaModel findByPersonaJuridicaNatural(PersonaJuridicaModel personaJuridica,
			PersonaNaturalModel personaNatural) {
		TypedQuery<AccionistaEntity> query = em.createNamedQuery("AccionistaEntity.findByIdPersonaJuridicaNatural",
				AccionistaEntity.class);
		query.setParameter("idPersonaJuridica", personaJuridica.getId());
		query.setParameter("idPersonaNatural", personaNatural.getId());
		List<AccionistaEntity> results = query.getResultList();
		if (results.isEmpty()) {
			return null;
		} else if (results.size() > 1) {
			throw new IllegalStateException("Mas de un AccionistaEntity con personaNatural=" + personaNatural.getId()
					+ " y personaJuridica=" + personaJuridica.getId() + ", results=" + results);
		} else {
			AccionistaEntity entity = results.get(0);
			return new AccionistaAdapter(session, em, entity);
		}
	}

	@Override
	public boolean remove(AccionistaModel accionistaModel) {
		AccionistaEntity accionistaEntity = em.find(AccionistaEntity.class, accionistaModel.getId());
		if (accionistaEntity == null)
			return false;
		em.remove(accionistaEntity);
		em.flush();
		return true;
	}

	@Override
	public List<AccionistaModel> getAll(PersonaJuridicaModel personaJuridicaModel) {
		PersonaJuridicaEntity personaJuridicaEntity = em.find(PersonaJuridicaEntity.class,
				personaJuridicaModel.getId());

		Set<AccionistaEntity> entities = personaJuridicaEntity.getAccionistas();
		List<AccionistaModel> models = new ArrayList<AccionistaModel>();
		for (AccionistaEntity accionistaEntity : entities) {
			models.add(new AccionistaAdapter(session, em, accionistaEntity));
		}

		return models;
	}

}
