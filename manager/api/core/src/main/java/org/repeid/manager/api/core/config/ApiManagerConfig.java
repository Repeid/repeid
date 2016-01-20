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
package org.repeid.manager.api.core.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.repeid.common.config.ConfigFactory;

/**
 * Configuration object for the API Manager.
 *
 * @author eric.wittmann@redhat.com
 */
public abstract class ApiManagerConfig {

	/**
	 * Storage
	 */
	public static final String MANAGER_STORAGE_TYPE = "repeid-manager.storage.type"; //$NON-NLS-1$

	/**
	 * Security
	 */
	public static final String MANAGER_SECURITY_CONTEXT_TYPE = "repeid-manager.security-context.type"; //$NON-NLS-1$

	/**
	 * org.apache.commons.configuration.Configuration
	 */
	private final Configuration config;

	/**
	 * Constructor.
	 */
	public ApiManagerConfig() {
		config = loadProperties();
	}

	/**
	 * Loads the config properties.
	 */
	protected Configuration loadProperties() {
		return ConfigFactory.createConfig();
	}

	public String getSecurityContextType() {
		return config.getString(MANAGER_SECURITY_CONTEXT_TYPE, "default"); //$NON-NLS-1$
	}

	/**
	 * @return the configured storage type
	 */
	public String getStorageType() {
		return config.getString(MANAGER_STORAGE_TYPE, "jpa"); //$NON-NLS-1$
	}

	/**
	 * Gets a map of properties prefixed by the given string.
	 */
	protected Map<String, String> getPrefixedProperties(String prefix) {
		Map<String, String> rval = new HashMap<>();
		Iterator<String> keys = getConfig().getKeys();
		while (keys.hasNext()) {
			String key = keys.next();
			if (key.startsWith(prefix)) {
				String value = getConfig().getString(key);
				key = key.substring(prefix.length());
				rval.put(key, value);
			}
		}
		return rval;
	}

	/**
	 * @return the configuration
	 */
	public Configuration getConfig() {
		return config;
	}

}
