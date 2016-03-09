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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;

import org.repeid.manager.api.jpa.AbstractJpaStorage;
import org.repeid.manager.api.jpa.entities.PersonaJuridicaEntity;
import org.repeid.manager.api.jpa.entities.PersonaNaturalEntity;
import org.repeid.manager.api.jpa.entities.TipoDocumentoEntity;
import org.repeid.manager.api.model.PersonaJuridicaModel;
import org.repeid.manager.api.model.PersonaJuridicaProvider;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.enums.TipoEmpresa;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;
import org.repeid.manager.api.model.provider.ProviderType;
import org.repeid.manager.api.model.provider.ProviderType.Type;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

@Stateless
@ProviderType(Type.JPA)
@TransactionManagement(TransactionManagementType.BEAN)
public class JpaPersonaJuridicaProvider extends AbstractJpaStorage implements PersonaJuridicaProvider {

	private final static String RAZON_SOCIAL = "razonSocial";
	private final static String NOMBRE_COMERCIAL = "nombreComercial";
	private final static String NUMERO_DOCUMENTO = "numeroDocumento";

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public PersonaJuridicaModel create(PersonaNaturalModel representanteLegal, String codigoPais,
			TipoDocumentoModel tipoDocumentoModel, String numeroDocumento, String razonSocial, Date fechaConstitucion,
			TipoEmpresa tipoEmpresa, boolean finLucro) {
		if (findByTipoNumeroDocumento(tipoDocumentoModel, numeroDocumento) != null) {
			throw new ModelDuplicateException(
					"PersonaJuridicaEntity tipoDocumento y numeroDocumento debe ser unico, se encontro otra entidad con tipoDocumento="
							+ tipoDocumentoModel + "y numeroDocumento=" + numeroDocumento);
		}

		TipoDocumentoEntity tipoDocumentoEntity = getEntityManager().find(TipoDocumentoEntity.class,
				tipoDocumentoModel.getId());
		PersonaNaturalEntity personaNaturalEntity = getEntityManager().find(PersonaNaturalEntity.class,
				representanteLegal.getId());

		PersonaJuridicaEntity personaJuridicaEntity = new PersonaJuridicaEntity();
		personaJuridicaEntity.setRepresentanteLegal(personaNaturalEntity);
		personaJuridicaEntity.setCodigoPais(codigoPais);
		personaJuridicaEntity.setTipoDocumento(tipoDocumentoEntity);
		personaJuridicaEntity.setNumeroDocumento(numeroDocumento);
		personaJuridicaEntity.setRazonSocial(razonSocial);
		personaJuridicaEntity.setFechaConstitucion(fechaConstitucion);
		personaJuridicaEntity.setTipoEmpresa(tipoEmpresa.toString());
		personaJuridicaEntity.setFinLucro(finLucro);

		getEntityManager().persist(personaJuridicaEntity);
		getEntityManager().flush();
		return new PersonaJuridicaAdapter(getEntityManager(), personaJuridicaEntity);
	}

	@Override
	public boolean remove(PersonaJuridicaModel personaJuridicaModel) {
		PersonaJuridicaEntity personaJuridicaEntity = getEntityManager().find(PersonaJuridicaEntity.class,
				personaJuridicaModel.getId());
		if (personaJuridicaEntity == null) {
			return false;
		}
		getEntityManager().remove(personaJuridicaEntity);
		getEntityManager().flush();
		return true;
	}

	@Override
	public PersonaJuridicaModel findById(String id) {
		PersonaJuridicaEntity personaJuridicaEntity = getEntityManager().find(PersonaJuridicaEntity.class, id);
		return personaJuridicaEntity != null ? new PersonaJuridicaAdapter(getEntityManager(), personaJuridicaEntity)
				: null;
	}

	@Override
	public PersonaJuridicaModel findByTipoNumeroDocumento(TipoDocumentoModel tipoDocumento, String numeroDocumento) {
		TypedQuery<PersonaJuridicaEntity> query = getEntityManager()
				.createNamedQuery("PersonaJuridicaEntity.findByTipoNumeroDocumento", PersonaJuridicaEntity.class);
		query.setParameter("tipoDocumento", tipoDocumento.getAbreviatura());
		query.setParameter("numeroDocumento", numeroDocumento);
		List<PersonaJuridicaEntity> results = query.getResultList();
		if (results.isEmpty()) {
			return null;
		} else if (results.size() > 1) {
			throw new IllegalStateException("Mas de una PersonaJuridicaEntity con tipoDocumento=" + tipoDocumento
					+ " y numeroDocumento=" + numeroDocumento + ", results=" + results);
		} else {
			return new PersonaJuridicaAdapter(getEntityManager(), results.get(0));
		}
	}

	@Override
	public List<PersonaJuridicaModel> getAll() {
		return getAll(-1, -1);
	}

	@Override
	public List<PersonaJuridicaModel> getAll(int firstResult, int maxResults) {
		TypedQuery<PersonaJuridicaEntity> query = getEntityManager().createNamedQuery("PersonaJuridicaEntity.findAll",
				PersonaJuridicaEntity.class);
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<PersonaJuridicaEntity> entities = query.getResultList();
		List<PersonaJuridicaModel> models = new ArrayList<PersonaJuridicaModel>();
		for (PersonaJuridicaEntity personaJuridicaEntity : entities) {
			models.add(new PersonaJuridicaAdapter(getEntityManager(), personaJuridicaEntity));
		}
		return models;
	}

	@Override
	public List<PersonaJuridicaModel> search(String filterText) {
		return search(filterText, -1, -1);
	}

	@Override
	public List<PersonaJuridicaModel> search(String filterText, int firstResult, int maxResults) {
		TypedQuery<PersonaJuridicaEntity> query = getEntityManager()
				.createNamedQuery("PersonaJuridicaEntity.findByFilterText", PersonaJuridicaEntity.class);
		query.setParameter("filterText", "%" + filterText.toLowerCase() + "%");
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<PersonaJuridicaEntity> entities = query.getResultList();
		List<PersonaJuridicaModel> models = new ArrayList<PersonaJuridicaModel>();
		for (PersonaJuridicaEntity personaJuridicaEntity : entities) {
			models.add(new PersonaJuridicaAdapter(getEntityManager(), personaJuridicaEntity));
		}
		return models;
	}

	@Override
	public List<PersonaJuridicaModel> searchByAttributes(Map<String, String> attributes) {
		return searchByAttributes(attributes, -1, -1);
	}

	@Override
	public List<PersonaJuridicaModel> searchByAttributes(Map<String, String> attributes, int firstResult,
			int maxResults) {
		StringBuilder builder = new StringBuilder("SELECT p FROM PersonaJuridicaEntity");
		for (Map.Entry<String, String> entry : attributes.entrySet()) {
			String attribute = null;
			String parameterName = null;
			if (entry.getKey().equals(PersonaJuridicaModel.RAZON_SOCIAL)) {
				attribute = "lower(p.razonSocial)";
				parameterName = JpaPersonaJuridicaProvider.RAZON_SOCIAL;
			} else if (entry.getKey().equalsIgnoreCase(PersonaJuridicaModel.NOMBRE_COMERCIAL)) {
				attribute = "lower(p.nombreComercial)";
				parameterName = JpaPersonaJuridicaProvider.NOMBRE_COMERCIAL;
			} else if (entry.getKey().equalsIgnoreCase(PersonaJuridicaModel.NUMERO_DOCUMENTO)) {
				attribute = "lower(p.numeroDocumento)";
				parameterName = JpaPersonaJuridicaProvider.NUMERO_DOCUMENTO;
			}

			if (attribute == null) {
				continue;
			}
			builder.append(attribute).append(" like :").append(parameterName);
		}
		builder.append(" order by p.razonSocial");
		String q = builder.toString();
		TypedQuery<PersonaJuridicaEntity> query = getEntityManager().createQuery(q, PersonaJuridicaEntity.class);
		for (Map.Entry<String, String> entry : attributes.entrySet()) {
			String parameterName = null;
			if (entry.getKey().equals(PersonaJuridicaModel.RAZON_SOCIAL)) {
				parameterName = JpaPersonaJuridicaProvider.RAZON_SOCIAL;
			} else if (entry.getKey().equals(PersonaJuridicaModel.NOMBRE_COMERCIAL)) {
				parameterName = JpaPersonaJuridicaProvider.NOMBRE_COMERCIAL;
			} else if (entry.getKey().equals(PersonaJuridicaModel.NUMERO_DOCUMENTO)) {
				parameterName = JpaPersonaJuridicaProvider.NUMERO_DOCUMENTO;
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
		List<PersonaJuridicaEntity> results = query.getResultList();
		List<PersonaJuridicaModel> personaNaturales = new ArrayList<PersonaJuridicaModel>();
		for (PersonaJuridicaEntity entity : results)
			personaNaturales.add(new PersonaJuridicaAdapter(getEntityManager(), entity));
		return personaNaturales;
	}

	@Override
	public SearchResultsModel<PersonaJuridicaModel> search(SearchCriteriaModel criteria) {
		SearchResultsModel<PersonaJuridicaEntity> entityResult = find(criteria, PersonaJuridicaEntity.class);

		SearchResultsModel<PersonaJuridicaModel> modelResult = new SearchResultsModel<>();
		List<PersonaJuridicaModel> list = new ArrayList<>();
		for (PersonaJuridicaEntity entity : entityResult.getModels()) {
			list.add(new PersonaJuridicaAdapter(getEntityManager(), entity));
		}
		modelResult.setTotalSize(entityResult.getTotalSize());
		modelResult.setModels(list);
		return modelResult;
	}

	@Override
	public SearchResultsModel<PersonaJuridicaModel> search(SearchCriteriaModel criteria, String filterText) {
		SearchResultsModel<PersonaJuridicaEntity> entityResult = findFullText(criteria, PersonaJuridicaEntity.class,
				filterText, "numeroDocumento", "razonSocial");

		SearchResultsModel<PersonaJuridicaModel> modelResult = new SearchResultsModel<>();
		List<PersonaJuridicaModel> list = new ArrayList<>();
		for (PersonaJuridicaEntity entity : entityResult.getModels()) {
			list.add(new PersonaJuridicaAdapter(getEntityManager(), entity));
		}
		modelResult.setTotalSize(entityResult.getTotalSize());
		modelResult.setModels(list);
		return modelResult;
	}

}
