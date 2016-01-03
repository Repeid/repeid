package org.repeid.models;

import javax.ejb.Local;

import org.repeid.provider.Provider;

@Local
public interface StoredFileProvider extends Provider {

	StoredFileModel findById(String id);

	StoredFileModel create(byte[] file, StoreConfigurationModel configuration);

	byte[] download(String fileId);

	boolean remove(StoredFileModel storedFile);

}
