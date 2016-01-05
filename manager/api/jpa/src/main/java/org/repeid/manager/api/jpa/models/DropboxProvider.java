package org.repeid.manager.api.jpa.models;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

public class DropboxProvider {

    @SuppressWarnings("unused")
    private DbxClient dbxClient;

    public DropboxProvider(DbxClient dbxClient) {
        this.dbxClient = dbxClient;
    }

    public DbxEntry.File upload(byte[] file) {
        // TODO Auto-generated method stub
        return null;
    }

}
