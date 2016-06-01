package org.repeid.models.cache.infinispan;

import org.repeid.Config;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;
import org.repeid.models.cache.CacheOrganizationProvider;
import org.repeid.models.cache.CacheOrganizationProviderFactory;

public class InfinispanCacheOrganizationProviderFactory implements CacheOrganizationProviderFactory {

	@Override
	public CacheOrganizationProvider create(RepeidSession session) {
		return null;
	}

	@Override
	public void init(Config.Scope config) {

	}

	@Override
	public void postInit(RepeidSessionFactory factory) {

	}

	@Override
	public void close() {

	}

	@Override
	public String getId() {
		return "default";
	}

}
