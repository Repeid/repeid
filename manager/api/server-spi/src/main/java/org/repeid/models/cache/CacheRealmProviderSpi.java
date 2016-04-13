package org.repeid.models.cache;

import org.repeid.provider.Provider;
import org.repeid.provider.ProviderFactory;
import org.repeid.provider.Spi;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class CacheRealmProviderSpi implements Spi {

    @Override
    public String getName() {
        return "realmCache";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return CacheRealmProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return CacheRealmProviderFactory.class;
    }
}
