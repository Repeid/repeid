package org.repeid.models;

import java.util.List;

import javax.ejb.Local;

import org.repeid.provider.Provider;

@Local
public interface StorageConfigurationProvider extends Provider {

    StorageConfigurationModel findById(String id);

    StorageConfigurationModel findByDenominacion(String denominacion);

    StorageConfigurationModel getDefaultStoreConfiguration();

    StorageConfigurationModel create(String appKey, String denominacion);

    boolean remove(StorageConfigurationModel storeConfiguration);

    List<StorageConfigurationModel> getAll();

}
