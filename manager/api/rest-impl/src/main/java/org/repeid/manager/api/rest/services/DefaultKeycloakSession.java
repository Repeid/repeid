/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.repeid.manager.api.rest.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.repeid.manager.api.model.TipoDocumentoProvider;
import org.repeid.manager.api.model.cache.CacheTipoDocumentoProvider;
import org.repeid.manager.api.model.provider.KeycloakSession;
import org.repeid.manager.api.model.provider.KeycloakSessionFactory;
import org.repeid.manager.api.model.provider.Provider;
import org.repeid.manager.api.model.provider.ProviderFactory;
import org.repeid.manager.api.model.system.KeycloakContext;
import org.repeid.manager.api.model.system.KeycloakTransactionManager;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class DefaultKeycloakSession implements KeycloakSession {

	private final DefaultKeycloakSessionFactory factory;
	private final Map<Integer, Provider> providers = new HashMap<>();
	private final List<Provider> closable = new LinkedList<Provider>();
	private final DefaultKeycloakTransactionManager transactionManager;
	private TipoDocumentoProvider model;
	private KeycloakContext context;

	public DefaultKeycloakSession(DefaultKeycloakSessionFactory factory) {
		this.factory = factory;
		this.transactionManager = new DefaultKeycloakTransactionManager();
		// federationManager = new UserFederationManager(this);
		context = new DefaultKeycloakContext(this);
	}

	@Override
	public KeycloakContext getContext() {
		return context;
	}

	private TipoDocumentoProvider getTipoDocumentoProvider() {
		CacheTipoDocumentoProvider cache = getProvider(CacheTipoDocumentoProvider.class);
		if (cache != null) {
			return cache;
		} else {
			return getProvider(TipoDocumentoProvider.class);
		}
	}

	@Override
	public void enlistForClose(Provider provider) {
		closable.add(provider);
	}

	@Override
	public KeycloakTransactionManager getTransaction() {
		return transactionManager;
	}

	@Override
	public KeycloakSessionFactory getKeycloakSessionFactory() {
		return factory;
	}

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

	public <T extends Provider> Set<String> listProviderIds(Class<T> clazz) {
		return factory.getAllProviderIds(clazz);
	}

	@Override
	public <T extends Provider> Set<T> getAllProviders(Class<T> clazz) {
		Set<T> providers = new HashSet<T>();
		for (String id : listProviderIds(clazz)) {
			providers.add(getProvider(clazz, id));
		}
		return providers;
	}

	@Override
	public TipoDocumentoProvider tipoDocumentos() {
		if (model == null) {
			model = getTipoDocumentoProvider();
		}
		return model;
	}

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
