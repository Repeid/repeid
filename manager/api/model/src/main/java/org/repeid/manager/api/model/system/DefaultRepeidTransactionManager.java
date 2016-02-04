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

package org.repeid.manager.api.model.system;

import org.jboss.logging.Logger;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

@ApplicationScoped
public class DefaultRepeidTransactionManager implements RepeidTransactionManager {

	public static final Logger log = Logger.getLogger(DefaultRepeidTransactionManager.class);

	@Inject
	private RepeidTransaction repeidTransaction;

	private List<RepeidTransaction> transactions = new LinkedList<RepeidTransaction>();
	private List<RepeidTransaction> afterCompletion = new LinkedList<RepeidTransaction>();
	private boolean active;
	private boolean rollback;

	@PostConstruct
	public void init() {
		lazyInit();
		log.info("RepeidTransactionManager started");
	}

	@PreDestroy
	public void close() {
		log.info("Stopping RepeidTransactionManager");
	}

	public void lazyInit() {
		enlist(repeidTransaction);
		log.info("Enlisted RepeidTransacction on RepeidTransactionManager");
	}

	@Override
	public void enlist(RepeidTransaction transaction) {
		if (active && !transaction.isActive()) {
			transaction.begin();
		}

		transactions.add(transaction);
	}

	@Override
	public void enlistAfterCompletion(RepeidTransaction transaction) {
		if (active && !transaction.isActive()) {
			transaction.begin();
		}

		afterCompletion.add(transaction);
	}

	@Override
	public void begin() {
		if (active) {
			throw new IllegalStateException("Transaction already active");
		}

		for (RepeidTransaction tx : transactions) {
			tx.begin();
		}

		active = true;
	}

	@Override
	public void commit() {
		RuntimeException exception = null;
		for (RepeidTransaction tx : transactions) {
			try {
				tx.commit();
			} catch (RuntimeException e) {
				exception = exception == null ? e : exception;
			}
		}

		// Don't commit "afterCompletion" if commit of some main transaction
		// failed
		if (exception == null) {
			for (RepeidTransaction tx : afterCompletion) {
				try {
					tx.commit();
				} catch (RuntimeException e) {
					exception = exception == null ? e : exception;
				}
			}
		} else {
			for (RepeidTransaction tx : afterCompletion) {
				try {
					tx.rollback();
				} catch (RuntimeException e) {
					log.error("Exception during rollback", e);
				}
			}
		}

		active = false;
		if (exception != null) {
			throw exception;
		}
	}

	@Override
	public void rollback() {
		RuntimeException exception = null;
		for (RepeidTransaction tx : transactions) {
			try {
				tx.rollback();
			} catch (RuntimeException e) {
				exception = exception != null ? e : exception;
			}
		}
		for (RepeidTransaction tx : afterCompletion) {
			try {
				tx.rollback();
			} catch (RuntimeException e) {
				exception = exception != null ? e : exception;
			}
		}
		active = false;
		if (exception != null) {
			throw exception;
		}
	}

	@Override
	public void setRollbackOnly() {
		rollback = true;
	}

	@Override
	public boolean getRollbackOnly() {
		if (rollback) {
			return true;
		}

		for (RepeidTransaction tx : transactions) {
			if (tx.getRollbackOnly()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isActive() {
		return active;
	}

}
