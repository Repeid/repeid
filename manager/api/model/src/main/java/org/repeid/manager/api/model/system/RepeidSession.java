package org.repeid.manager.api.model.system;

import java.util.Set;

import org.repeid.manager.api.model.provider.Provider;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface RepeidSession {

	RepeidTransactionManager getTransaction();

	<T extends Provider> T getProvider(Class<T> clazz);

	<T extends Provider> T getProvider(Class<T> clazz, String id);

	<T extends Provider> Set<String> listProviderIds(Class<T> clazz);

	<T extends Provider> Set<T> getAllProviders(Class<T> clazz);

	void enlistForClose(Provider provider);

	RepeidSessionFactory getKeycloakSessionFactory();

	void close();

}
