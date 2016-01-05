package org.repeid.manager.api.rest.bussiness.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.repeid.manager.api.core.exceptions.StorageException;
import org.repeid.manager.api.core.representations.idm.PersonaJuridicaRepresentation;
import org.repeid.manager.api.core.representations.idm.security.PermissionType;
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
        if (!iSecurityContext.hasPermission(PermissionType.tipoDocumentoAdmin))
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
