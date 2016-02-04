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

package org.repeid.manager.api.jpa;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.repeid.manager.api.model.provider.ProviderType;
import org.repeid.manager.api.model.provider.ProviderType.Type;
import org.repeid.manager.api.model.system.RepeidTransaction;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
@ProviderType(Type.JPA)
public class JpaRepeidTransaction implements RepeidTransaction {

	@Inject
	private JpaConnectionProvider connectionProvider;

	private EntityManager em;

	@PostConstruct
	public void init() {
		this.em = connectionProvider.getEntityManager();
	}

	@Override
	public void begin() {
		em.getTransaction().begin();
	}

	@Override
	public void commit() {
		try {
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			throw PersistenceExceptionConverter.convert(e.getCause() != null ? e.getCause() : e);
		}
	}

	@Override
	public void rollback() {
		em.getTransaction().rollback();
	}

	@Override
	public void setRollbackOnly() {
		em.getTransaction().setRollbackOnly();
	}

	@Override
	public boolean getRollbackOnly() {
		return em.getTransaction().getRollbackOnly();
	}

	@Override
	public boolean isActive() {
		return em.getTransaction().isActive();
	}

}
