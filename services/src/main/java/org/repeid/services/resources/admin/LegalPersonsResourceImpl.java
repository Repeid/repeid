package org.repeid.services.resources.admin;

import java.util.List;

import javax.ws.rs.core.Response;

import org.repeid.representations.idm.LegalPersonRepresentation;
import org.repeid.representations.idm.search.SearchCriteriaRepresentation;
import org.repeid.representations.idm.search.SearchResultsRepresentation;
import org.repeid.services.resources.admin.LegalPersonResource;
import org.repeid.services.resources.admin.LegalPersonsResource;

public class LegalPersonsResourceImpl implements LegalPersonsResource {

    @Override
    public LegalPersonResource personaJuridica(String personaJuridicaId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response create(LegalPersonRepresentation rep) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LegalPersonRepresentation> search(String tipoDocumento, String numeroDocumento,
                                                      String razonSocial, String nombreComercial, String filterText, Integer firstResult,
                                                      Integer maxResults) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SearchResultsRepresentation<LegalPersonRepresentation> search(
            SearchCriteriaRepresentation criteria) {
        // TODO Auto-generated method stub
        return null;
    }

}
