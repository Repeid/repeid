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
package org.repeid.manager.api.mongo.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import org.repeid.manager.api.model.AccionistaModel;
import org.repeid.manager.api.model.AccionistaProvider;
import org.repeid.manager.api.model.PersonaJuridicaModel;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;
import org.repeid.manager.api.model.provider.ProviderFactory;
import org.repeid.manager.api.model.provider.ProviderType;
import org.repeid.manager.api.mongo.AbstractMongoStorage;
import org.repeid.manager.api.mongo.entities.MongoAccionistaEntity;
import org.repeid.manager.api.mongo.entities.MongoPersonaJuridicaEntity;
import org.repeid.manager.api.mongo.entities.MongoPersonaNaturalEntity;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@ProviderFactory(ProviderType.MONGO)
public class MongoAccionistaProvider extends AbstractMongoStorage implements AccionistaProvider {

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

		MongoPersonaJuridicaEntity personaJuridicaEntity = getEntityManager().find(MongoPersonaJuridicaEntity.class,
				personaJuridica.getId());
		MongoPersonaNaturalEntity personaNaturalEntity = getEntityManager().find(MongoPersonaNaturalEntity.class,
				personaNatural.getId());

		MongoAccionistaEntity accionistaEntity = new MongoAccionistaEntity();
		accionistaEntity.setPersonaNatural(personaNaturalEntity);
		accionistaEntity.setPersonaJuridica(personaJuridicaEntity);
		accionistaEntity.setPorcentajeParticipacion(porcentaje);
		getEntityManager().persist(accionistaEntity);
		return new AccionistaAdapter(getEntityManager(), accionistaEntity);
	}

	@Override
	public AccionistaModel findById(String id) {
		MongoAccionistaEntity accionistaEntity = getEntityManager().find(MongoAccionistaEntity.class, id);
		return accionistaEntity != null ? new AccionistaAdapter(getEntityManager(), accionistaEntity) : null;
	}

	@Override
	public AccionistaModel findByPersonaJuridicaNatural(PersonaJuridicaModel personaJuridica,
			PersonaNaturalModel personaNatural) {
		TypedQuery<MongoAccionistaEntity> query = getEntityManager()
				.createNamedQuery("MongoAccionistaEntity.findByIdPersonaJuridicaNatural", MongoAccionistaEntity.class);
		query.setParameter("idPersonaJuridica", personaJuridica.getId());
		query.setParameter("idPersonaNatural", personaNatural.getId());
		List<MongoAccionistaEntity> results = query.getResultList();
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
		MongoAccionistaEntity accionistaEntity = getEntityManager().find(MongoAccionistaEntity.class, accionistaModel.getId());
		if (accionistaEntity == null)
			return false;
		getEntityManager().remove(accionistaEntity);
		return true;
	}

	@Override
	public List<AccionistaModel> getAll(PersonaJuridicaModel personaJuridicaModel) {
		MongoPersonaJuridicaEntity personaJuridicaEntity = getEntityManager().find(MongoPersonaJuridicaEntity.class,
				personaJuridicaModel.getId());

		Set<MongoAccionistaEntity> entities = personaJuridicaEntity.getAccionistas();
		List<AccionistaModel> models = new ArrayList<AccionistaModel>();
		for (MongoAccionistaEntity accionistaEntity : entities) {
			models.add(new AccionistaAdapter(getEntityManager(), accionistaEntity));
		}

		return models;
	}

}
