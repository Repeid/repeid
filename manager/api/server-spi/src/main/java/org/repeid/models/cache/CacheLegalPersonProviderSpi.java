package org.repeid.models.cache;

import org.repeid.provider.Provider;
import org.repeid.provider.ProviderFactory;
import org.repeid.provider.Spi;

public class CacheLegalPersonProviderSpi implements Spi {

    @Override
    public boolean isInternal() {
        return true;
    }

    @Override
    public String getName() {
        return "legalPersonCache";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return CacheLegalPersonProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return CacheLegalPersonProviderFactory.class;
    }
}
