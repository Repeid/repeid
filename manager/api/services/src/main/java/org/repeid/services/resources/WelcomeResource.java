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
package org.repeid.services.resources;

import org.repeid.Config;
import org.repeid.common.ClientConnection;
import org.repeid.common.util.MimeTypeUtil;
import org.repeid.models.RepeidSession;
import org.repeid.services.ServicesLogger;
import org.repeid.services.managers.ApplianceBootstrap;
import org.repeid.utils.MediaType;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
@Path("/")
public class WelcomeResource {

	private static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

	private static final String KEYCLOAK_STATE_CHECKER = "KEYCLOAK_STATE_CHECKER";

	private boolean bootstrap;

	@Context
	protected HttpHeaders headers;

	@Context
	private UriInfo uriInfo;

	@Context
	private RepeidSession session;

	private String stateChecker;

	public WelcomeResource(boolean bootstrap) {
		this.bootstrap = bootstrap;
	}

	/**
	 * Welcome page of Keycloak
	 *
	 * @return
	 * @throws URISyntaxException
	 */
	@GET
	@Produces(MediaType.TEXT_HTML_UTF_8)
	public Response getWelcomePage() throws URISyntaxException {
		checkBootstrap();

		String requestUri = uriInfo.getRequestUri().toString();
		if (!requestUri.endsWith("/")) {
			return Response.seeOther(new URI(requestUri + "/")).build();
		} else {
			return createWelcomePage(null, null);
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createUser(final MultivaluedMap<String, String> formData) {
		checkBootstrap();

		return null;
	}

	/**
	 * Resources for welcome page
	 *
	 * @param path
	 * @return
	 */
	@GET
	@Path("/welcome-content/{path}")
	@Produces(MediaType.TEXT_HTML_UTF_8)
	public Response getResource(@PathParam("path") String path) {
		return null;
	}

	private Response createWelcomePage(String successMessage, String errorMessage) {
		try {
			return null;
		} catch (Exception e) {
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	private void checkBootstrap() {
		if (bootstrap) {
			bootstrap = new ApplianceBootstrap(session).isNoMasterUser();
		}
	}

}
