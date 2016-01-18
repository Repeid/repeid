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
package org.repeid.manager.api.rest.bussiness.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.beans.representations.PersonaJuridicaRepresentation;
import org.repeid.manager.api.beans.representations.security.PermissionType;
import org.repeid.manager.api.model.PersonaJuridicaModel;
import org.repeid.manager.api.model.PersonaJuridicaProvider;
import org.repeid.manager.api.model.utils.ModelToRepresentation;
import org.repeid.manager.api.rest.bussiness.AccionistasResource;
import org.repeid.manager.api.rest.bussiness.PersonaJuridicaResource;
import org.repeid.manager.api.rest.bussiness.PersonasJuridicasResource;
import org.repeid.manager.api.rest.contract.exceptions.NotAuthorizedException;
import org.repeid.manager.api.rest.contract.exceptions.PersonaJuridicaNotFoundException;
import org.repeid.manager.api.rest.contract.exceptions.SystemErrorException;
import org.repeid.manager.api.rest.impl.util.ExceptionFactory;
import org.repeid.manager.api.rest.managers.PersonaJuridicaManager;
import org.repeid.manager.api.security.ISecurityContext;

@Stateless
public class PersonaJuridicaResourceImpl implements PersonaJuridicaResource {

    @PathParam(PersonasJuridicasResource.PERSONA_JURIDICA_ID)
    private String personaJuridicaId;

    @Inject
    private PersonaJuridicaProvider personaJuridicaProvider;

    @Inject
    private PersonaJuridicaManager personaJuridicaManager;

    @Inject
    private AccionistasResource accionistasResource;

    @Inject
    private ISecurityContext iSecurityContext;

    private PersonaJuridicaModel getPersonaJuridicaModel() throws StorageException {
        return personaJuridicaProvider.findById(personaJuridicaId);
    }

    @Override
    public PersonaJuridicaRepresentation toRepresentation()
            throws PersonaJuridicaNotFoundException, NotAuthorizedException {
        if (!iSecurityContext.hasPermission(PermissionType.personaView))
            throw ExceptionFactory.notAuthorizedException();

        try {
            PersonaJuridicaModel personaJuridica = getPersonaJuridicaModel();
            if (personaJuridica == null) {
                throw ExceptionFactory.personaJuridicaNotFoundException(personaJuridicaId);
            }
            return ModelToRepresentation.toRepresentation(personaJuridica);
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public void update(PersonaJuridicaRepresentation rep)
            throws PersonaJuridicaNotFoundException, NotAuthorizedException {
        if (!iSecurityContext.hasPermission(PermissionType.personaEdit))
            throw ExceptionFactory.notAuthorizedException();

        try {
            PersonaJuridicaModel personaJuridica = getPersonaJuridicaModel();
            if (personaJuridica == null) {
                throw ExceptionFactory.personaJuridicaNotFoundException(personaJuridicaId);
            }
            personaJuridicaManager.update(personaJuridica, rep);
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public Response remove() throws PersonaJuridicaNotFoundException, NotAuthorizedException {
        if (!iSecurityContext.hasPermission(PermissionType.documentoAdmin))
            throw ExceptionFactory.notAuthorizedException();

        try {
            PersonaJuridicaModel personaJuridica = getPersonaJuridicaModel();
            if (personaJuridica == null) {
                throw ExceptionFactory.personaJuridicaNotFoundException(personaJuridicaId);
            }

            boolean removed = personaJuridicaProvider.remove(personaJuridica);
            if (removed) {
                return Response.noContent().build();
            } else {
                throw ExceptionFactory.personaJuridicaLockedException(personaJuridicaId);
            }
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public AccionistasResource accionistas() {
        return accionistasResource;
    }

}
