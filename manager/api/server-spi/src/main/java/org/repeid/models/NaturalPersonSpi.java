package org.repeid.models;

import org.repeid.provider.Provider;
import org.repeid.provider.ProviderFactory;
import org.repeid.provider.Spi;

public class NaturalPersonSpi implements Spi {

    @Override
    public String getName() {
        return "naturalPerson";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return NaturalPersonProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return NaturalPersonProviderFactory.class;
    }

    @Override
    public boolean isInternal() {
        return true;
    }

}
