package org.repeid.connections.infinispan;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;

public class DefaultInfinispanConnectionProvider implements InfinispanConnectionProvider {

    private EmbeddedCacheManager cacheManager;

    public DefaultInfinispanConnectionProvider(EmbeddedCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) {
        return cacheManager.getCache(name);
    }

    @Override
    public void close() {
    }

}
