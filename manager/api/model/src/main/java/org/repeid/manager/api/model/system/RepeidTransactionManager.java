package org.repeid.manager.api.model.system;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface RepeidTransactionManager extends RepeidTransaction {

	void enlist(RepeidTransaction transaction);

	void enlistAfterCompletion(RepeidTransaction transaction);

}
