package org.repeid.models.jpa;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.repeid.models.StorageConfigurationProvider;
import org.repeid.models.StoredFileProvider;
import org.repeid.models.StoredFileProviderFactory;
import org.repeid.models.enums.StoredFileProviderName;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
@Named
@Stateless
@Local(StoredFileProviderFactory.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaStoredFileProviderFactory implements StoredFileProviderFactory {

    private StoredFileProvider storedFileProvider;

    @Override
    public StoredFileProvider get(StoredFileProviderName providerName) {
        // TODO Auto-generated method stub
        return null;
    }

}
