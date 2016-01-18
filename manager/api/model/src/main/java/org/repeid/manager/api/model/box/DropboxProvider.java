package org.repeid.manager.api.model.box;

import org.repeid.manager.api.model.StoreConfigurationModel;
import org.repeid.manager.api.model.provider.Provider;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

public interface DropboxProvider extends Provider {

	DbxClient getDropBoxClient(StoreConfigurationModel storeConfiguration);

	DbxEntry.File upload(DbxClient dbxClient, byte[] file);

	DbxEntry.File download(DbxClient dbxClient, String fileId);

}
