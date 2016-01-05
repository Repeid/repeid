package org.repeid.manager.api.rest.bussiness.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.repeid.manager.api.rest.bussiness.TipoDocumentoResource;
import org.repeid.manager.api.rest.bussiness.TiposDocumentoResource;
import org.repeid.manager.api.rest.contract.exceptions.InvalidSearchCriteriaException;
import org.repeid.manager.api.rest.contract.exceptions.NotAuthorizedException;
import org.repeid.manager.api.rest.contract.exceptions.SystemErrorException;
import org.repeid.manager.api.rest.contract.exceptions.TipoDocumentoAlreadyExistsException;
import org.repeid.manager.api.rest.impl.util.ExceptionFactory;
import org.repeid.manager.api.rest.impl.util.SearchCriteriaUtil;
import org.repeid.manager.api.security.ISecurityContext;
import org.repeid.models.ModelDuplicateException;
import org.repeid.models.TipoDocumentoModel;
import org.repeid.models.TipoDocumentoProvider;
import org.repeid.models.search.SearchCriteriaModel;
import org.repeid.models.search.SearchResultsModel;
import org.repeid.models.utils.ModelToRepresentation;
import org.repeid.models.utils.RepresentationToModel;
import org.repeid.representations.idm.TipoDocumentoRepresentation;
import org.repeid.representations.idm.search.SearchCriteriaRepresentation;
import org.repeid.representations.idm.search.SearchResultsRepresentation;
import org.repeid.representations.idm.security.PermissionType;

import io.apiman.manager.api.core.exceptions.StorageException;

@Stateless
public class TiposDocumentoResourceImpl implements TiposDocumentoResource {

    @Inject
    private TipoDocumentoProvider tipoDocumentoProvider;

    @Inject
    private RepresentationToModel representationToModel;

    @Inject
    private TipoDocumentoResource tipoDocumentoResource;

    @Inject
    private ISecurityContext iSecurityContext;

    @Context
    private UriInfo uriInfo;

    @Override
    public TipoDocumentoResource tipoDocumento(String tipoDocumentoId) {
        return tipoDocumentoResource;
    }

    @Override
    public Response create(TipoDocumentoRepresentation rep)
            throws TipoDocumentoAlreadyExistsException, NotAuthorizedException {

        if (!iSecurityContext.hasPermission(PermissionType.tipoDocumentoAdmin))
            throw ExceptionFactory.notAuthorizedException();

        try {
            // Check duplicated abreviatura
            if (tipoDocumentoProvider.findByAbreviatura(rep.getAbreviatura()) != null) {
                throw ExceptionFactory.tipoDocumentoAlreadyExistsException(rep.getAbreviatura());
            }
            try {
                TipoDocumentoModel model = representationToModel.createTipoDocumento(rep,
                        tipoDocumentoProvider);
                return Response.created(uriInfo.getAbsolutePathBuilder().path(model.getId()).build())
                        .header("Access-Control-Expose-Headers", "Location")
                        .entity(ModelToRepresentation.toRepresentation(model)).build();
            } catch (ModelDuplicateException e) {
                throw ExceptionFactory.tipoDocumentoAlreadyExistsException(rep.getAbreviatura());
            }
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public List<TipoDocumentoRepresentation> search(String abreviatura, String denominacion,
            String tipoPersona, Boolean estado, String filterText, Integer firstResult, Integer maxResults)
                    throws NotAuthorizedException {

        if (!iSecurityContext.hasPermission(PermissionType.tipoDocumentoView))
            throw ExceptionFactory.notAuthorizedException();

        try {
            firstResult = firstResult != null ? firstResult : -1;
            maxResults = maxResults != null ? maxResults : -1;

            List<TipoDocumentoModel> models;
            if (filterText != null) {
                models = tipoDocumentoProvider.search(filterText.trim(), firstResult, maxResults);
            } else if (denominacion != null || abreviatura != null || tipoPersona != null || estado != null) {
                Map<String, Object> attributes = new HashMap<String, Object>();
                if (abreviatura != null) {
                    attributes.put(TipoDocumentoModel.ABREVIATURA, abreviatura);
                }
                if (denominacion != null) {
                    attributes.put(TipoDocumentoModel.DENOMINACION, denominacion);
                }
                if (tipoPersona != null) {
                    attributes.put(TipoDocumentoModel.TIPO_PERSONA, tipoPersona);
                }
                if (estado != null) {
                    attributes.put(TipoDocumentoModel.ESTADO, estado);
                }
                models = tipoDocumentoProvider.searchByAttributes(attributes, firstResult, maxResults);
            } else {
                models = tipoDocumentoProvider.getAll(firstResult, maxResults);
            }

            List<TipoDocumentoRepresentation> results = new ArrayList<>();
            for (TipoDocumentoModel model : models) {
                results.add(ModelToRepresentation.toRepresentation(model));
            }
            return results;
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public SearchResultsRepresentation<TipoDocumentoRepresentation> search(
            SearchCriteriaRepresentation criteria)
                    throws InvalidSearchCriteriaException, NotAuthorizedException {

        if (!iSecurityContext.hasPermission(PermissionType.tipoDocumentoView))
            throw ExceptionFactory.notAuthorizedException();

        try {
            SearchCriteriaUtil.validateSearchCriteria(criteria);
            SearchCriteriaModel criteriaModel = SearchCriteriaUtil.getSearchCriteriaModel(criteria);

            // extract filterText
            String filterText = criteria.getFilterText();

            // search
            SearchResultsModel<TipoDocumentoModel> models = null;
            if (filterText == null) {
                models = tipoDocumentoProvider.search(criteriaModel);
            } else {
                models = tipoDocumentoProvider.search(criteriaModel, filterText);
            }

            SearchResultsRepresentation<TipoDocumentoRepresentation> result = new SearchResultsRepresentation<>();
            List<TipoDocumentoRepresentation> items = new ArrayList<>();
            for (TipoDocumentoModel model : models.getModels()) {
                items.add(ModelToRepresentation.toRepresentation(model));
            }
            result.setItems(items);
            result.setTotalSize(models.getTotalSize());
            return result;
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

}
