package org.repeid.models.cache.infinispan;

import org.repeid.Config;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;
import org.repeid.models.cache.CacheNaturalPersonProvider;
import org.repeid.models.cache.CacheNaturalPersonProviderFactory;

public class InfinispanCacheNaturalPersonProviderFactory implements CacheNaturalPersonProviderFactory {


    @Override
    public CacheNaturalPersonProvider create(RepeidSession session) {
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
