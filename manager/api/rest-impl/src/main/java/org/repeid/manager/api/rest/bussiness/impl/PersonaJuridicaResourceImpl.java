package org.repeid.manager.api.rest.bussiness.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.repeid.manager.api.rest.bussiness.AccionistasResource;
import org.repeid.manager.api.rest.bussiness.PersonaJuridicaResource;
import org.repeid.manager.api.rest.managers.PersonaJuridicaManager;
import org.repeid.models.PersonaJuridicaModel;
import org.repeid.models.PersonaJuridicaProvider;
import org.repeid.models.utils.ModelToRepresentation;
import org.repeid.representations.idm.PersonaJuridicaRepresentation;
import org.repeid.services.ErrorResponse;

@Stateless
public class PersonaJuridicaResourceImpl implements PersonaJuridicaResource {

	@PathParam("idPersonaJuridica")
	private String idPersonaJuridica;

	@Inject
	private PersonaJuridicaProvider personaJuridicaProvider;

	@Inject
	private PersonaJuridicaManager personaJuridicaManager;

	@Inject
	private AccionistasResource accionistasResource;

	private PersonaJuridicaModel getPersonaJuridicaModel() {
		return personaJuridicaProvider.findById(idPersonaJuridica);
	}

	@Override
	public PersonaJuridicaRepresentation toRepresentation() {
		PersonaJuridicaRepresentation rep = ModelToRepresentation.toRepresentation(getPersonaJuridicaModel());
		if (rep != null) {
			return rep;
		} else {
			throw new NotFoundException("Persona Juridica no encontrada");
		}
	}

	@Override
	public void update(PersonaJuridicaRepresentation rep) {
		personaJuridicaManager.update(getPersonaJuridicaModel(), rep);
	}

	@Override
	public Response remove() {
		PersonaJuridicaModel personaJuridica = getPersonaJuridicaModel();
		if (personaJuridica == null) {
			throw new NotFoundException("PersonaJuridica no encontrado");
		}
		boolean removed = personaJuridicaProvider.remove(personaJuridica);
		if (removed) {
			return Response.noContent().build();
		} else {
			return ErrorResponse.error("Persona Juridica no pudo ser eliminada", Response.Status.BAD_REQUEST);
		}
	}

	@Override
	public AccionistasResource accionistas() {
		return accionistasResource;
	}

}
