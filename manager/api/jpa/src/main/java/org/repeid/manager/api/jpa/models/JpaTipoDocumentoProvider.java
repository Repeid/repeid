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
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.repeid.manager.api.jpa.AbstractJpaStorage;
import org.repeid.manager.api.jpa.entities.PersonaJuridicaEntity;
import org.repeid.manager.api.jpa.entities.PersonaNaturalEntity;
import org.repeid.manager.api.jpa.entities.TipoDocumentoEntity;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.TipoDocumentoProvider;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;
import org.repeid.manager.api.model.system.RepeidSession;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public class JpaTipoDocumentoProvider extends AbstractJpaStorage implements TipoDocumentoProvider {

	private static final String ABREVIATURA = "abreviatura";
	private static final String DENOMINACION = "denominacion";
	private static final String TIPO_PERSONA = "tipoPersona";
	private static final String ESTADO = "estado";

	private final RepeidSession session;
	private EntityManager em;

	public JpaTipoDocumentoProvider(RepeidSession session, EntityManager em) {
		super(em);
		this.session = session;
		this.em = em;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public TipoDocumentoModel create(String abreviatura, String denominacion, int cantidadCaracteres,
			TipoPersona tipoPersona) {
		if (findByAbreviatura(abreviatura) != null) {
			throw new ModelDuplicateException(
					"TipoDocumentoEntity abreviatura debe ser unico, se encontro otra entidad con abreviatura:"
							+ abreviatura);
		}

		TipoDocumentoEntity entity = new TipoDocumentoEntity();
		entity.setAbreviatura(abreviatura);
		entity.setDenominacion(denominacion);
		entity.setCantidadCaracteres(cantidadCaracteres);
		entity.setTipoPersona(tipoPersona.toString());
		entity.setEstado(true);
		em.persist(entity);
		em.flush();
		return new TipoDocumentoAdapter(session, em, entity);
	}

	@Override
	public TipoDocumentoModel findByAbreviatura(String abreviatura) {
		TypedQuery<TipoDocumentoEntity> query = em.createNamedQuery("TipoDocumentoEntity.findByAbreviatura",
				TipoDocumentoEntity.class);
		query.setParameter("abreviatura", abreviatura);
		List<TipoDocumentoEntity> results = query.getResultList();
		if (results.isEmpty()) {
			return null;
		} else if (results.size() > 1) {
			throw new IllegalStateException(
					"Mas de un TipoDocumentoEntity con abreviatura=" + abreviatura + ", results=" + results);
		} else {
			TipoDocumentoEntity entity = results.get(0);
			return new TipoDocumentoAdapter(session, em, entity);
		}
	}

	@Override
	public TipoDocumentoModel findById(String id) {
		TipoDocumentoEntity entity = em.find(TipoDocumentoEntity.class, id);
		return entity != null ? new TipoDocumentoAdapter(session, em, entity) : null;
	}

	@Override
	public boolean remove(TipoDocumentoModel tipoDocumento) {
		TypedQuery<PersonaNaturalEntity> query1 = em.createNamedQuery("PersonaNaturalEntity.findByTipoDocumento",
				PersonaNaturalEntity.class);
		query1.setParameter("tipoDocumento", tipoDocumento.getAbreviatura());
		query1.setMaxResults(1);
		if (!query1.getResultList().isEmpty()) {
			return false;
		}

		TypedQuery<PersonaJuridicaEntity> query2 = em.createNamedQuery("PersonaJuridicaEntity.findByTipoDocumento",
				PersonaJuridicaEntity.class);
		query2.setParameter("tipoDocumento", tipoDocumento.getAbreviatura());
		query2.setMaxResults(1);
		if (!query2.getResultList().isEmpty()) {
			return false;
		}

		TipoDocumentoEntity tipoDocumentoEntity = em.find(TipoDocumentoEntity.class, tipoDocumento.getId());
		if (tipoDocumentoEntity == null) {
			return false;
		}
		em.remove(tipoDocumentoEntity);
		em.flush();
		return true;
	}

	@Override
	public List<TipoDocumentoModel> getAll() {
		return getAll(-1, -1);
	}

	@Override
	public List<TipoDocumentoModel> getAll(int firstResult, int maxResults) {
		TypedQuery<TipoDocumentoEntity> query = em.createNamedQuery("TipoDocumentoEntity.findAll",
				TipoDocumentoEntity.class);
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<TipoDocumentoEntity> entities = query.getResultList();
		List<TipoDocumentoModel> models = new ArrayList<TipoDocumentoModel>();
		for (TipoDocumentoEntity tipoDocumentoEntity : entities) {
			models.add(new TipoDocumentoAdapter(session, em, tipoDocumentoEntity));
		}
		return models;
	}

	@Override
	public List<TipoDocumentoModel> search(String filterText) {
		return search(filterText, -1, -1);
	}

	@Override
	public List<TipoDocumentoModel> search(String filterText, int firstResult, int maxResults) {
		TypedQuery<TipoDocumentoEntity> query = em.createNamedQuery("TipoDocumentoEntity.findByFilterText",
				TipoDocumentoEntity.class);
		query.setParameter("filterText", "%" + filterText.toLowerCase() + "%");
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<TipoDocumentoEntity> entities = query.getResultList();
		List<TipoDocumentoModel> models = new ArrayList<TipoDocumentoModel>();
		for (TipoDocumentoEntity tipoDocumentoEntity : entities) {
			models.add(new TipoDocumentoAdapter(session, em, tipoDocumentoEntity));
		}

		return models;
	}

	@Override
	public List<TipoDocumentoModel> searchByAttributes(Map<String, Object> attributes) {
		return searchByAttributes(attributes, -1, -1);
	}

	@Override
	public List<TipoDocumentoModel> searchByAttributes(Map<String, Object> attributes, int firstResult,
			int maxResults) {
		StringBuilder builder = new StringBuilder("SELECT t FROM TipoDocumentoEntity");
		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attribute = null;
			String parameterName = null;
			if (entry.getKey().equals(TipoDocumentoModel.ABREVIATURA)) {
				attribute = "lower(t.abreviatura)";
				parameterName = JpaTipoDocumentoProvider.ABREVIATURA;
			} else if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.DENOMINACION)) {
				attribute = "lower(t.denominacion)";
				parameterName = JpaTipoDocumentoProvider.DENOMINACION;
			}

			if (attribute != null) {
				builder.append(" and ");
				builder.append(attribute).append(" like :").append(parameterName);
			} else {
				if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.TIPO_PERSONA)) {
					attribute = "lower(t.tipoPersona)";
					parameterName = JpaTipoDocumentoProvider.TIPO_PERSONA;
				} else if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.ESTADO)) {
					attribute = "t.estado";
					parameterName = JpaTipoDocumentoProvider.ESTADO;
				}

				if (attribute == null) {
					continue;
				}
				builder.append(" and ");
				builder.append(attribute).append(" = :").append(parameterName);
			}
		}
		builder.append(" order by t.abreviatura");
		String q = builder.toString();
		TypedQuery<TipoDocumentoEntity> query = em.createQuery(q, TipoDocumentoEntity.class);
		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String parameterName = null;
			if (entry.getKey().equals(TipoDocumentoModel.ABREVIATURA)) {
				parameterName = JpaTipoDocumentoProvider.ABREVIATURA;
			} else if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.DENOMINACION)) {
				parameterName = JpaTipoDocumentoProvider.DENOMINACION;
			}

			if (parameterName != null) {
				query.setParameter(parameterName, "%" + String.valueOf(entry.getValue()).toLowerCase() + "%");
			} else {
				if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.TIPO_PERSONA)) {
					parameterName = JpaTipoDocumentoProvider.TIPO_PERSONA;
				} else if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.ESTADO)) {
					parameterName = JpaTipoDocumentoProvider.ESTADO;
				}

				if (parameterName == null) {
					continue;
				}
				query.setParameter(parameterName, entry.getValue());
			}
		}
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<TipoDocumentoEntity> results = query.getResultList();
		List<TipoDocumentoModel> tipoDocumentos = new ArrayList<TipoDocumentoModel>();
		for (TipoDocumentoEntity entity : results)
			tipoDocumentos.add(new TipoDocumentoAdapter(session, em, entity));
		return tipoDocumentos;
	}

	@Override
	public SearchResultsModel<TipoDocumentoModel> search(SearchCriteriaModel criteria) {
		SearchResultsModel<TipoDocumentoEntity> entityResult = find(criteria, TipoDocumentoEntity.class);

		SearchResultsModel<TipoDocumentoModel> modelResult = new SearchResultsModel<>();
		List<TipoDocumentoModel> list = new ArrayList<>();
		for (TipoDocumentoEntity entity : entityResult.getModels()) {
			list.add(new TipoDocumentoAdapter(session, em, entity));
		}
		modelResult.setTotalSize(entityResult.getTotalSize());
		modelResult.setModels(list);
		return modelResult;
	}

	@Override
	public SearchResultsModel<TipoDocumentoModel> search(SearchCriteriaModel criteria, String filterText) {
		SearchResultsModel<TipoDocumentoEntity> entityResult = findFullText(criteria, TipoDocumentoEntity.class,
				filterText, ABREVIATURA, DENOMINACION);

		SearchResultsModel<TipoDocumentoModel> modelResult = new SearchResultsModel<>();
		List<TipoDocumentoModel> list = new ArrayList<>();
		for (TipoDocumentoEntity entity : entityResult.getModels()) {
			list.add(new TipoDocumentoAdapter(session, em, entity));
		}
		modelResult.setTotalSize(entityResult.getTotalSize());
		modelResult.setModels(list);
		return modelResult;
	}

}
