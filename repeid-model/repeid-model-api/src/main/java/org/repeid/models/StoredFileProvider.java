package org.repeid.models;

import java.io.File;

import javax.ejb.Local;

import org.repeid.provider.Provider;

@Local
public interface StoredFileProvider extends Provider {

    StoredFileModel findById(String id);

    StoredFileModel create(File file);

    boolean remove(String id);

}
