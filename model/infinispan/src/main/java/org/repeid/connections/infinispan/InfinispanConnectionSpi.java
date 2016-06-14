package org.repeid.connections.infinispan;

import org.repeid.provider.Provider;
import org.repeid.provider.ProviderFactory;
import org.repeid.provider.Spi;

public class InfinispanConnectionSpi implements Spi {

    @Override
    public boolean isInternal() {
        return true;
    }

    @Override
    public String getName() {
        return "connectionsInfinispan";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return InfinispanConnectionProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return InfinispanConnectionProviderFactory.class;
    }

}
