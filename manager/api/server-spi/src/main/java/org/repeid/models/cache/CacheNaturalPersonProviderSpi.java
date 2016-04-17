package org.repeid.models.cache;

import org.repeid.provider.Provider;
import org.repeid.provider.ProviderFactory;
import org.repeid.provider.Spi;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class CacheNaturalPersonProviderSpi implements Spi {

    @Override
    public String getName() {
        return "realmCache";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return CacheOrganizationProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return CacheOrganizationProviderFactory.class;
    }

    @Override
    public boolean isInternal() {
        // TODO Auto-generated method stub
        return false;
    }
}
