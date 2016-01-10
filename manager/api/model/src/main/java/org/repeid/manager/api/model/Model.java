package org.repeid.manager.api.model;

import org.repeid.manager.api.beans.exceptions.StorageException;

public interface Model {

    void commit() throws StorageException;

}
