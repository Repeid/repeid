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

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.repeid.manager.api.model.exceptions.ModelException;
import org.repeid.manager.api.model.provider.KeycloakSession;
import org.repeid.manager.api.rest.messages.MessagesProvider;
import org.repeid.manager.api.rest.services.ServicesLogger;

/**
 * @author <a href="mailto:leonardo.zanivan@gmail.com">Leonardo Zanivan</a>
 */
@Provider
public class ModelExceptionMapper implements ExceptionMapper<ModelException> {

	private static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

	@Context
	private KeycloakSession session;

	@Override
	public Response toResponse(ModelException ex) {
		String message = session.getProvider(MessagesProvider.class, "admin").getMessage(ex.getMessage(),
				ex.getParameters());

		logger.error(message, ex);
		return ErrorResponse.error(message, Response.Status.BAD_REQUEST);
	}
}
