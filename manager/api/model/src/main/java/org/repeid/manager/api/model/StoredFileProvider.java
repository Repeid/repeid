package org.repeid.manager.api.model;

import javax.ejb.Local;

import org.repeid.manager.api.model.provider.Provider;

@Local
public interface StoredFileProvider extends Provider {

	StoredFileModel findById(String id);

	StoredFileModel create(byte[] file, StoreConfigurationModel configuration);

	byte[] download(String fileId);

	boolean remove(StoredFileModel storedFile);

}
