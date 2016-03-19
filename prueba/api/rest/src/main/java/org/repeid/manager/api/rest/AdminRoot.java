/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.repeid.manager.api.rest;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.repeid.manager.api.rest.admin.MaestroResource;
import org.repeid.manager.api.rest.admin.PersonasResource;
import org.repeid.manager.api.rest.admin.TiposDocumentoResource;

/**
 * Root resource for admin console and admin REST API
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
@Path("/admin")
public interface AdminRoot {

	/**
	 * Base Path to tipoDocumento admin REST interface
	 *
	 * @param headers
	 * @return
	 */
	@Path("tiposDocumento")
	public TiposDocumentoResource getTiposDocumentoResource(@Context final HttpHeaders headers);

	/**
	 * Base Path to persona admin REST interface
	 *
	 * @param headers
	 * @return
	 */
	@Path("personas")
	public PersonasResource getPersonasResource(@Context final HttpHeaders headers);

	/**
	 * Base Path to utils admin REST interface
	 *
	 * @param headers
	 * @return
	 */
	@Path("utils")
	public MaestroResource getUtilsResource(@Context final HttpHeaders headers);

	/**
	 * General information about the server
	 *
	 * @param headers
	 * @return
	 */
	@Path("serverinfo")
	public ServerInfoAdminResource getServerInfo(@Context final HttpHeaders headers);

}
