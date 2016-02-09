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
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.repeid.manager.api.model.system.RepeidSession;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

@Stateless
public class DefaultJpaConnectionProvider implements JpaConnectionProvider {

	@Inject
	private JpaConnectionProviderFactory cpf;

	private EntityManager em;

	@Inject
	private RepeidSession session;

	@PostConstruct
	public void init() {
		em = cpf.getEntityManagerFactory().createEntityManager();
		em = PersistenceExceptionConverter.create(em);
		session.getTransaction().enlist(new JpaRepeidTransaction(em));
	}

	@Override
	@PreDestroy
	public void close() {

	}

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

}
