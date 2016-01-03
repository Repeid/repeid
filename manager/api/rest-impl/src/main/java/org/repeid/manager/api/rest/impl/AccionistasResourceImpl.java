package org.repeid.manager.api.rest.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.repeid.manager.api.rest.AccionistaResource;
import org.repeid.manager.api.rest.AccionistasResource;
import org.repeid.models.AccionistaModel;
import org.repeid.models.AccionistaProvider;
import org.repeid.models.PersonaJuridicaModel;
import org.repeid.models.PersonaJuridicaProvider;
import org.repeid.models.PersonaNaturalModel;
import org.repeid.models.PersonaNaturalProvider;
import org.repeid.models.TipoDocumentoModel;
import org.repeid.models.TipoDocumentoProvider;
import org.repeid.models.utils.ModelToRepresentation;
import org.repeid.models.utils.RepresentationToModel;
import org.repeid.representations.idm.AccionistaRepresentation;
import org.repeid.representations.idm.PersonaNaturalRepresentation;
import org.repeid.services.ErrorResponse;

@Stateless
public class AccionistasResourceImpl implements AccionistasResource {

	@PathParam("idPersonaJuridica")
	private String idPersonaJuridica;

	@Inject
	private TipoDocumentoProvider tipoDocumentoProvider;

	@Inject
	private PersonaJuridicaProvider personaJuridicaProvider;

	@Inject
	private PersonaNaturalProvider personaNaturalProvider;

	@Inject
	private AccionistaProvider accionistaProvider;

	@Inject
	private AccionistaResource accionistaResource;

	@Inject
	private RepresentationToModel representationToModel;

	@Context
	private UriInfo uriInfo;

	private PersonaJuridicaModel getPersonaJuridicaModel() {
		return personaJuridicaProvider.findById(idPersonaJuridica);
	}

	@Override
	public AccionistaResource accionista(String accionista) {
		return accionistaResource;
	}

	@Override
	public Response create(AccionistaRepresentation rep) {
		PersonaJuridicaModel personaJuridica = getPersonaJuridicaModel();

		PersonaNaturalRepresentation personaNaturalRep = rep.getPersonaNatural();
		TipoDocumentoModel tipoDocumento = tipoDocumentoProvider
				.findByAbreviatura(personaNaturalRep.getTipoDocumento());
		PersonaNaturalModel personaNatural = personaNaturalProvider.findByTipoNumeroDocumento(tipoDocumento,
				personaNaturalRep.getNumeroDocumento());

		// Check duplicated personaJuridica y personaNatural
		if (accionistaProvider.findByPersonaJuridicaNatural(personaJuridica, personaNatural) != null) {
			return ErrorResponse.exists("Accionista existe con la misma persona juridica y natural");
		}

		AccionistaModel model = representationToModel.createAccionista(rep, personaJuridica, personaNatural,
				accionistaProvider);

		return Response.created(uriInfo.getAbsolutePathBuilder().path(model.getId()).build())
				.header("Access-Control-Expose-Headers", "Location")
				.entity(ModelToRepresentation.toRepresentation(model)).build();
	}

	@Override
	public List<AccionistaRepresentation> getAll() {
		List<AccionistaModel> results = accionistaProvider.getAll(getPersonaJuridicaModel());
		List<AccionistaRepresentation> representations = new ArrayList<>();
		for (AccionistaModel model : results) {
			representations.add(ModelToRepresentation.toRepresentation(model));
		}
		return representations;
	}

}
