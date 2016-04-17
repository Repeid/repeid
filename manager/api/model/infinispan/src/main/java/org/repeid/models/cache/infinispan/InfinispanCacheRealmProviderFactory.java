package org.repeid.models.cache.infinispan;

import org.infinispan.Cache;
import org.jboss.logging.Logger;
import org.repeid.Config.Scope;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;
import org.repeid.models.cache.CacheOrganizationProvider;
import org.repeid.models.cache.CacheOrganizationProviderFactory;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class InfinispanCacheRealmProviderFactory implements CacheOrganizationProviderFactory {

    @Override
    public CacheOrganizationProvider create(RepeidSession session) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void init(Scope config) {
        // TODO Auto-generated method stub

    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getId() {
        return "default";
    }

    @Override
    public void postInit(RepeidSessionFactory factory) {
        // TODO Auto-generated method stub

    }

}
