package org.repeid.models;

import io.apiman.manager.api.core.exceptions.StorageException;

public interface Model {

    void commit() throws StorageException;

}
