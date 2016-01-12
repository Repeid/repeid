package org.repeid.manager.api.model;

import java.util.List;

import org.repeid.manager.api.model.provider.Provider;

public interface StoreConfigurationProvider extends Provider {

	StoreConfigurationModel findById(String id);

	StoreConfigurationModel findByDenominacion(String denominacion);

	StoreConfigurationModel getDefaultStoreConfiguration();

	StoreConfigurationModel create(String appKey, String denominacion);

	boolean remove(StoreConfigurationModel storeConfiguration);

	List<StoreConfigurationModel> getAll();

}
