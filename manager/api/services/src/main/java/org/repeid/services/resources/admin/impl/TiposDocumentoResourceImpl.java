package org.repeid.services.resources.admin.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.repeid.manager.api.beans.representations.TipoDocumentoRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchCriteriaRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchResultsRepresentation;
import org.repeid.services.resources.admin.TipoDocumentoResource;
import org.repeid.services.resources.admin.TiposDocumentoResource;

public class TiposDocumentoResourceImpl implements TiposDocumentoResource {

	@Override
	public TipoDocumentoResource tipoDocumento(String tipoDocumentoId) {
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
