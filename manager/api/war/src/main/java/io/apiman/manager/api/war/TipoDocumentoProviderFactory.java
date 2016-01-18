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
package io.apiman.manager.api.war;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;

import org.repeid.manager.api.model.TipoDocumentoProvider;
import org.repeid.manager.api.mongo.models.MongoTipoDocumentoProvider;

import io.apiman.manager.api.core.config.Config;

@ApplicationScoped
public interface TipoDocumentoProviderFactory {

	@Produces
	public static TipoDocumentoProvider provideTipoDocumentoProvider(Config.Scope config,
			@New MongoTipoDocumentoProvider jpaProvider, @New MongoTipoDocumentoProvider mongoProvider) {
		if (config.get("provider").equalsIgnoreCase("jpa")) {
			return jpaProvider;
		} else if (config.get("provider").equalsIgnoreCase("mongo")) {
			return mongoProvider;
		} else {
			throw new RuntimeException("Unknown tipoDocumentoProvider type: " + config.get("provider"));
		}
	}

}
