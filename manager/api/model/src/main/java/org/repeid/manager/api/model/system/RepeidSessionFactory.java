package org.repeid.manager.api.model.system;

import org.repeid.manager.api.model.provider.Provider;
import org.repeid.manager.api.model.provider.ProviderEventManager;
import org.repeid.manager.api.model.provider.ProviderFactory;

import java.util.List;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface RepeidSessionFactory extends ProviderEventManager {
	RepeidSession create();

	<T extends Provider> ProviderFactory<T> getProviderFactory(Class<T> clazz);

	<T extends Provider> ProviderFactory<T> getProviderFactory(Class<T> clazz, String id);

	List<ProviderFactory> getProviderFactories(Class<? extends Provider> clazz);

	long getServerStartupTimestamp();

	void close();
}
