package org.repeid.models;

import org.repeid.provider.ProviderFactory;

public interface StoredFileProviderFactory extends ProviderFactory<StoredFileProvider> {

    StoredFileProvider get(StorageConfigurationModel storageConfigurationModel);

}
