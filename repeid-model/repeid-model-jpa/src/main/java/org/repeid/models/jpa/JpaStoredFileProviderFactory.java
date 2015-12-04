package org.repeid.models.jpa;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.repeid.models.StoreConfigurationModel;
import org.repeid.models.StoredFileProvider;
import org.repeid.models.StoredFileProviderFactory;
import org.repeid.models.enums.StoreFileProviderName;
import org.repeid.models.jpa.qualifiers.Dropbox;
import org.repeid.models.jpa.qualifiers.GoogleDrive;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
@Named
@Stateless
@Local(StoredFileProviderFactory.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaStoredFileProviderFactory implements StoredFileProviderFactory {

    @Inject
    private StoredFileProvider localProvider;

    @Inject
    @Dropbox
    private StoredFileProvider dropboxProvider;

    @Inject
    @GoogleDrive
    private StoredFileProvider googleDriveProvider;

    @Override
    public StoredFileProvider get(StoreConfigurationModel storageConfiguration) {
        StoreFileProviderName providerName = storageConfiguration.getProviderName();
        switch (providerName) {
        case localhost:
            return localProvider;
        case Dropbox:
            return dropboxProvider;
        case GoogleDrive:
            return googleDriveProvider;
        default:
            return null;
        }
    }

}
