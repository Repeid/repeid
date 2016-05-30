package org.repeid.models.cache;

import org.repeid.provider.Provider;
import org.repeid.provider.ProviderFactory;
import org.repeid.provider.Spi;

public class CacheNaturalPersonProviderSpi implements Spi {

    @Override
    public boolean isInternal() {
        return true;
    }

    @Override
    public String getName() {
        return "naturalPersonCache";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return CacheNaturalPersonProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return CacheNaturalPersonProviderFactory.class;
    }
}
