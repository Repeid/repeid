package org.repeid.models.jpa;

import java.io.File;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Default;
import javax.inject.Named;

import org.repeid.models.StoredFileModel;
import org.repeid.models.StoredFileProvider;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
@Default
@Named
@Stateless
@Local(StoredFileProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaStoredFileLocalProvider implements StoredFileProvider {

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

    @Override
    public StoredFileModel findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StoredFileModel create(File file) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean remove(String id) {
        // TODO Auto-generated method stub
        return false;
    }

}
