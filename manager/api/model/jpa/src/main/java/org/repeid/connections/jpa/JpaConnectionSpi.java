package org.repeid.connections.jpa;

import org.repeid.provider.Provider;
import org.repeid.provider.ProviderFactory;
import org.repeid.provider.Spi;

public class JpaConnectionSpi implements Spi {

    @Override
    public boolean isInternal() {
        return true;
    }

    @Override
    public String getName() {
        return "connectionsJpa";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return JpaConnectionProvider.class;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return JpaConnectionProviderFactory.class;
    }

}
