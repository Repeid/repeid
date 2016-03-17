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
package org.repeid.manager.api.models.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.repeid.manager.api.model.PersonaJuridicaModel;
import org.repeid.manager.api.model.PersonaJuridicaProvider;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.enums.TipoEmpresa;
import org.repeid.manager.api.model.provider.KeycloakSession;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;
import org.repeid.manager.api.models.jpa.entities.PersonaJuridicaEntity;
import org.repeid.manager.api.models.jpa.entities.PersonaNaturalEntity;
import org.repeid.manager.api.models.jpa.entities.TipoDocumentoEntity;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

public class JpaPersonaJuridicaProvider extends AbstractJpaStorage implements PersonaJuridicaProvider {

	private final static String RAZON_SOCIAL = "razonSocial";
	private final static String NOMBRE_COMERCIAL = "nombreComercial";
	private final static String NUMERO_DOCUMENTO = "numeroDocumento";

	private final KeycloakSession session;
	protected EntityManager em;

	public JpaPersonaJuridicaProvider(KeycloakSession session, EntityManager em) {
		this.session = session;
		this.em = em;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public PersonaJuridicaModel create(PersonaNaturalModel representanteLegal, String codigoPais,
			TipoDocumentoModel tipoDocumentoModel, String numeroDocumento, String razonSocial, Date fechaConstitucion,
			TipoEmpresa tipoEmpresa, boolean finLucro) {

		TipoDocumentoEntity tipoDocumentoEntity = em.find(TipoDocumentoEntity.class, tipoDocumentoModel.getId());
		PersonaNaturalEntity personaNaturalEntity = em.find(PersonaNaturalEntity.class, representanteLegal.getId());

		PersonaJuridicaEntity entity = new PersonaJuridicaEntity();
		entity.setRepresentanteLegal(personaNaturalEntity);
		entity.setCodigoPais(codigoPais);
		entity.setTipoDocumento(tipoDocumentoEntity);
		entity.setNumeroDocumento(numeroDocumento);
		entity.setRazonSocial(razonSocial);
		entity.setFechaConstitucion(fechaConstitucion);
		entity.setTipoEmpresa(tipoEmpresa.toString());
		entity.setFinLucro(finLucro);

		em.persist(entity);
		em.flush();
		return new PersonaJuridicaAdapter(session, em, entity);
	}

	@Override
	public boolean remove(PersonaJuridicaModel personaJuridicaModel) {
		PersonaJuridicaEntity entity = em.find(PersonaJuridicaEntity.class, personaJuridicaModel.getId());
		if (entity == null) {
			return false;
		}
		em.remove(entity);
		em.flush();
		return true;
	}

	@Override
	public PersonaJuridicaModel findById(String id) {
		PersonaJuridicaEntity entity = em.find(PersonaJuridicaEntity.class, id);
		return entity != null ? new PersonaJuridicaAdapter(session, em, entity) : null;
	}

	@Override
	public PersonaJuridicaModel findByTipoNumeroDocumento(TipoDocumentoModel tipoDocumento, String numeroDocumento) {
		TypedQuery<PersonaJuridicaEntity> query = em.createNamedQuery("PersonaJuridicaEntity.findByTipoNumeroDocumento",
				PersonaJuridicaEntity.class);
		query.setParameter("tipoDocumento", tipoDocumento.getAbreviatura());
		query.setParameter("numeroDocumento", numeroDocumento);
		List<PersonaJuridicaEntity> results = query.getResultList();
		if (results.isEmpty()) {
			return null;
		} else if (results.size() > 1) {
			throw new IllegalStateException("Mas de una PersonaJuridicaEntity con tipoDocumento=" + tipoDocumento
					+ " y numeroDocumento=" + numeroDocumento + ", resultados=" + results);
		} else {
			return new PersonaJuridicaAdapter(session, em, results.get(0));
		}
	}

	@Override
	public List<PersonaJuridicaModel> getAll() {
		return getAll(-1, -1);
	}

	@Override
	public List<PersonaJuridicaModel> getAll(int firstResult, int maxResults) {
		TypedQuery<PersonaJuridicaEntity> query = em.createNamedQuery("PersonaJuridicaEntity.findAll",
				PersonaJuridicaEntity.class);
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<PersonaJuridicaEntity> entities = query.getResultList();
		List<PersonaJuridicaModel> result = new ArrayList<>();
		entities.forEach(entity -> result.add(new PersonaJuridicaAdapter(session, em, entity)));
		return result;
	}

	@Override
	public List<PersonaJuridicaModel> search(String filterText) {
		return search(filterText, -1, -1);
	}

	@Override
	public List<PersonaJuridicaModel> search(String filterText, int firstResult, int maxResults) {
		TypedQuery<PersonaJuridicaEntity> query = em.createNamedQuery("PersonaJuridicaEntity.findByFilterText",
				PersonaJuridicaEntity.class);
		query.setParameter("filterText", "%" + filterText.toLowerCase() + "%");
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<PersonaJuridicaEntity> entities = query.getResultList();
		List<PersonaJuridicaModel> result = new ArrayList<>();
		entities.forEach(entity -> result.add(new PersonaJuridicaAdapter(session, em, entity)));
		return result;
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
		TypedQuery<PersonaJuridicaEntity> query = em.createQuery(q, PersonaJuridicaEntity.class);
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

		List<PersonaJuridicaEntity> entities = query.getResultList();
		List<PersonaJuridicaModel> result = new ArrayList<>();
		entities.forEach(entity -> result.add(new PersonaJuridicaAdapter(session, em, entity)));
		return result;
	}

	@Override
	public SearchResultsModel<PersonaJuridicaModel> search(SearchCriteriaModel criteria) {
		SearchResultsModel<PersonaJuridicaEntity> entityResult = find(criteria, PersonaJuridicaEntity.class);

		SearchResultsModel<PersonaJuridicaModel> modelResult = new SearchResultsModel<>();
		List<PersonaJuridicaModel> list = new ArrayList<>();
		entityResult.getModels().forEach(entity -> list.add(new PersonaJuridicaAdapter(session, em, entity)));
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
		entityResult.getModels().forEach(entity -> list.add(new PersonaJuridicaAdapter(session, em, entity)));
		modelResult.setTotalSize(entityResult.getTotalSize());
		modelResult.setModels(list);
		return modelResult;
	}

}
