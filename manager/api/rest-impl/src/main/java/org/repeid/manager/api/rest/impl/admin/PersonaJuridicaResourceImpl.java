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
package org.repeid.manager.api.rest.impl.admin;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.PathParam;

import org.repeid.manager.api.beans.representations.PersonaJuridicaRepresentation;
import org.repeid.manager.api.beans.representations.security.PermissionType;
import org.repeid.manager.api.model.PersonaJuridicaModel;
import org.repeid.manager.api.model.exceptions.ModelException;
import org.repeid.manager.api.model.exceptions.ModelReadOnlyException;
import org.repeid.manager.api.model.system.RepeidSession;
import org.repeid.manager.api.model.utils.ModelToRepresentation;
import org.repeid.manager.api.rest.admin.PersonaJuridicaResource;
import org.repeid.manager.api.rest.admin.PersonasJuridicasResource;
import org.repeid.manager.api.rest.contract.exceptions.SystemErrorException;
import org.repeid.manager.api.rest.impl.admin.AccionistasResource;
import org.repeid.manager.api.rest.managers.PersonaJuridicaManager;
import org.repeid.manager.api.rest.util.ExceptionFactory;
import org.repeid.manager.api.security.ISecurityContext;

@RequestScoped
public class PersonaJuridicaResourceImpl implements PersonaJuridicaResource {

	@PathParam(PersonasJuridicasResource.PERSONA_JURIDICA_ID)
	private String personaJuridicaId;

	@Inject
	private RepeidSession session;

	@Inject
	private ISecurityContext auth;

	@Inject
	private AccionistasResource accionistasResource;

	private PersonaJuridicaModel getPersonaJuridicaModel() {
		return session.personasJuridicas().findById(personaJuridicaId);
	}

	@Override
	public PersonaJuridicaRepresentation toRepresentation() {
		if (!auth.hasPermission(PermissionType.personaView))
			throw ExceptionFactory.notAuthorizedException();

		PersonaJuridicaModel personaJuridica = getPersonaJuridicaModel();
		if (personaJuridica == null) {
			throw ExceptionFactory.personaJuridicaNotFoundException(personaJuridicaId);
		}
		return ModelToRepresentation.toRepresentation(personaJuridica);
	}

	@Override
	public void update(PersonaJuridicaRepresentation rep) {
		if (!auth.hasPermission(PermissionType.personaEdit))
			throw ExceptionFactory.notAuthorizedException();

		PersonaJuridicaModel personaJuridica = getPersonaJuridicaModel();
		if (personaJuridica == null) {
			throw ExceptionFactory.personaJuridicaNotFoundException(personaJuridicaId);
		}

		boolean result = new PersonaJuridicaManager(session).update(personaJuridica, rep);
		if (!result) {
			throw ExceptionFactory.tipoDocumentoLockedException(personaJuridicaId);
		}
	}

	@Override
	public void remove() {
		if (!auth.hasPermission(PermissionType.documentoAdmin))
			throw ExceptionFactory.notAuthorizedException();

		PersonaJuridicaModel personaJuridica = getPersonaJuridicaModel();
		if (personaJuridica == null) {
			throw ExceptionFactory.personaJuridicaNotFoundException(personaJuridicaId);
		}

		try {
			session.personasJuridicas().remove(personaJuridica);

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

	@Override
	public AccionistasResource accionistas() {
		return accionistasResource;
	}

}
