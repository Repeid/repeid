package org.repeid.manager.api.jpa;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.model.system.RepeidTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
@Stateless
public class JpaRepeidTransaction implements RepeidTransaction {

	private static Logger logger = LoggerFactory.getLogger(JpaRepeidTransaction.class);

	protected EntityManager em;

	public JpaRepeidTransaction(EntityManager em) {
		this.em = em;
	}

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
