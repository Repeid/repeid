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

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public class RepeidConfig {

	private static ConfigProvider configProvider = new SystemPropertiesConfigProvider();

	public static void init(ConfigProvider configProvider) {
		RepeidConfig.configProvider = configProvider;
	}

	public static String getAdminRealm() {
		return configProvider.scope("admin").get("realm", "master");
	}

	public static String getProvider(String spi) {
		String provider = configProvider.getProvider(spi);
		if (provider == null || provider.trim().equals("")) {
			return null;
		} else {
			return provider;
		}
	}

	public static Scope scope(String... scope) {
		return configProvider.scope(scope);
	}

}
