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

import java.util.Locale;

import javax.ejb.Stateless;

import org.repeid.manager.api.model.StoreConfigurationModel;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxEntry.File;
import com.dropbox.core.DbxRequestConfig;

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
	public DbxClient getDropBoxClient(StoreConfigurationModel storeConfiguration) {
		DbxRequestConfig config = new DbxRequestConfig("Repeid", Locale.getDefault().toString());
		return new DbxClient(config, storeConfiguration.getToken());
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
