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
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import org.repeid.manager.api.model.StoreConfigurationModel;
import org.repeid.manager.api.model.StoreConfigurationProvider;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;
import org.repeid.manager.api.model.provider.ProviderFactory;
import org.repeid.manager.api.model.provider.ProviderType;
import org.repeid.manager.api.mongo.AbstractMongoStorage;
import org.repeid.manager.api.mongo.entities.MongoStoreConfigurationEntity;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@ProviderFactory(ProviderType.MONGO)
public class MongoStorageConfigurationProvider extends AbstractMongoStorage implements StoreConfigurationProvider {

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public StoreConfigurationModel create(String appKey, String denominacion) {
		if (findByDenominacion(denominacion) != null) {
			throw new ModelDuplicateException(
					"StoreConfigurationEntity denominacion debe ser unico, se encontro otra entidad con denominacion:"
							+ denominacion);
		}

		MongoStoreConfigurationEntity storeConfigurationEntity = new MongoStoreConfigurationEntity();
		storeConfigurationEntity.setAppKey(appKey);
		storeConfigurationEntity.setDenominacion(denominacion);
		storeConfigurationEntity.setCarpetaFirma("firma");
		storeConfigurationEntity.setCarpetaFoto("foto");
		storeConfigurationEntity.setCarpetaRaiz(appKey);
		storeConfigurationEntity.setCarpetaTemporal("tmp");
		storeConfigurationEntity.setDefault(false);
		storeConfigurationEntity.setToken(null);
		getEntityManager().persist(storeConfigurationEntity);
		return new StoreConfigurationAdapter(getEntityManager(), storeConfigurationEntity);
	}

	@Override
	public StoreConfigurationModel findById(String id) {
		MongoStoreConfigurationEntity entity = this.getEntityManager().find(MongoStoreConfigurationEntity.class, id);
		return entity != null ? new StoreConfigurationAdapter(getEntityManager(), entity) : null;
	}

	@Override
	public StoreConfigurationModel findByDenominacion(String denominacion) {
		TypedQuery<MongoStoreConfigurationEntity> query = getEntityManager().createNamedQuery(
				"MongoStoreConfigurationEntity.findByDenominacion", MongoStoreConfigurationEntity.class);
		query.setParameter("denominacion", denominacion);
		List<MongoStoreConfigurationEntity> results = query.getResultList();
		if (results.isEmpty()) {
			return null;
		} else if (results.size() > 1) {
			throw new IllegalStateException(
					"Mas de un StoreConfigurationEntity con denominacion=" + denominacion + ", results=" + results);
		} else {
			return new StoreConfigurationAdapter(getEntityManager(), results.get(0));
		}
	}

	@Override
	public StoreConfigurationModel getDefaultStoreConfiguration() {
		TypedQuery<MongoStoreConfigurationEntity> query = getEntityManager()
				.createNamedQuery("MongoStoreConfigurationEntity.findByIsDefault", MongoStoreConfigurationEntity.class);
		query.setParameter("isDefault", true);
		List<MongoStoreConfigurationEntity> results = query.getResultList();
		if (results.isEmpty()) {
			return null;
		} else if (results.size() > 1) {
			throw new IllegalStateException(
					"Mas de un StoreConfigurationEntity con isDefault=" + true + ", results=" + results);
		} else {
			return new StoreConfigurationAdapter(getEntityManager(), results.get(0));
		}
	}

	@Override
	public boolean remove(StoreConfigurationModel storeConfiguration) {
		MongoStoreConfigurationEntity storeConfigurationEntity = getEntityManager()
				.find(MongoStoreConfigurationEntity.class, storeConfiguration.getId());
		if (storeConfigurationEntity == null) {
			return false;
		}
		getEntityManager().remove(storeConfigurationEntity);
		return true;
	}

	@Override
	public List<StoreConfigurationModel> getAll() {
		TypedQuery<MongoStoreConfigurationEntity> query = getEntityManager()
				.createNamedQuery("MongoStoreConfigurationEntity.findAll", MongoStoreConfigurationEntity.class);

		List<MongoStoreConfigurationEntity> entities = query.getResultList();
		List<StoreConfigurationModel> models = new ArrayList<StoreConfigurationModel>();
		for (MongoStoreConfigurationEntity storeConfigurationEntity : entities) {
			models.add(new StoreConfigurationAdapter(getEntityManager(), storeConfigurationEntity));
		}
		return models;
	}

}
