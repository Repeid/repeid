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

package org.repeid.manager.api.model.provider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.repeid.manager.api.core.config.Config;
import org.repeid.manager.api.model.provider.ProviderType.Type;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

@ApplicationScoped
public class ProviderProducer {

	private String realmProvider = Config.getProvider("realm");

	@Produces
	public <T extends Provider> T getProvider(@ProviderType(Type.JPA) T jpa, @ProviderType(Type.MONGO) T mongo) {
		if (realmProvider.equalsIgnoreCase("jpa")) {
			return jpa;
		} else if (realmProvider.equalsIgnoreCase("mongo")) {
			return mongo;
		} else {
			throw new RuntimeException("Provider type desconocido");
		}
	}

}
