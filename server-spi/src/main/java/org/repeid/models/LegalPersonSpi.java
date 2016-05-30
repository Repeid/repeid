package org.repeid.models;

import org.repeid.provider.Provider;
import org.repeid.provider.ProviderFactory;
import org.repeid.provider.Spi;

public class LegalPersonSpi implements Spi {

    @Override
    public String getName() {
        return "legalPerson";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return LegalPersonProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return LegalPersonProviderFactory.class;
    }

    @Override
    public boolean isInternal() {
        return true;
    }

}
