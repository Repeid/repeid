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

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.model.system.RepeidTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 * @version $Revision: 1 $
 */

public class JpaRepeidTransaction implements RepeidTransaction {

	private static Logger logger = LoggerFactory.getLogger(JpaRepeidTransaction.class);

	protected EntityManager em;

	@Override
	public void beginTx() throws StorageException {
		em.getTransaction().begin();
	}

	@Override
	public void commitTx() throws StorageException {
		try {
			em.getTransaction().commit();
		} catch (EntityExistsException e) {
			throw new StorageException(e);
		} catch (RollbackException e) {
			logger.error(e.getMessage(), e);
			throw new StorageException(e);
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new StorageException(t);
		}
	}

	@Override
	public void rollbackTx() {
		em.getTransaction().rollback();
	}

	@Override
	public void setRollbackTxOnly() {
		em.getTransaction().setRollbackOnly();
	}

	@Override
	public boolean getRollbackTxOnly() {
		return em.getTransaction().getRollbackOnly();
	}

	@Override
	public boolean isTxActive() {
		return em.getTransaction().isActive();
	}

}
