package org.repeid.services.resources.admin;

import java.util.List;

import javax.ws.rs.core.Response;

import org.repeid.manager.api.beans.representations.TipoDocumentoRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchCriteriaRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchResultsRepresentation;
import org.repeid.services.resources.admin.DocumentResource;
import org.repeid.services.resources.admin.DocumentsResource;

public class DocumentsResourceImpl implements DocumentsResource {

    @Override
    public DocumentResource tipoDocumento(String tipoDocumentoId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response create(TipoDocumentoRepresentation rep) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TipoDocumentoRepresentation> search(String abreviatura, String denominacion, String tipoPersona,
                                                    Boolean estado, String filterText, Integer firstResult, Integer maxResults) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SearchResultsRepresentation<TipoDocumentoRepresentation> search(SearchCriteriaRepresentation criteria) {
        // TODO Auto-generated method stub
        return null;
    }

}
