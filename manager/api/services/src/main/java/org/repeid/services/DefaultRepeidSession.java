package org.repeid.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.KeycloakTransactionManager;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.cache.CacheRealmProvider;
import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;

public class DefaultRepeidSession implements KeycloakSession {

	private final DefaultRepeidSessionFactory factory;
	private final Map<Integer, Provider> providers = new HashMap<Integer, Provider>();
	private final List<Provider> closable = new LinkedList<Provider>();
	private final DefaultRepeidTransactionManager transactionManager;

	private RealmProvider realmProvider;

	public DefaultRepeidSession(DefaultRepeidSessionFactory factory) {
		this.factory = factory;
		this.transactionManager = new DefaultRepeidTransactionManager();
	}

	/**
	 * @return RepeidTransactionManager return transaction manager associate to
	 *         the session.
	 */
	@Override
	public KeycloakTransactionManager getTransaction() {
		return transactionManager;
	}

	/**
	 * @param provider
	 *            added to invoke close method of the provider on
	 *            DefaultRepeidSession close.
	 */
	@Override
	public void enlistForClose(Provider provider) {
		closable.add(provider);
	}

	/**
	 * @param clazz
	 *            return provider for the given class. If the provider don't
	 *            exists then this method create it and save on local variable.
	 */
	@Override
	public <T extends Provider> T getProvider(Class<T> clazz) {
		Integer hash = clazz.hashCode();
		T provider = (T) providers.get(hash);
		if (provider == null) {
			ProviderFactory<T> providerFactory = factory.getProviderFactory(clazz);
			if (providerFactory != null) {
				provider = providerFactory.create(this);
				providers.put(hash, provider);
			}
		}
		return provider;
	}

	/**
	 * @param clazz
	 * @param id
	 *            return provider for the given class. If the provider don't
	 *            exists then this method create it and save on local variable.
	 */
	@Override
	public <T extends Provider> T getProvider(Class<T> clazz, String id) {
		Integer hash = clazz.hashCode() + id.hashCode();
		T provider = (T) providers.get(hash);
		if (provider == null) {
			ProviderFactory<T> providerFactory = factory.getProviderFactory(clazz, id);
			if (providerFactory != null) {
				provider = providerFactory.create(this);
				providers.put(hash, provider);
			}
		}
		return provider;
	}

	/**
	 * @param clazz
	 *            return all the provider's id for the given class.
	 */
	@Override
	public <T extends Provider> Set<String> listProviderIds(Class<T> clazz) {
		return factory.getAllProviderIds(clazz);
	}

	/**
	 * @param clazz
	 *            return all the provider's class for the given class.
	 */
	@Override
	public <T extends Provider> Set<T> getAllProviders(Class<T> clazz) {
		Set<T> providers = new HashSet<T>();
		for (String id : listProviderIds(clazz)) {
			providers.add(getProvider(clazz, id));
		}
		return providers;
	}

	/**
	 * @return the current RepeidSessionFactory.
	 */
	@Override
	public KeycloakSessionFactory getKeycloakSessionFactory() {
		return factory;
	}

	/**
	 * @return RealmProvider
	 */
	@Override
	public RealmProvider realms() {
		if (realmProvider == null) {
			realmProvider = getRealmProvider();
		}
		return realmProvider;
	}

	private RealmProvider getRealmProvider() {
		if (factory.getDefaultProvider(CacheRealmProvider.class) != null) {
			return getProvider(CacheRealmProvider.class);
		} else {
			return getProvider(RealmProvider.class);
		}
	}

	/**
	 * This method is invoked on destroy this method.
	 */
	@Override
	public void close() {
		for (Provider p : providers.values()) {
			try {
				p.close();
			} catch (Exception e) {
			}
		}
		for (Provider p : closable) {
			try {
				p.close();
			} catch (Exception e) {
			}
		}
	}

}