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

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.repeid.manager.api.beans.representations.PersonaNaturalRepresentation;
import org.repeid.manager.api.beans.representations.StoredFileRepresentation;
import org.repeid.manager.api.beans.representations.security.PermissionType;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.exceptions.ModelException;
import org.repeid.manager.api.model.exceptions.ModelReadOnlyException;
import org.repeid.manager.api.model.system.RepeidSession;
import org.repeid.manager.api.model.utils.ModelToRepresentation;
import org.repeid.manager.api.rest.admin.PersonaNaturalResource;
import org.repeid.manager.api.rest.admin.PersonasNaturalesResource;
import org.repeid.manager.api.rest.contract.exceptions.SystemErrorException;
import org.repeid.manager.api.rest.impl.util.ExceptionFactory;
import org.repeid.manager.api.rest.managers.PersonaNaturalManager;
import org.repeid.manager.api.security.ISecurityContext;

@RequestScoped
public class PersonaNaturalResourceImpl implements PersonaNaturalResource {

	@PathParam(PersonasNaturalesResource.PERSONA_NATURAL_ID)
	private String personaNaturalId;

	@Inject
	private RepeidSession session;

	@Inject
	private ISecurityContext auth;

	private PersonaNaturalModel getPersonaNaturalModel() {
		return session.personasNaturales().findById(personaNaturalId);
	}

	@Override
	public PersonaNaturalRepresentation toRepresentation() {
		if (!auth.hasPermission(PermissionType.personaView))
			throw ExceptionFactory.notAuthorizedException();

		PersonaNaturalModel personaNatural = getPersonaNaturalModel();
		if (personaNatural == null) {
			throw ExceptionFactory.personaNaturalNotFoundException(personaNaturalId);
		}
		return ModelToRepresentation.toRepresentation(personaNatural);
	}

	@Override
	public void update(PersonaNaturalRepresentation rep) {
		if (!auth.hasPermission(PermissionType.documentoEdit))
			throw ExceptionFactory.notAuthorizedException();

		PersonaNaturalModel personaNatural = getPersonaNaturalModel();
		if (personaNatural == null) {
			throw ExceptionFactory.personaNaturalNotFoundException(personaNaturalId);
		}

		boolean result = new PersonaNaturalManager(session).update(personaNatural, rep);
		if (!result) {
			throw ExceptionFactory.tipoDocumentoLockedException(personaNaturalId);
		}
	}

	@Override
	public Response getFoto() {
		return null;
	}

	@Override
	public Response getFirma() {
		return null;
	}

	@Override
	public StoredFileRepresentation setFoto(MultipartFormDataInput input) {
		return null;
	}

	@Override
	public StoredFileRepresentation setFirma(MultipartFormDataInput input) {
		return null;
	}

	@Override
	public void remove() {
		if (!auth.hasPermission(PermissionType.documentoAdmin))
			throw ExceptionFactory.notAuthorizedException();

		PersonaNaturalModel personaNatural = getPersonaNaturalModel();
		if (personaNatural == null) {
			throw ExceptionFactory.personaNaturalNotFoundException(personaNaturalId);
		}

		try {
			session.personasNaturales().remove(personaNatural);

			if (session.getTransaction().isActive()) {
				session.getTransaction().commit();
			}
		} catch (ModelReadOnlyException e) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().setRollbackOnly();
			}
			throw new SystemErrorException(e);
		} catch (ModelException e) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().setRollbackOnly();
			}
			throw new SystemErrorException(e);
		}
	}

}
