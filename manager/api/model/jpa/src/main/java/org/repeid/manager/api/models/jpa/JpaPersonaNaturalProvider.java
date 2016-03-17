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

import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.PersonaNaturalProvider;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.enums.Sexo;
import org.repeid.manager.api.model.provider.KeycloakSession;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;
import org.repeid.manager.api.models.jpa.entities.PersonaJuridicaEntity;
import org.repeid.manager.api.models.jpa.entities.PersonaNaturalEntity;
import org.repeid.manager.api.models.jpa.entities.TipoDocumentoEntity;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

public class JpaPersonaNaturalProvider extends AbstractJpaStorage implements PersonaNaturalProvider {

	private static final String APELLIDO_PATERNO = "apellidoPaterno";
	private static final String APELLIDO_MATERNO = "apellidoMaterno";
	private static final String NOMBRES = "nombres";
	private static final String NUMERO_DOCUMENTO = "numeroDocumento";

	private final KeycloakSession session;
	protected EntityManager em;

	public JpaPersonaNaturalProvider(KeycloakSession session, EntityManager em) {
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
	public PersonaNaturalModel create(String codigoPais, TipoDocumentoModel tipoDocumento, String numeroDocumento,
			String apellidoPaterno, String apellidoMaterno, String nombres, Date fechaNacimiento, Sexo sexo) {
		TipoDocumentoEntity tipoDocumentoEntity = em.find(TipoDocumentoEntity.class, tipoDocumento.getId());

		PersonaNaturalEntity entity = new PersonaNaturalEntity();
		entity.setCodigoPais(codigoPais);
		entity.setTipoDocumento(tipoDocumentoEntity);
		entity.setNumeroDocumento(numeroDocumento);
		entity.setApellidoPaterno(apellidoPaterno);
		entity.setApellidoMaterno(apellidoMaterno);
		entity.setNombres(nombres);
		entity.setFechaNacimiento(fechaNacimiento);
		entity.setSexo(sexo.toString());
		em.persist(entity);
		em.flush();
		return new PersonaNaturalAdapter(session, em, entity);
	}

	@Override
	public boolean remove(PersonaNaturalModel personaNaturalModel) {
		TypedQuery<PersonaJuridicaEntity> query = em.createNamedQuery(
				"PersonaJuridicaEntity.findByIdPersonaNaturalRepresentanteLegal", PersonaJuridicaEntity.class);
		query.setParameter("idPersonaNaturalRepresentanteLegal", personaNaturalModel.getId());
		query.setMaxResults(1);
		if (!query.getResultList().isEmpty()) {
			return false;
		}

		PersonaNaturalEntity entity = em.find(PersonaNaturalEntity.class, personaNaturalModel.getId());
		if (entity == null) {
			return false;
		}
		em.remove(entity);
		em.flush();
		return true;
	}

	@Override
	public PersonaNaturalModel findById(String id) {
		PersonaNaturalEntity entity = this.em.find(PersonaNaturalEntity.class, id);
		return entity != null ? new PersonaNaturalAdapter(session, em, entity) : null;
	}

	@Override
	public PersonaNaturalModel findByTipoNumeroDocumento(TipoDocumentoModel tipoDocumento, String numeroDocumento) {
		TypedQuery<PersonaNaturalEntity> query = em.createNamedQuery("PersonaNaturalEntity.findByTipoNumeroDocumento",
				PersonaNaturalEntity.class);
		query.setParameter("tipoDocumento", tipoDocumento.getAbreviatura());
		query.setParameter("numeroDocumento", numeroDocumento);
		List<PersonaNaturalEntity> results = query.getResultList();
		if (results.isEmpty()) {
			return null;
		} else if (results.size() > 1) {
			throw new IllegalStateException("Mas de una PersonaNaturalEntity con tipoDocumento=" + tipoDocumento
					+ " y numeroDocumento=" + numeroDocumento + ", resultados=" + results);
		} else {
			return new PersonaNaturalAdapter(session, em, results.get(0));
		}
	}

	@Override
	public List<PersonaNaturalModel> getAll() {
		return getAll(-1, -1);
	}

	@Override
	public List<PersonaNaturalModel> getAll(int firstResult, int maxResults) {
		TypedQuery<PersonaNaturalEntity> query = em.createNamedQuery("PersonaNaturalEntity.findAll",
				PersonaNaturalEntity.class);
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<PersonaNaturalEntity> entities = query.getResultList();
		List<PersonaNaturalModel> result = new ArrayList<>();
		entities.forEach(entity -> result.add(new PersonaNaturalAdapter(session, em, entity)));
		return result;
	}

	@Override
	public List<PersonaNaturalModel> search(String filterText) {
		return search(filterText, -1, -1);
	}

	@Override
	public List<PersonaNaturalModel> search(String filterText, int firstResult, int maxResults) {
		TypedQuery<PersonaNaturalEntity> query = em.createNamedQuery("PersonaNaturalEntity.findByFilterText",
				PersonaNaturalEntity.class);
		query.setParameter("filterText", "%" + filterText.toLowerCase() + "%");
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<PersonaNaturalEntity> entities = query.getResultList();
		List<PersonaNaturalModel> result = new ArrayList<>();
		entities.forEach(entity -> result.add(new PersonaNaturalAdapter(session, em, entity)));
		return result;
	}

	@Override
	public List<PersonaNaturalModel> searchByAttributes(Map<String, String> attributes) {
		return searchByAttributes(attributes, -1, -1);
	}

	@Override
	public List<PersonaNaturalModel> searchByAttributes(Map<String, String> attributes, int firstResult,
			int maxResults) {
		StringBuilder builder = new StringBuilder("SELECT p FROM PersonaNaturalEntity");
		for (Map.Entry<String, String> entry : attributes.entrySet()) {
			String attribute = null;
			String parameterName = null;
			if (entry.getKey().equals(PersonaNaturalModel.APELLIDO_PATERNO)) {
				attribute = "lower(p.apellidoPaterno)";
				parameterName = JpaPersonaNaturalProvider.APELLIDO_PATERNO;
			} else if (entry.getKey().equalsIgnoreCase(PersonaNaturalModel.APELLIDO_MATERNO)) {
				attribute = "lower(p.apellidoMaterno)";
				parameterName = JpaPersonaNaturalProvider.APELLIDO_MATERNO;
			} else if (entry.getKey().equalsIgnoreCase(PersonaNaturalModel.NOMBRES)) {
				attribute = "lower(p.nombres)";
				parameterName = JpaPersonaNaturalProvider.NOMBRES;
			} else if (entry.getKey().equalsIgnoreCase(PersonaNaturalModel.NUMERO_DOCUMENTO)) {
				attribute = "lower(p.numeroDocumento)";
				parameterName = JpaPersonaNaturalProvider.NUMERO_DOCUMENTO;
			}

			if (attribute == null) {
				continue;
			}
			builder.append(attribute).append(" like :").append(parameterName);
		}
		builder.append(" order by p.apellidoPaterno, p.apellidoMaterno, p.nombres");
		String q = builder.toString();
		TypedQuery<PersonaNaturalEntity> query = em.createQuery(q, PersonaNaturalEntity.class);
		for (Map.Entry<String, String> entry : attributes.entrySet()) {
			String parameterName = null;
			if (entry.getKey().equals(PersonaNaturalModel.APELLIDO_PATERNO)) {
				parameterName = JpaPersonaNaturalProvider.APELLIDO_PATERNO;
			} else if (entry.getKey().equals(PersonaNaturalModel.APELLIDO_MATERNO)) {
				parameterName = JpaPersonaNaturalProvider.APELLIDO_MATERNO;
			} else if (entry.getKey().equals(PersonaNaturalModel.NOMBRES)) {
				parameterName = JpaPersonaNaturalProvider.NOMBRES;
			} else if (entry.getKey().equals(PersonaNaturalModel.NUMERO_DOCUMENTO)) {
				parameterName = JpaPersonaNaturalProvider.NUMERO_DOCUMENTO;
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

		List<PersonaNaturalEntity> entities = query.getResultList();
		List<PersonaNaturalModel> result = new ArrayList<>();
		entities.forEach(entity -> result.add(new PersonaNaturalAdapter(session, em, entity)));
		return result;
	}

	@Override
	public SearchResultsModel<PersonaNaturalModel> search(SearchCriteriaModel criteria) {
		SearchResultsModel<PersonaNaturalEntity> entityResult = find(criteria, PersonaNaturalEntity.class);

		SearchResultsModel<PersonaNaturalModel> modelResult = new SearchResultsModel<>();
		List<PersonaNaturalModel> list = new ArrayList<>();
		entityResult.getModels().forEach(entity -> list.add(new PersonaNaturalAdapter(session, em, entity)));
		modelResult.setTotalSize(entityResult.getTotalSize());
		modelResult.setModels(list);
		return modelResult;
	}

	@Override
	public SearchResultsModel<PersonaNaturalModel> search(SearchCriteriaModel criteria, String filterText) {
		SearchResultsModel<PersonaNaturalEntity> entityResult = findFullText(criteria, PersonaNaturalEntity.class,
				filterText, "numeroDocumento", "apellidoPaterno", "apellidoMaterno", "nombres");

		SearchResultsModel<PersonaNaturalModel> modelResult = new SearchResultsModel<>();
		List<PersonaNaturalModel> list = new ArrayList<>();
		entityResult.getModels().forEach(entity -> list.add(new PersonaNaturalAdapter(session, em, entity)));
		modelResult.setTotalSize(entityResult.getTotalSize());
		modelResult.setModels(list);
		return modelResult;
	}

}
