/*******************************************************************************
 * Repeid, Home of Professional Open Source
 *
 * Copyright 2015 Sistcoop, Inc. and/or its affiliates.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.repeid.manager.api.model.box;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.repeid.manager.api.model.StoreConfigurationModel;
import org.repeid.manager.api.model.provider.Provider;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.drive.Drive;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public interface GoogleDriveProvider extends Provider {

	Credential getGoogleDriveService(StoreConfigurationModel storeConfiguration);

	String upload(Drive service, java.io.File UPLOAD_FILE, String title, String mimeType, String description,
			String... parents) throws GeneralSecurityException, IOException;

}
