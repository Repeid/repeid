package org.repeid.manager.api.model.system;

import org.repeid.manager.api.beans.exceptions.StorageException;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 * @version $Revision: 1 $
 */
public interface RepeidTransaction {

	void beginTx() throws StorageException;

	void commitTx() throws StorageException;

	void rollbackTx();

	void setRollbackTxOnly();

	boolean getRollbackTxOnly();

	boolean isTxActive();

}