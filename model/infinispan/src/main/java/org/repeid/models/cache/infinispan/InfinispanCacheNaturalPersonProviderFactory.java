package org.repeid.models.cache.infinispan;

import org.infinispan.Cache;
import org.jboss.logging.Logger;
import org.repeid.Config;
import org.repeid.connections.infinispan.InfinispanConnectionProvider;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;
import org.repeid.models.cache.CacheNaturalPersonProvider;
import org.repeid.models.cache.CacheNaturalPersonProviderFactory;
import org.repeid.models.cache.infinispan.entities.Revisioned;

public class InfinispanCacheNaturalPersonProviderFactory implements CacheNaturalPersonProviderFactory {

	private static final Logger log = Logger.getLogger(InfinispanCacheNaturalPersonProviderFactory.class);

	protected volatile NaturalPersonCacheManager naturalPersonCache;

	@Override
	public CacheNaturalPersonProvider create(RepeidSession session) {
		lazyInit(session);
		return new NaturalPersonCacheSession(naturalPersonCache, session);
	}

	private void lazyInit(RepeidSession session) {
		if (naturalPersonCache == null) {
			synchronized (this) {
				if (naturalPersonCache == null) {
					Cache<String, Revisioned> cache = session.getProvider(InfinispanConnectionProvider.class)
							.getCache(InfinispanConnectionProvider.NATURALPERSON_CACHE_NAME);
					Cache<String, Long> revisions = session.getProvider(InfinispanConnectionProvider.class)
							.getCache(InfinispanConnectionProvider.VERSION_CACHE_NAME);
					naturalPersonCache = new NaturalPersonCacheManager(cache, revisions);
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
