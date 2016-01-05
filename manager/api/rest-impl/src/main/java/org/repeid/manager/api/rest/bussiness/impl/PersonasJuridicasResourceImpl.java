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

import org.repeid.manager.api.rest.bussiness.PersonaJuridicaResource;
import org.repeid.manager.api.rest.bussiness.PersonasJuridicasResource;
import org.repeid.manager.api.rest.contract.exceptions.InvalidSearchCriteriaException;
import org.repeid.manager.api.rest.contract.exceptions.NotAuthorizedException;
import org.repeid.manager.api.rest.contract.exceptions.PersonaJuridicaAlreadyExistsException;
import org.repeid.manager.api.rest.contract.exceptions.SystemErrorException;
import org.repeid.manager.api.rest.impl.util.ExceptionFactory;
import org.repeid.manager.api.rest.impl.util.SearchCriteriaUtil;
import org.repeid.models.ModelDuplicateException;
import org.repeid.models.PersonaJuridicaModel;
import org.repeid.models.PersonaJuridicaProvider;
import org.repeid.models.PersonaNaturalModel;
import org.repeid.models.PersonaNaturalProvider;
import org.repeid.models.TipoDocumentoModel;
import org.repeid.models.TipoDocumentoProvider;
import org.repeid.models.search.SearchCriteriaModel;
import org.repeid.models.search.SearchResultsModel;
import org.repeid.models.utils.ModelToRepresentation;
import org.repeid.models.utils.RepresentationToModel;
import org.repeid.representations.idm.PersonaJuridicaRepresentation;
import org.repeid.representations.idm.PersonaNaturalRepresentation;
import org.repeid.representations.idm.search.SearchCriteriaRepresentation;
import org.repeid.representations.idm.search.SearchResultsRepresentation;
import org.repeid.representations.idm.security.PermissionType;

import io.apiman.manager.api.core.exceptions.StorageException;
import io.apiman.manager.api.security.ISecurityContext;

@Stateless
public class PersonasJuridicasResourceImpl implements PersonasJuridicasResource {

    @Inject
    private RepresentationToModel representationToModel;

    @Inject
    private TipoDocumentoProvider tipoDocumentoProvider;

    @Inject
    private PersonaNaturalProvider personaNaturalProvider;

    @Inject
    private PersonaJuridicaProvider personaJuridicaProvider;

    @Inject
    private PersonaJuridicaResource personaJuridicaResource;

    @Inject
    private ISecurityContext iSecurityContext;

    @Context
    private UriInfo uriInfo;

    @Override
    public PersonaJuridicaResource personaJuridica(String personaJuridica) {
        return personaJuridicaResource;
    }

    @Override
    public Response create(PersonaJuridicaRepresentation rep)
            throws PersonaJuridicaAlreadyExistsException, NotAuthorizedException {

        if (!iSecurityContext.hasPermission(PermissionType.personaAdmin))
            throw ExceptionFactory.notAuthorizedException();

        try {
            TipoDocumentoModel tipoDocumentoPersonaJuridica = tipoDocumentoProvider
                    .findByAbreviatura(rep.getTipoDocumento());

            // Check duplicated tipo y numero de documento
            if (personaJuridicaProvider.findByTipoNumeroDocumento(tipoDocumentoPersonaJuridica,
                    rep.getNumeroDocumento()) != null) {
                throw ExceptionFactory.personaJuridicaAlreadyExistsException(null);
            }

            PersonaNaturalRepresentation representanteRep = rep.getRepresentanteLegal();
            TipoDocumentoModel tipoDocumentoRepresentante = tipoDocumentoProvider
                    .findByAbreviatura(representanteRep.getTipoDocumento());
            PersonaNaturalModel representanteLegal = personaNaturalProvider.findByTipoNumeroDocumento(
                    tipoDocumentoRepresentante, representanteRep.getNumeroDocumento());
            try {
                PersonaJuridicaModel personaJuridica = representationToModel.createPersonaJuridica(rep,
                        tipoDocumentoPersonaJuridica, representanteLegal, personaJuridicaProvider);
                return Response
                        .created(uriInfo.getAbsolutePathBuilder().path(personaJuridica.getId()).build())
                        .header("Access-Control-Expose-Headers", "Location")
                        .entity(ModelToRepresentation.toRepresentation(personaJuridica)).build();
            } catch (ModelDuplicateException e) {
                throw ExceptionFactory.personaJuridicaAlreadyExistsException(null);
            }
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public List<PersonaJuridicaRepresentation> search(String tipoDocumento, String numeroDocumento,
            String razonSocial, String nombreComercial, String filterText, Integer firstResult,
            Integer maxResults) throws NotAuthorizedException {

        if (!iSecurityContext.hasPermission(PermissionType.personaView))
            throw ExceptionFactory.notAuthorizedException();

        try {
            firstResult = firstResult != null ? firstResult : -1;
            maxResults = maxResults != null ? maxResults : -1;

            List<PersonaJuridicaModel> models;
            if (filterText != null) {
                models = personaJuridicaProvider.search(filterText.trim(), firstResult, maxResults);
            } else if (tipoDocumento != null || numeroDocumento != null) {
                TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider
                        .findByAbreviatura(tipoDocumento);
                PersonaJuridicaModel personaJuridica = personaJuridicaProvider
                        .findByTipoNumeroDocumento(tipoDocumentoModel, numeroDocumento);
                models = new ArrayList<>();
                models.add(personaJuridica);
            } else if (razonSocial != null || nombreComercial != null || numeroDocumento != null) {
                Map<String, String> attributes = new HashMap<String, String>();
                if (razonSocial != null) {
                    attributes.put(PersonaJuridicaModel.RAZON_SOCIAL, razonSocial);
                }
                if (nombreComercial != null) {
                    attributes.put(PersonaJuridicaModel.NOMBRE_COMERCIAL, nombreComercial);
                }
                if (numeroDocumento != null) {
                    attributes.put(PersonaJuridicaModel.NUMERO_DOCUMENTO, numeroDocumento);
                }
                models = personaJuridicaProvider.searchByAttributes(attributes, firstResult, maxResults);
            } else {
                models = personaJuridicaProvider.getAll(firstResult, maxResults);
            }

            List<PersonaJuridicaRepresentation> result = new ArrayList<>();
            for (PersonaJuridicaModel model : models) {
                result.add(ModelToRepresentation.toRepresentation(model));
            }
            return result;
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public SearchResultsRepresentation<PersonaJuridicaRepresentation> search(
            SearchCriteriaRepresentation criteria)
                    throws InvalidSearchCriteriaException, NotAuthorizedException {

        if (!iSecurityContext.hasPermission(PermissionType.personaView))
            throw ExceptionFactory.notAuthorizedException();

        try {
            SearchCriteriaUtil.validateSearchCriteria(criteria);
            SearchCriteriaModel criteriaModel = SearchCriteriaUtil.getSearchCriteriaModel(criteria);

            // extract filterText
            String filterText = criteria.getFilterText();

            // search
            SearchResultsModel<PersonaJuridicaModel> models = null;
            if (filterText == null) {
                models = personaJuridicaProvider.search(criteriaModel);
            } else {
                models = personaJuridicaProvider.search(criteriaModel, filterText);
            }

            SearchResultsRepresentation<PersonaJuridicaRepresentation> result = new SearchResultsRepresentation<>();
            List<PersonaJuridicaRepresentation> items = new ArrayList<>();
            for (PersonaJuridicaModel model : models.getModels()) {
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
