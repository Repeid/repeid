package org.repeid.models.cache.infinispan;

import org.infinispan.Cache;
import org.jboss.logging.Logger;
import org.repeid.Config;
import org.repeid.connections.infinispan.InfinispanConnectionProvider;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;
import org.repeid.models.cache.CacheOrganizationProvider;
import org.repeid.models.cache.CacheOrganizationProviderFactory;
import org.repeid.models.cache.infinispan.entities.Revisioned;

public class InfinispanCacheOrganizationProviderFactory implements CacheOrganizationProviderFactory {

	private static final Logger log = Logger.getLogger(InfinispanCacheOrganizationProviderFactory.class);

	protected volatile OrganizationCacheManager organizationCache;

	@Override
	public CacheOrganizationProvider create(RepeidSession session) {
		lazyInit(session);
		return new OrganizationCacheSession(organizationCache, session);
	}

	private void lazyInit(RepeidSession session) {
		if (organizationCache == null) {
			synchronized (this) {
				if (organizationCache == null) {
					Cache<String, Revisioned> cache = session.getProvider(InfinispanConnectionProvider.class)
							.getCache(InfinispanConnectionProvider.ORGANIZATION_CACHE_NAME);
					Cache<String, Long> revisions = session.getProvider(InfinispanConnectionProvider.class)
							.getCache(InfinispanConnectionProvider.VERSION_CACHE_NAME);
					organizationCache = new OrganizationCacheManager(cache, revisions);
				}
			}
		}
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
