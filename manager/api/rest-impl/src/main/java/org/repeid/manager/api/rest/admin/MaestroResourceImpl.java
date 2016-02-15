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
package org.repeid.manager.api.rest.admin;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

import org.repeid.manager.api.model.enums.EstadoCivil;
import org.repeid.manager.api.model.enums.Sexo;
import org.repeid.manager.api.model.enums.TipoEmpresa;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.api.model.provider.KeycloakSession;
import org.repeid.manager.api.rest.admin.MaestroResource;
import org.repeid.manager.api.rest.services.ServicesLogger;

public class MaestroResourceImpl implements MaestroResource {

	protected static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

	protected RealmModel realm;

	private RealmAuth auth;

	private AdminEventBuilder adminEvent;

	@Context
	protected ClientConnection clientConnection;

	@Context
	protected UriInfo uriInfo;

	@Context
	protected KeycloakSession session;

	@Context
	protected HttpHeaders headers;

	public UsersResource(RealmModel realm, RealmAuth auth, TokenManager tokenManager, AdminEventBuilder adminEvent) {
        this.auth = auth;
        this.realm = realm;
        this.adminEvent = adminEvent;

        auth.init(RealmAuth.Resource.USER);
    }

	@Override
	public List<String> getAllTipoPersonas() {
		TipoPersona[] enums = TipoPersona.values();

		List<String> representations = new ArrayList<>();
		for (int i = 0; i < enums.length; i++) {
			representations.add(enums[i].toString());
		}
		return representations;
	}

	@Override
	public List<String> getAllEstadosCiviles() {
		EstadoCivil[] enums = EstadoCivil.values();

		List<String> representations = new ArrayList<>();
		for (int i = 0; i < enums.length; i++) {
			representations.add(enums[i].toString());
		}
		return representations;
	}

	@Override
	public List<String> getAllSexos() {
		Sexo[] enums = Sexo.values();

		List<String> representations = new ArrayList<>();
		for (int i = 0; i < enums.length; i++) {
			representations.add(enums[i].toString());
		}
		return representations;
	}

	@Override
	public List<String> getAllTiposEmpresa() {
		TipoEmpresa[] enums = TipoEmpresa.values();

		List<String> representations = new ArrayList<>();
		for (int i = 0; i < enums.length; i++) {
			representations.add(enums[i].toString());
		}
		return representations;
	}

}
