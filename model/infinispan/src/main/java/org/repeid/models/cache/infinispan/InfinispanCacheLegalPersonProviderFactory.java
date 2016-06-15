package org.repeid.models.cache.infinispan;

import org.infinispan.Cache;
import org.jboss.logging.Logger;
import org.repeid.Config;
import org.repeid.connections.infinispan.InfinispanConnectionProvider;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;
import org.repeid.models.cache.CacheLegalPersonProvider;
import org.repeid.models.cache.CacheLegalPersonProviderFactory;
import org.repeid.models.cache.infinispan.entities.Revisioned;

public class InfinispanCacheLegalPersonProviderFactory implements CacheLegalPersonProviderFactory {


	private static final Logger log = Logger.getLogger(InfinispanCacheLegalPersonProviderFactory.class);

	protected volatile LegalPersonCacheManager legalPersonCache;

	@Override
	public CacheLegalPersonProvider create(RepeidSession session) {
		lazyInit(session);
		return new LegalPersonCacheSession(legalPersonCache, session);
	}

	private void lazyInit(RepeidSession session) {
		if (legalPersonCache == null) {
			synchronized (this) {
				if (legalPersonCache == null) {
					Cache<String, Revisioned> cache = session.getProvider(InfinispanConnectionProvider.class)
							.getCache(InfinispanConnectionProvider.NATURALPERSON_CACHE_NAME);
					Cache<String, Long> revisions = session.getProvider(InfinispanConnectionProvider.class)
							.getCache(InfinispanConnectionProvider.VERSION_CACHE_NAME);
					legalPersonCache = new LegalPersonCacheManager(cache, revisions);
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
