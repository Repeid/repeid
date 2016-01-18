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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.PersonaNaturalProvider;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.enums.Sexo;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;
import org.repeid.manager.api.model.provider.ProviderFactory;
import org.repeid.manager.api.model.provider.ProviderType;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;
import org.repeid.manager.api.mongo.AbstractMongoStorage;
import org.repeid.manager.api.mongo.entities.MongoAccionistaEntity;
import org.repeid.manager.api.mongo.entities.MongoPersonaJuridicaEntity;
import org.repeid.manager.api.mongo.entities.MongoPersonaNaturalEntity;
import org.repeid.manager.api.mongo.entities.MongoTipoDocumentoEntity;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@ProviderFactory(ProviderType.MONGO)
public class MongoPersonaNaturalProvider extends AbstractMongoStorage implements PersonaNaturalProvider {

	private static final String APELLIDO_PATERNO = "apellidoPaterno";
	private static final String APELLIDO_MATERNO = "apellidoMaterno";
	private static final String NOMBRES = "nombres";
	private static final String NUMERO_DOCUMENTO = "numeroDocumento";

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public PersonaNaturalModel create(String codigoPais, TipoDocumentoModel tipoDocumentoModel, String numeroDocumento,
			String apellidoPaterno, String apellidoMaterno, String nombres, Date fechaNacimiento, Sexo sexo) {
		if (findByTipoNumeroDocumento(tipoDocumentoModel, numeroDocumento) != null) {
			throw new ModelDuplicateException(
					"PersonaNaturalEntity tipoDocumento y numeroDocumento debe ser unico, se encontro otra entidad con tipoDocumento="
							+ tipoDocumentoModel + "y numeroDocumento=" + numeroDocumento);
		}

		MongoTipoDocumentoEntity tipoDocumentoEntity = getEntityManager().find(MongoTipoDocumentoEntity.class,
				tipoDocumentoModel.getId());

		MongoPersonaNaturalEntity personaNaturalEntity = new MongoPersonaNaturalEntity();
		personaNaturalEntity.setCodigoPais(codigoPais);
		personaNaturalEntity.setTipoDocumento(tipoDocumentoEntity);
		personaNaturalEntity.setNumeroDocumento(numeroDocumento);
		personaNaturalEntity.setApellidoPaterno(apellidoPaterno);
		personaNaturalEntity.setApellidoMaterno(apellidoMaterno);
		personaNaturalEntity.setNombres(nombres);
		personaNaturalEntity.setFechaNacimiento(fechaNacimiento);
		personaNaturalEntity.setSexo(sexo.toString());
		getEntityManager().persist(personaNaturalEntity);
		return new PersonaNaturalAdapter(getEntityManager(), personaNaturalEntity);
	}

	@Override
	public boolean remove(PersonaNaturalModel personaNaturalModel) {
		TypedQuery<MongoAccionistaEntity> query1 = getEntityManager()
				.createNamedQuery("MongoAccionistaEntity.findByIdPersonaNatural", MongoAccionistaEntity.class);
		query1.setParameter("idPersonaNatural", personaNaturalModel.getId());
		query1.setMaxResults(1);
		if (!query1.getResultList().isEmpty()) {
			return false;
		}

		TypedQuery<MongoPersonaJuridicaEntity> query2 = getEntityManager().createNamedQuery(
				"MongoPersonaJuridicaEntity.findByIdPersonaNaturalRepresentanteLegal", MongoPersonaJuridicaEntity.class);
		query2.setParameter("idPersonaNaturalRepresentanteLegal", personaNaturalModel.getId());
		query2.setMaxResults(1);
		if (!query2.getResultList().isEmpty()) {
			return false;
		}

		MongoPersonaNaturalEntity personaNaturalEntity = getEntityManager().find(MongoPersonaNaturalEntity.class,
				personaNaturalModel.getId());
		if (personaNaturalEntity == null) {
			return false;
		}
		getEntityManager().remove(personaNaturalEntity);
		return true;
	}

	@Override
	public PersonaNaturalModel findById(String id) {
		MongoPersonaNaturalEntity personaNaturalEntity = this.getEntityManager().find(MongoPersonaNaturalEntity.class, id);
		return personaNaturalEntity != null ? new PersonaNaturalAdapter(getEntityManager(), personaNaturalEntity)
				: null;
	}

	@Override
	public PersonaNaturalModel findByTipoNumeroDocumento(TipoDocumentoModel tipoDocumento, String numeroDocumento) {
		TypedQuery<MongoPersonaNaturalEntity> query = getEntityManager()
				.createNamedQuery("MongoPersonaNaturalEntity.findByTipoNumeroDocumento", MongoPersonaNaturalEntity.class);
		query.setParameter("tipoDocumento", tipoDocumento.getAbreviatura());
		query.setParameter("numeroDocumento", numeroDocumento);
		List<MongoPersonaNaturalEntity> results = query.getResultList();
		if (results.isEmpty()) {
			return null;
		} else if (results.size() > 1) {
			throw new IllegalStateException("Mas de una PersonaNaturalEntity con tipoDocumento=" + tipoDocumento
					+ " y numeroDocumento=" + numeroDocumento + ", results=" + results);
		} else {
			return new PersonaNaturalAdapter(getEntityManager(), results.get(0));
		}
	}

	@Override
	public List<PersonaNaturalModel> getAll() {
		return getAll(-1, -1);
	}

	@Override
	public List<PersonaNaturalModel> getAll(int firstResult, int maxResults) {
		TypedQuery<MongoPersonaNaturalEntity> query = getEntityManager().createNamedQuery("MongoPersonaNaturalEntity.findAll",
				MongoPersonaNaturalEntity.class);
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<MongoPersonaNaturalEntity> entities = query.getResultList();
		List<PersonaNaturalModel> result = new ArrayList<PersonaNaturalModel>();
		for (MongoPersonaNaturalEntity personaNaturalEntity : entities) {
			result.add(new PersonaNaturalAdapter(getEntityManager(), personaNaturalEntity));
		}
		return result;
	}

	@Override
	public List<PersonaNaturalModel> search(String filterText) {
		return search(filterText, -1, -1);
	}

	@Override
	public List<PersonaNaturalModel> search(String filterText, int firstResult, int maxResults) {
		TypedQuery<MongoPersonaNaturalEntity> query = getEntityManager()
				.createNamedQuery("MongoPersonaNaturalEntity.findByFilterText", MongoPersonaNaturalEntity.class);
		query.setParameter("filterText", "%" + filterText.toLowerCase() + "%");
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<MongoPersonaNaturalEntity> entities = query.getResultList();
		List<PersonaNaturalModel> models = new ArrayList<PersonaNaturalModel>();
		for (MongoPersonaNaturalEntity personaNaturalEntity : entities) {
			models.add(new PersonaNaturalAdapter(getEntityManager(), personaNaturalEntity));
		}
		return models;
	}

	@Override
	public List<PersonaNaturalModel> searchByAttributes(Map<String, String> attributes) {
		return searchByAttributes(attributes, -1, -1);
	}

	@Override
	public List<PersonaNaturalModel> searchByAttributes(Map<String, String> attributes, int firstResult,
			int maxResults) {
		StringBuilder builder = new StringBuilder("SELECT p FROM MongoPersonaNaturalEntity");
		for (Map.Entry<String, String> entry : attributes.entrySet()) {
			String attribute = null;
			String parameterName = null;
			if (entry.getKey().equals(PersonaNaturalModel.APELLIDO_PATERNO)) {
				attribute = "lower(p.apellidoPaterno)";
				parameterName = MongoPersonaNaturalProvider.APELLIDO_PATERNO;
			} else if (entry.getKey().equalsIgnoreCase(PersonaNaturalModel.APELLIDO_MATERNO)) {
				attribute = "lower(p.apellidoMaterno)";
				parameterName = MongoPersonaNaturalProvider.APELLIDO_MATERNO;
			} else if (entry.getKey().equalsIgnoreCase(PersonaNaturalModel.NOMBRES)) {
				attribute = "lower(p.nombres)";
				parameterName = MongoPersonaNaturalProvider.NOMBRES;
			} else if (entry.getKey().equalsIgnoreCase(PersonaNaturalModel.NUMERO_DOCUMENTO)) {
				attribute = "lower(p.numeroDocumento)";
				parameterName = MongoPersonaNaturalProvider.NUMERO_DOCUMENTO;
			}

			if (attribute == null) {
				continue;
			}
			builder.append(attribute).append(" like :").append(parameterName);
		}
		builder.append(" order by p.apellidoPaterno, p.apellidoMaterno, p.nombres");
		String q = builder.toString();
		TypedQuery<MongoPersonaNaturalEntity> query = getEntityManager().createQuery(q, MongoPersonaNaturalEntity.class);
		for (Map.Entry<String, String> entry : attributes.entrySet()) {
			String parameterName = null;
			if (entry.getKey().equals(PersonaNaturalModel.APELLIDO_PATERNO)) {
				parameterName = MongoPersonaNaturalProvider.APELLIDO_PATERNO;
			} else if (entry.getKey().equals(PersonaNaturalModel.APELLIDO_MATERNO)) {
				parameterName = MongoPersonaNaturalProvider.APELLIDO_MATERNO;
			} else if (entry.getKey().equals(PersonaNaturalModel.NOMBRES)) {
				parameterName = MongoPersonaNaturalProvider.NOMBRES;
			} else if (entry.getKey().equals(PersonaNaturalModel.NUMERO_DOCUMENTO)) {
				parameterName = MongoPersonaNaturalProvider.NUMERO_DOCUMENTO;
			}

			if (parameterName == null) {
				continue;
			}
			query.setParameter(parameterName, "%" + entry.getValue().toLowerCase() + "%");
		}
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<MongoPersonaNaturalEntity> results = query.getResultList();
		List<PersonaNaturalModel> personaNaturales = new ArrayList<PersonaNaturalModel>();
		for (MongoPersonaNaturalEntity entity : results)
			personaNaturales.add(new PersonaNaturalAdapter(getEntityManager(), entity));
		return personaNaturales;
	}

	@Override
	public SearchResultsModel<PersonaNaturalModel> search(SearchCriteriaModel criteria) {
		SearchResultsModel<MongoPersonaNaturalEntity> entityResult = find(criteria, MongoPersonaNaturalEntity.class);

		SearchResultsModel<PersonaNaturalModel> modelResult = new SearchResultsModel<>();
		List<PersonaNaturalModel> list = new ArrayList<>();
		for (MongoPersonaNaturalEntity entity : entityResult.getModels()) {
			list.add(new PersonaNaturalAdapter(getEntityManager(), entity));
		}
		modelResult.setTotalSize(entityResult.getTotalSize());
		modelResult.setModels(list);
		return modelResult;
	}

	@Override
	public SearchResultsModel<PersonaNaturalModel> search(SearchCriteriaModel criteria, String filterText) {
		SearchResultsModel<MongoPersonaNaturalEntity> entityResult = findFullText(criteria, MongoPersonaNaturalEntity.class,
				filterText, "numeroDocumento", "apellidoPaterno", "apellidoMaterno", "nombres");

		SearchResultsModel<PersonaNaturalModel> modelResult = new SearchResultsModel<>();
		List<PersonaNaturalModel> list = new ArrayList<>();
		for (MongoPersonaNaturalEntity entity : entityResult.getModels()) {
			list.add(new PersonaNaturalAdapter(getEntityManager(), entity));
		}
		modelResult.setTotalSize(entityResult.getTotalSize());
		modelResult.setModels(list);
		return modelResult;
	}

}
