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
package org.repeid.manager.api.mongo.models.security;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.beans.representations.security.PermissionType;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;
import org.repeid.manager.api.model.provider.ProviderFactory;
import org.repeid.manager.api.model.provider.ProviderType;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;
import org.repeid.manager.api.model.security.RoleModel;
import org.repeid.manager.api.model.security.RoleProvider;
import org.repeid.manager.api.mongo.AbstractMongoStorage;
import org.repeid.manager.api.mongo.entities.security.MongoRoleEntity;
import org.repeid.manager.api.mongo.entities.security.MongoRoleMembershipEntity;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@ProviderFactory(ProviderType.MONGO)
public class MongoRoleProvider extends AbstractMongoStorage implements RoleProvider {

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public RoleModel create(String name, String description, boolean autoGrant, Set<PermissionType> permissions,
			String createdBy) throws StorageException {
		if (findByName(name) != null) {
			throw new ModelDuplicateException(
					"RoleEntity name debe ser unico, se encontro otra entidad con name:" + name);
		}

		MongoRoleEntity roleEntity = new MongoRoleEntity();
		roleEntity.setName(name);
		roleEntity.setDescription(description);
		roleEntity.setAutoGrant(autoGrant);
		roleEntity.setPermissions(permissions);
		roleEntity.setCreatedOn(Calendar.getInstance().getTime());
		roleEntity.setCreatedBy(createdBy);
		getEntityManager().persist(roleEntity);
		return new RoleAdapter(getEntityManager(), roleEntity);
	}

	@Override
	public RoleModel findById(String id) throws StorageException {
		MongoRoleEntity roleEntity = getEntityManager().find(MongoRoleEntity.class, id);
		return roleEntity != null ? new RoleAdapter(getEntityManager(), roleEntity) : null;
	}

	@Override
	public RoleModel findByName(String rolName) throws StorageException {
		TypedQuery<MongoRoleEntity> query = getEntityManager().createNamedQuery("RoleEntity.findByName", MongoRoleEntity.class);
		query.setParameter("name", rolName);
		List<MongoRoleEntity> results = query.getResultList();
		if (results.isEmpty()) {
			return null;
		} else if (results.size() > 1) {
			throw new IllegalStateException("Mas de un RoleEntity con nama=" + rolName + ", results=" + results);
		} else {
			return new RoleAdapter(getEntityManager(), results.get(0));
		}
	}

	@Override
	public boolean remove(RoleModel role) {
		MongoRoleEntity roleEntity = getEntityManager().find(MongoRoleEntity.class, role.getId());

		TypedQuery<MongoRoleMembershipEntity> query = getEntityManager()
				.createNamedQuery("RoleMembershipEntity.findByRoleId", MongoRoleMembershipEntity.class);
		query.setParameter("roleId", role.getId());
		List<MongoRoleMembershipEntity> roleMemberships = query.getResultList();

		getEntityManager().remove(roleEntity);
		for (MongoRoleMembershipEntity roleMembership : roleMemberships) {
			getEntityManager().remove(roleMembership.getUser());
			getEntityManager().remove(roleMembership);
		}
		return true;
	}

	@Override
	public List<RoleModel> getAll() {
		return getAll(-1, -1);
	}

	@Override
	public List<RoleModel> getAll(int firstResult, int maxResults) {
		TypedQuery<MongoRoleEntity> query = getEntityManager().createNamedQuery("RoleEntity.findAll", MongoRoleEntity.class);
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<MongoRoleEntity> entities = query.getResultList();
		List<RoleModel> models = new ArrayList<>();
		for (MongoRoleEntity entity : entities) {
			models.add(new RoleAdapter(getEntityManager(), entity));
		}
		return models;
	}

	/*
	 * @Override public List<TipoDocumentoModel> search(String filterText) {
	 * return search(filterText, -1, -1); }
	 * 
	 * @Override public List<TipoDocumentoModel> search(String filterText, int
	 * firstResult, int maxResults) { TypedQuery<TipoDocumentoEntity> query =
	 * getEntityManager()
	 * .createNamedQuery("TipoDocumentoEntity.findByFilterText",
	 * TipoDocumentoEntity.class); query.setParameter("filterText", "%" +
	 * filterText.toLowerCase() + "%"); if (firstResult != -1) {
	 * query.setFirstResult(firstResult); } if (maxResults != -1) {
	 * query.setMaxResults(maxResults); } List<TipoDocumentoEntity> entities =
	 * query.getResultList(); List<TipoDocumentoModel> models = new
	 * ArrayList<TipoDocumentoModel>(); for (TipoDocumentoEntity
	 * tipoDocumentoEntity : entities) { models.add(new
	 * TipoDocumentoAdapter(getEntityManager(), tipoDocumentoEntity)); }
	 * 
	 * return models; }
	 */

	/*
	 * @Override public List<TipoDocumentoModel> searchByAttributes(Map<String,
	 * Object> attributes) { return searchByAttributes(attributes, -1, -1); }
	 * 
	 * @Override public List<TipoDocumentoModel> searchByAttributes(Map<String,
	 * Object> attributes, int firstResult, int maxResults) { StringBuilder
	 * builder = new StringBuilder("SELECT t FROM TipoDocumentoEntity"); for
	 * (Map.Entry<String, Object> entry : attributes.entrySet()) { String
	 * attribute = null; String parameterName = null; if
	 * (entry.getKey().equals(TipoDocumentoModel.ABREVIATURA)) { attribute =
	 * "lower(t.abreviatura)"; parameterName = JpaUserProvider.ABREVIATURA; }
	 * else if
	 * (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.DENOMINACION)) {
	 * attribute = "lower(t.denominacion)"; parameterName =
	 * JpaUserProvider.DENOMINACION; }
	 * 
	 * if (attribute != null) { builder.append(" and ");
	 * builder.append(attribute).append(" like :").append(parameterName); } else
	 * { if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.TIPO_PERSONA)) {
	 * attribute = "lower(t.tipoPersona)"; parameterName =
	 * JpaUserProvider.TIPO_PERSONA; } else if
	 * (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.ESTADO)) { attribute
	 * = "t.estado"; parameterName = JpaUserProvider.ESTADO; }
	 * 
	 * if (attribute == null) { continue; } builder.append(" and ");
	 * builder.append(attribute).append(" = :").append(parameterName); } }
	 * builder.append(" order by t.abreviatura"); String q = builder.toString();
	 * TypedQuery<TipoDocumentoEntity> query = getEntityManager().createQuery(q,
	 * TipoDocumentoEntity.class); for (Map.Entry<String, Object> entry :
	 * attributes.entrySet()) { String parameterName = null; if
	 * (entry.getKey().equals(TipoDocumentoModel.ABREVIATURA)) { parameterName =
	 * JpaUserProvider.ABREVIATURA; } else if
	 * (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.DENOMINACION)) {
	 * parameterName = JpaUserProvider.DENOMINACION; }
	 * 
	 * if (parameterName != null) { query.setParameter(parameterName, "%" +
	 * String.valueOf(entry.getValue()).toLowerCase() + "%"); } else { if
	 * (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.TIPO_PERSONA)) {
	 * parameterName = JpaUserProvider.TIPO_PERSONA; } else if
	 * (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.ESTADO)) {
	 * parameterName = JpaUserProvider.ESTADO; }
	 * 
	 * if (parameterName == null) { continue; }
	 * query.setParameter(parameterName, entry.getValue()); } } if (firstResult
	 * != -1) { query.setFirstResult(firstResult); } if (maxResults != -1) {
	 * query.setMaxResults(maxResults); } List<TipoDocumentoEntity> results =
	 * query.getResultList(); List<TipoDocumentoModel> tipoDocumentos = new
	 * ArrayList<TipoDocumentoModel>(); for (TipoDocumentoEntity entity :
	 * results) tipoDocumentos.add(new TipoDocumentoAdapter(getEntityManager(),
	 * entity)); return tipoDocumentos; }
	 */

	@Override
	public SearchResultsModel<RoleModel> search(SearchCriteriaModel criteria) {
		SearchResultsModel<MongoRoleEntity> entityResult = find(criteria, MongoRoleEntity.class);

		SearchResultsModel<RoleModel> modelResult = new SearchResultsModel<>();
		List<RoleModel> list = new ArrayList<>();
		for (MongoRoleEntity entity : entityResult.getModels()) {
			list.add(new RoleAdapter(getEntityManager(), entity));
		}
		modelResult.setTotalSize(entityResult.getTotalSize());
		modelResult.setModels(list);
		return modelResult;
	}

	@Override
	public SearchResultsModel<RoleModel> search(SearchCriteriaModel criteria, String filterText) {
		SearchResultsModel<MongoRoleEntity> entityResult = findFullText(criteria, MongoRoleEntity.class, filterText, "name");

		SearchResultsModel<RoleModel> modelResult = new SearchResultsModel<>();
		List<RoleModel> list = new ArrayList<>();
		for (MongoRoleEntity entity : entityResult.getModels()) {
			list.add(new RoleAdapter(getEntityManager(), entity));
		}
		modelResult.setTotalSize(entityResult.getTotalSize());
		modelResult.setModels(list);
		return modelResult;
	}

}
