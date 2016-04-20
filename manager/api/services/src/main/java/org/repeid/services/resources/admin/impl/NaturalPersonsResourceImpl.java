package org.repeid.services.resources.admin.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.repeid.manager.api.beans.representations.PersonaNaturalRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchCriteriaRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchResultsRepresentation;
import org.repeid.services.resources.admin.NaturalPersonResource;
import org.repeid.services.resources.admin.NaturalPersonsResource;

public class NaturalPersonsResourceImpl implements NaturalPersonsResource {

    @Override
    public NaturalPersonResource personaNatural(String personaNaturalId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response create(PersonaNaturalRepresentation rep) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<PersonaNaturalRepresentation> search(String tipoDocumento, String numeroDocumento,
                                                     String apellidoPaterno, String apellidoMaterno, String nombres, String filterText,
                                                     Integer firstResult, Integer maxResults) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SearchResultsRepresentation<PersonaNaturalRepresentation> search(
            SearchCriteriaRepresentation criteria) {
        // TODO Auto-generated method stub
        return null;
    }

}
