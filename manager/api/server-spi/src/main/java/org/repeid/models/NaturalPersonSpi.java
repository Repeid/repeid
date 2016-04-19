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
        return OrganizationProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return OrganizationProviderFactory.class;
    }

    @Override
    public boolean isInternal() {
        // TODO Auto-generated method stub
        return false;
    }

}
