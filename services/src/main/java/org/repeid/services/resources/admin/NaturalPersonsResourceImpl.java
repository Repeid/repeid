package org.repeid.services.resources.admin;

import java.util.List;

import javax.ws.rs.core.Response;

import org.repeid.representations.idm.NaturalPersonRepresentation;
import org.repeid.representations.idm.search.SearchCriteriaRepresentation;
import org.repeid.representations.idm.search.SearchResultsRepresentation;
import org.repeid.services.resources.admin.NaturalPersonResource;
import org.repeid.services.resources.admin.NaturalPersonsResource;

public class NaturalPersonsResourceImpl implements NaturalPersonsResource {

    @Override
    public NaturalPersonResource personaNatural(String personaNaturalId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response create(NaturalPersonRepresentation rep) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<NaturalPersonRepresentation> search(String tipoDocumento, String numeroDocumento,
                                                     String apellidoPaterno, String apellidoMaterno, String nombres, String filterText,
                                                     Integer firstResult, Integer maxResults) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SearchResultsRepresentation<NaturalPersonRepresentation> search(
            SearchCriteriaRepresentation criteria) {
        // TODO Auto-generated method stub
        return null;
    }

}
