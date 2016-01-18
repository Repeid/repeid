package org.repeid.manager.api.model.box;

import javax.ejb.Stateless;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxEntry.File;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

@Stateless
public class DefaultDropboxProvider implements DropboxProvider {

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public DbxEntry.File upload(DbxClient dbxClient, byte[] file) {
		// File storage
		/*
		 * dbxClient. DbxEntry.File fileEntity =
		 * dbxClient.uploadFile(targetPath, writeMode, numBytes, writer);
		 */
		return null;
	}

	@Override
	public File download(DbxClient dbxClient, String fileId) {
		// TODO Auto-generated method stub
		return null;
	}

}
