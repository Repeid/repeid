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

import java.util.UUID;

import javax.persistence.EntityManager;

import org.repeid.manager.api.jpa.AbstractJpaStorage;
import org.repeid.manager.api.jpa.entities.FileEntity;
import org.repeid.manager.api.jpa.entities.StoreConfigurationEntity;
import org.repeid.manager.api.jpa.entities.StoredFileEntity;
import org.repeid.manager.api.model.KeycloakSession;
import org.repeid.manager.api.model.StoreConfigurationModel;
import org.repeid.manager.api.model.StoredFileModel;
import org.repeid.manager.api.model.StoredFileProvider;
import org.repeid.manager.api.model.enums.StoreConfigurationType;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public class JpaStoredFileProvider extends AbstractJpaStorage implements StoredFileProvider {

	private final KeycloakSession session;
	private EntityManager em;

	public JpaStoredFileProvider(KeycloakSession session, EntityManager em) {
		super(em);
		this.session = session;
		this.em = em;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public StoredFileModel findById(String id) {
		StoredFileEntity storedFileEntity = em.find(StoredFileEntity.class, id);
		return storedFileEntity != null ? new StoredFileAdapter(session, em, storedFileEntity) : null;
	}

	@Override
	public StoredFileModel create(byte[] file, StoreConfigurationModel configuration) {
		StoreConfigurationType provider = configuration.getProviderName();
		switch (provider) {
		case localhost:
			return createLocalFile(file, configuration);
		default:
			return null;
		}
	}

	private StoredFileModel createLocalFile(byte[] file, StoreConfigurationModel configuration) {
		// File storage
		FileEntity fileEntity = new FileEntity();
		fileEntity.setFile(file);
		em.persist(fileEntity);

		// Store configuration entity
		StoreConfigurationEntity storeConfigurationEntity = StoreConfigurationAdapter
				.toStoreConfigurationEntity(configuration, em);

		// Create StoreFileEntity
		StoredFileEntity storedFileEntity = new StoredFileEntity();
		storedFileEntity.setFileId(UUID.randomUUID().toString());
		storedFileEntity.setUrl(UUID.randomUUID().toString());
		storedFileEntity.setStoreConfiguration(storeConfigurationEntity);

		em.persist(storedFileEntity);
		em.flush();
		return new StoredFileAdapter(session, em, storedFileEntity);
	}

	@Override
	public byte[] download(String fileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(StoredFileModel storedFile) {
		// TODO Auto-generated method stub
		return false;
	}

}
