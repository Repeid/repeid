package org.repeid.services.resources.admin.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.repeid.manager.api.beans.representations.PersonaJuridicaRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchCriteriaRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchResultsRepresentation;
import org.repeid.services.resources.admin.LegalPersonResource;
import org.repeid.services.resources.admin.LegalPersonsResource;

public class LegalPersonsResourceImpl implements LegalPersonsResource {

    @Override
    public LegalPersonResource personaJuridica(String personaJuridicaId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response create(PersonaJuridicaRepresentation rep) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<PersonaJuridicaRepresentation> search(String tipoDocumento, String numeroDocumento,
            String razonSocial, String nombreComercial, String filterText, Integer firstResult,
            Integer maxResults) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SearchResultsRepresentation<PersonaJuridicaRepresentation> search(
            SearchCriteriaRepresentation criteria) {
        // TODO Auto-generated method stub
        return null;
    }

}
