package org.repeid.models;

import java.util.Set;

import org.repeid.provider.Provider;

public interface RepeidSession {

	RepeidTransactionManager getTransaction();

	<T extends Provider> T getProvider(Class<T> clazz);

	<T extends Provider> T getProvider(Class<T> clazz, String id);

	<T extends Provider> Set<String> listProviderIds(Class<T> clazz);

	<T extends Provider> Set<T> getAllProviders(Class<T> clazz);

	void enlistForClose(Provider provider);

	RepeidSessionFactory getRepeidSessionFactory();

	/**
	 * Returns a managed provider instance. Will start a provider transaction.
	 * This transaction is managed by the KeycloakSession transaction.
	 *
	 * @return
	 * @throws IllegalStateException
	 *             if transaction is not active
	 */
	RealmProvider realms();

	void close();

}
