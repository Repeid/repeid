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

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.repeid.manager.api.jpa.AbstractJpaStorage;
import org.repeid.manager.api.jpa.entities.StoreConfigurationEntity;
import org.repeid.manager.api.model.StoreConfigurationModel;
import org.repeid.manager.api.model.StoreConfigurationProvider;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;
import org.repeid.manager.api.model.system.RepeidSession;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public class JpaStoreConfigurationProvider extends AbstractJpaStorage implements StoreConfigurationProvider {

	private final RepeidSession session;
	private EntityManager em;

	public JpaStoreConfigurationProvider(RepeidSession session, EntityManager em) {
		super(em);
		this.session = session;
		this.em = em;
	}

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

		StoreConfigurationEntity storeConfigurationEntity = new StoreConfigurationEntity();
		storeConfigurationEntity.setAppKey(appKey);
		storeConfigurationEntity.setDenominacion(denominacion);
		storeConfigurationEntity.setCarpetaFirma("firma");
		storeConfigurationEntity.setCarpetaFoto("foto");
		storeConfigurationEntity.setCarpetaRaiz(appKey);
		storeConfigurationEntity.setCarpetaTemporal("tmp");
		storeConfigurationEntity.setDefault(false);
		storeConfigurationEntity.setToken(null);
		em.persist(storeConfigurationEntity);
		em.flush();
		return new StoreConfigurationAdapter(session, em, storeConfigurationEntity);
	}

	@Override
	public StoreConfigurationModel findById(String id) {
		StoreConfigurationEntity entity = this.em.find(StoreConfigurationEntity.class, id);
		return entity != null ? new StoreConfigurationAdapter(session, em, entity) : null;
	}

	@Override
	public StoreConfigurationModel findByDenominacion(String denominacion) {
		TypedQuery<StoreConfigurationEntity> query = em.createNamedQuery("StoreConfigurationEntity.findByDenominacion",
				StoreConfigurationEntity.class);
		query.setParameter("denominacion", denominacion);
		List<StoreConfigurationEntity> results = query.getResultList();
		if (results.isEmpty()) {
			return null;
		} else if (results.size() > 1) {
			throw new IllegalStateException(
					"Mas de un StoreConfigurationEntity con denominacion=" + denominacion + ", results=" + results);
		} else {
			return new StoreConfigurationAdapter(session, em, results.get(0));
		}
	}

	@Override
	public StoreConfigurationModel getDefaultStoreConfiguration() {
		TypedQuery<StoreConfigurationEntity> query = em.createNamedQuery("StoreConfigurationEntity.findByIsDefault",
				StoreConfigurationEntity.class);
		query.setParameter("isDefault", true);
		List<StoreConfigurationEntity> results = query.getResultList();
		if (results.isEmpty()) {
			return null;
		} else if (results.size() > 1) {
			throw new IllegalStateException(
					"Mas de un StoreConfigurationEntity con isDefault=" + true + ", results=" + results);
		} else {
			return new StoreConfigurationAdapter(session, em, results.get(0));
		}
	}

	@Override
	public boolean remove(StoreConfigurationModel storeConfiguration) {
		StoreConfigurationEntity storeConfigurationEntity = em.find(StoreConfigurationEntity.class,
				storeConfiguration.getId());
		if (storeConfigurationEntity == null) {
			return false;
		}
		em.remove(storeConfigurationEntity);
		em.flush();
		return true;
	}

	@Override
	public List<StoreConfigurationModel> getAll() {
		TypedQuery<StoreConfigurationEntity> query = em.createNamedQuery("StoreConfigurationEntity.findAll",
				StoreConfigurationEntity.class);

		List<StoreConfigurationEntity> entities = query.getResultList();
		List<StoreConfigurationModel> models = new ArrayList<StoreConfigurationModel>();
		for (StoreConfigurationEntity storeConfigurationEntity : entities) {
			models.add(new StoreConfigurationAdapter(session, em, storeConfigurationEntity));
		}
		return models;
	}

}
