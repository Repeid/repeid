package org.repeid.manager.api.model.box;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.repeid.manager.api.model.provider.Provider;

import com.google.api.services.drive.Drive;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

public interface GoogleDriveProvider extends Provider {

	public String upload(Drive service, java.io.File UPLOAD_FILE, String title, String mimeType, String description,
			String... parents) throws GeneralSecurityException, IOException;

}
