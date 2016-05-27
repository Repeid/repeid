package org.repeid.migration;

import org.repeid.provider.Provider;
import org.repeid.provider.ProviderFactory;
import org.repeid.provider.Spi;

public class MigrationSpi implements Spi {

    @Override
    public boolean isInternal() {
        return true;
    }

    @Override
    public String getName() {
        return "migration";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return MigrationProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return MigrationProviderFactory.class;
    }
}
