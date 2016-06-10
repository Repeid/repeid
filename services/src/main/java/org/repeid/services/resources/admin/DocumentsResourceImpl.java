package org.repeid.services.resources.admin;

import java.util.List;

import javax.ws.rs.core.Response;

import org.repeid.representations.idm.DocumentRepresentation;
import org.repeid.representations.idm.search.SearchCriteriaRepresentation;
import org.repeid.representations.idm.search.SearchResultsRepresentation;
import org.repeid.services.resources.admin.DocumentResource;
import org.repeid.services.resources.admin.DocumentsResource;

public class DocumentsResourceImpl implements DocumentsResource {

    @Override
    public DocumentResource tipoDocumento(String tipoDocumentoId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response create(DocumentRepresentation rep) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DocumentRepresentation> search(String abreviatura, String denominacion, String tipoPersona,
                                                    Boolean estado, String filterText, Integer firstResult, Integer maxResults) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SearchResultsRepresentation<DocumentRepresentation> search(SearchCriteriaRepresentation criteria) {
        // TODO Auto-generated method stub
        return null;
    }

}
