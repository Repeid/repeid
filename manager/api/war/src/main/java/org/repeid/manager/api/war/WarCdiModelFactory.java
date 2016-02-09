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
package org.repeid.manager.api.war;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.repeid.manager.api.core.config.Config;
import org.repeid.manager.api.model.AccionistaProvider;
import org.repeid.manager.api.model.PersonaJuridicaProvider;
import org.repeid.manager.api.model.PersonaNaturalProvider;
import org.repeid.manager.api.model.TipoDocumentoProvider;
import org.repeid.manager.api.model.provider.ProviderType;
import org.repeid.manager.api.model.provider.ProviderType.Type;

/**
 * Attempt to create producer methods for CDI beans.
 *
 * @author carlosthe19916@sistcoop.com
 */
@ApplicationScoped
public class WarCdiModelFactory {

	private String realmProvider = Config.getProvider("realm");

	@Produces
	public TipoDocumentoProvider getTipoDocumentoProvider(@ProviderType(Type.JPA) TipoDocumentoProvider jpa,
			@ProviderType(Type.MONGO) TipoDocumentoProvider mongo) {
		if (realmProvider.equalsIgnoreCase("jpa")) {
			return jpa;
		} else if (realmProvider.equalsIgnoreCase("mongo")) {
			return mongo;
		} else {
			throw new RuntimeException("Provider type desconocido");
		}
	}

	@Produces
	public PersonaNaturalProvider getPersonaNaturalProvider(@ProviderType(Type.JPA) PersonaNaturalProvider jpa,
			@ProviderType(Type.MONGO) PersonaNaturalProvider mongo) {
		if (realmProvider.equalsIgnoreCase("jpa")) {
			return jpa;
		} else if (realmProvider.equalsIgnoreCase("mongo")) {
			return mongo;
		} else {
			throw new RuntimeException("Provider type desconocido");
		}
	}

	@Produces
	public PersonaJuridicaProvider getPersonaJuridicaProvider(@ProviderType(Type.JPA) PersonaJuridicaProvider jpa,
			@ProviderType(Type.MONGO) PersonaJuridicaProvider mongo) {
		if (realmProvider.equalsIgnoreCase("jpa")) {
			return jpa;
		} else if (realmProvider.equalsIgnoreCase("mongo")) {
			return mongo;
		} else {
			throw new RuntimeException("Provider type desconocido");
		}
	}

	@Produces
	public AccionistaProvider getAccionistaProvider(@ProviderType(Type.JPA) AccionistaProvider jpa,
			@ProviderType(Type.MONGO) AccionistaProvider mongo) {
		if (realmProvider.equalsIgnoreCase("jpa")) {
			return jpa;
		} else if (realmProvider.equalsIgnoreCase("mongo")) {
			return mongo;
		} else {
			throw new RuntimeException("Provider type desconocido");
		}
	}

}
