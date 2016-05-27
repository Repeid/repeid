package org.repeid.connections.jpa.updater;

import org.repeid.provider.Provider;
import org.repeid.provider.ProviderFactory;
import org.repeid.provider.Spi;

public class JpaUpdaterSpi implements Spi {

    @Override
    public boolean isInternal() {
        return true;
    }

    @Override
    public String getName() {
        return "connectionsJpaUpdater";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return JpaUpdaterProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return JpaUpdaterProviderFactory.class;
    }

}
