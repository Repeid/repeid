package org.repeid.cluster;

import org.repeid.provider.Provider;
import org.repeid.provider.ProviderFactory;
import org.repeid.provider.Spi;

public class ClusterSpi implements Spi {

    @Override
    public boolean isInternal() {
        return true;
    }

    @Override
    public String getName() {
        return "cluster";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return ClusterProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return ClusterProviderFactory.class;
    }
}
