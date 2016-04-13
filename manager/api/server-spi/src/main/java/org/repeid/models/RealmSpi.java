package org.repeid.models;

import org.repeid.provider.Provider;
import org.repeid.provider.ProviderFactory;
import org.repeid.provider.Spi;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class RealmSpi implements Spi {

    @Override
    public String getName() {
        return "realm";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return RealmProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return RealmProviderFactory.class;
    }

}
