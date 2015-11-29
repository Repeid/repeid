package org.repeid.services.resources.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.repeid.admin.client.resource.PersonaNaturalResource;
import org.repeid.admin.client.resource.PersonasNaturalesResource;
import org.repeid.models.ModelDuplicateException;
import org.repeid.models.PersonaNaturalModel;
import org.repeid.models.PersonaNaturalProvider;
import org.repeid.models.TipoDocumentoModel;
import org.repeid.models.TipoDocumentoProvider;
import org.repeid.models.search.SearchCriteriaFilterOperator;
import org.repeid.models.search.SearchCriteriaModel;
import org.repeid.models.search.SearchResultsModel;
import org.repeid.models.utils.ModelToRepresentation;
import org.repeid.models.utils.RepresentationToModel;
import org.repeid.representations.idm.PersonaNaturalRepresentation;
import org.repeid.representations.idm.search.OrderByRepresentation;
import org.repeid.representations.idm.search.PagingRepresentation;
import org.repeid.representations.idm.search.SearchCriteriaFilterRepresentation;
import org.repeid.representations.idm.search.SearchCriteriaRepresentation;
import org.repeid.representations.idm.search.SearchResultsRepresentation;
import org.repeid.services.ErrorResponse;

@Stateless
public class PersonasNaturalesResourceImpl implements PersonasNaturalesResource {

    @Inject
    private PersonaNaturalProvider personaNaturalProvider;

    @Inject
    private TipoDocumentoProvider tipoDocumentoProvider;

    @Inject
    private RepresentationToModel representationToModel;

    @Inject
    private PersonaNaturalResource personaNaturalResource;

    @Context
    private UriInfo uriInfo;

    @Override
    public PersonaNaturalResource personaNatural(String personaNatural) {
        return personaNaturalResource;
    }

    @Override
    public Response create(PersonaNaturalRepresentation rep) {
        TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider
                .findByAbreviatura(rep.getTipoDocumento());

        // Check duplicated tipo y numero de documento
        if (personaNaturalProvider.findByTipoNumeroDocumento(tipoDocumentoModel,
                rep.getNumeroDocumento()) != null) {
            return ErrorResponse.exists("Persona Natural existe con el mismo tipo y numero de documento");
        }

        try {
            PersonaNaturalModel model = representationToModel.createPersonaNatural(rep, tipoDocumentoModel,
                    personaNaturalProvider);
            return Response.created(uriInfo.getAbsolutePathBuilder().path(model.getId()).build())
                    .header("Access-Control-Expose-Headers", "Location")
                    .entity(ModelToRepresentation.toRepresentation(model)).build();
        } catch (ModelDuplicateException e) {
            return ErrorResponse.exists("Persona Natural existe con el mismo tipo y numero de documento");
        }
    }

    @Override
    public List<PersonaNaturalRepresentation> search(String tipoDocumento, String numeroDocumento,
            String apellidoPaterno, String apellidoMaterno, String nombres, String filterText,
            Integer firstResult, Integer maxResults) {
        int firstResultParam = firstResult != null ? firstResult : -1;
        int maxResultsParam = maxResults != null ? maxResults : -1;

        List<PersonaNaturalRepresentation> results = new ArrayList<PersonaNaturalRepresentation>();
        List<PersonaNaturalModel> personaNaturalModels;
        if (filterText != null) {
            personaNaturalModels = personaNaturalProvider.search(filterText.trim(), firstResult, maxResults);
        } else if (tipoDocumento != null || numeroDocumento != null) {
            TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.findByAbreviatura(tipoDocumento);
            PersonaNaturalModel personaNatural = personaNaturalProvider
                    .findByTipoNumeroDocumento(tipoDocumentoModel, numeroDocumento);
            personaNaturalModels = new ArrayList<>();
            personaNaturalModels.add(personaNatural);
        } else if (apellidoPaterno != null || apellidoMaterno != null || nombres != null
                || numeroDocumento != null) {
            Map<String, String> attributes = new HashMap<String, String>();
            if (apellidoPaterno != null) {
                attributes.put(PersonaNaturalModel.APELLIDO_PATERNO, apellidoPaterno);
            }
            if (apellidoMaterno != null) {
                attributes.put(PersonaNaturalModel.APELLIDO_MATERNO, apellidoMaterno);
            }
            if (nombres != null) {
                attributes.put(PersonaNaturalModel.NOMBRES, nombres);
            }
            if (numeroDocumento != null) {
                attributes.put(PersonaNaturalModel.NUMERO_DOCUMENTO, numeroDocumento);
            }
            personaNaturalModels = personaNaturalProvider.searchByAttributes(attributes, firstResult,
                    maxResults);
        } else {
            personaNaturalModels = personaNaturalProvider.getAll(firstResultParam, maxResultsParam);
        }

        for (PersonaNaturalModel model : personaNaturalModels) {
            results.add(ModelToRepresentation.toRepresentation(model));
        }
        return results;
    }

    @Override
    public SearchResultsRepresentation<PersonaNaturalRepresentation> search(
            SearchCriteriaRepresentation criteria) {
        SearchCriteriaModel criteriaModel = new SearchCriteriaModel();

        // set filter and order
        for (SearchCriteriaFilterRepresentation filter : criteria.getFilters()) {
            criteriaModel.addFilter(filter.getName(), filter.getValue(),
                    SearchCriteriaFilterOperator.valueOf(filter.getOperator().toString()));
        }
        for (OrderByRepresentation order : criteria.getOrders()) {
            criteriaModel.addOrder(order.getName(), order.isAscending());
        }

        // set paging
        PagingRepresentation paging = criteria.getPaging();
        if (paging == null) {
            paging = new PagingRepresentation();
            paging.setPage(1);
            paging.setPageSize(20);
        }
        criteriaModel.setPageSize(paging.getPageSize());
        criteriaModel.setPage(paging.getPage());

        // extract filterText
        String filterText = criteria.getFilterText();

        // search
        SearchResultsModel<PersonaNaturalModel> results = null;
        if (filterText == null) {
            results = personaNaturalProvider.search(criteriaModel);
        } else {
            results = personaNaturalProvider.search(criteriaModel, filterText);
        }

        SearchResultsRepresentation<PersonaNaturalRepresentation> rep = new SearchResultsRepresentation<>();
        List<PersonaNaturalRepresentation> items = new ArrayList<>();
        for (PersonaNaturalModel model : results.getModels()) {
            items.add(ModelToRepresentation.toRepresentation(model));
        }
        rep.setItems(items);
        rep.setTotalSize(results.getTotalSize());
        return rep;
    }

}
