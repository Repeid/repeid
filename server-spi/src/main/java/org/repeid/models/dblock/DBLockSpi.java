package org.repeid.models.dblock;

import org.repeid.provider.Provider;
import org.repeid.provider.ProviderFactory;
import org.repeid.provider.Spi;

public class DBLockSpi implements Spi {

    @Override
    public boolean isInternal() {
        return true;
    }

    @Override
    public String getName() {
        return "dblock";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return DBLockProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return DBLockProviderFactory.class;
    }
}
