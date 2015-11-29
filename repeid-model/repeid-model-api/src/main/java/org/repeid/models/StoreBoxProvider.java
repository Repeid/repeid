package org.repeid.models;

import java.io.IOException;

import javax.ejb.Local;

import org.repeid.provider.Provider;

@Local
public interface StoreBoxProvider extends Provider {

    StoreBoxModel getByStoreConfiguration(StoreConfigurationModel storeConfiguration) throws IOException;

}
