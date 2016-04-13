package org.repeid.models;

import java.util.List;

import org.repeid.provider.Provider;
import org.repeid.provider.ProviderFactory;

public interface RepeidSessionFactory {
	RepeidSession create();

	<T extends Provider> ProviderFactory<T> getProviderFactory(Class<T> clazz);

	<T extends Provider> ProviderFactory<T> getProviderFactory(Class<T> clazz, String id);

	List<ProviderFactory> getProviderFactories(Class<? extends Provider> clazz);

	void close();
}