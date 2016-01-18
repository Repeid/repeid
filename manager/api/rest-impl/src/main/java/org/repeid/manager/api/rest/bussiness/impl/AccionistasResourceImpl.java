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

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.repeid.manager.api.beans.representations.AccionistaRepresentation;
import org.repeid.manager.api.rest.bussiness.AccionistaResource;
import org.repeid.manager.api.rest.bussiness.AccionistasResource;
import org.repeid.manager.api.rest.bussiness.PersonasJuridicasResource;

@Stateless
public class AccionistasResourceImpl implements AccionistasResource {

    @PathParam(PersonasJuridicasResource.PERSONA_JURIDICA_ID)
    private String personaJuridicaId;

    // @Inject
    // private TipoDocumentoProvider tipoDocumentoProvider;

    // @Inject
    // private PersonaJuridicaProvider personaJuridicaProvider;

    // @Inject
    // private PersonaNaturalProvider personaNaturalProvider;

    // @Inject
    // private AccionistaProvider accionistaProvider;

    @Inject
    private AccionistaResource accionistaResource;

    // @Inject
    // private RepresentationToModel representationToModel;

    @Context
    private UriInfo uriInfo;

    /*
     * private PersonaJuridicaModel getPersonaJuridicaModel() throws
     * StorageException { return
     * personaJuridicaProvider.findById(personaJuridicaId); }
     */

    @Override
    public AccionistaResource accionista(String accionista) {
        return accionistaResource;
    }

    @Override
    public Response create(AccionistaRepresentation rep) {
        /*
         * PersonaJuridicaModel personaJuridica = getPersonaJuridicaModel();
         * 
         * PersonaNaturalRepresentation personaNaturalRep =
         * rep.getPersonaNatural(); TipoDocumentoModel tipoDocumento =
         * tipoDocumentoProvider
         * .findByAbreviatura(personaNaturalRep.getTipoDocumento());
         * PersonaNaturalModel personaNatural =
         * personaNaturalProvider.findByTipoNumeroDocumento(tipoDocumento,
         * personaNaturalRep.getNumeroDocumento());
         * 
         * // Check duplicated personaJuridica y personaNatural if
         * (accionistaProvider.findByPersonaJuridicaNatural(personaJuridica,
         * personaNatural) != null) { return ErrorResponse.exists(
         * "Accionista existe con la misma persona juridica y natural"); }
         * 
         * AccionistaModel model = representationToModel.createAccionista(rep,
         * personaJuridica, personaNatural, accionistaProvider);
         * 
         * return
         * Response.created(uriInfo.getAbsolutePathBuilder().path(model.getId())
         * .build()) .header("Access-Control-Expose-Headers", "Location")
         * .entity(ModelToRepresentation.toRepresentation(model)).build();
         */
        return null;
    }

    @Override
    public List<AccionistaRepresentation> getAll() {
        /*
         * List<AccionistaModel> results =
         * accionistaProvider.getAll(getPersonaJuridicaModel());
         * List<AccionistaRepresentation> representations = new ArrayList<>();
         * for (AccionistaModel model : results) {
         * representations.add(ModelToRepresentation.toRepresentation(model)); }
         * return representations;
         */
        return null;
    }

}
