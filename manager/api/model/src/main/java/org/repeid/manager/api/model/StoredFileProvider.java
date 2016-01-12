package org.repeid.manager.api.model;

import org.repeid.manager.api.model.provider.Provider;

public interface StoredFileProvider extends Provider {

	StoredFileModel findById(String id);

	StoredFileModel create(byte[] file, StoreConfigurationModel configuration);

	byte[] download(String fileId);

	boolean remove(StoredFileModel storedFile);

}
