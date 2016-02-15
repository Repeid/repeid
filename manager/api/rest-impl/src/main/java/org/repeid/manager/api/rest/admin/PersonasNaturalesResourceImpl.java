/*******************************************************************************
 * Repeid, Home of Professional Open Source
 *
 * Copyright 2015 Sistcoop, Inc. and/or its affiliates.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.repeid.manager.api.rest.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.repeid.manager.api.beans.representations.PersonaNaturalRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchCriteriaRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchResultsRepresentation;
import org.repeid.manager.api.beans.representations.security.PermissionType;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;
import org.repeid.manager.api.model.exceptions.ModelException;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;
import org.repeid.manager.api.model.system.RepeidSession;
import org.repeid.manager.api.model.utils.ModelToRepresentation;
import org.repeid.manager.api.model.utils.RepresentationToModel;
import org.repeid.manager.api.rest.admin.PersonaNaturalResource;
import org.repeid.manager.api.rest.admin.PersonasNaturalesResource;
import org.repeid.manager.api.rest.contract.exceptions.SystemErrorException;
import org.repeid.manager.api.rest.impl.util.ExceptionFactory;
import org.repeid.manager.api.rest.impl.util.SearchCriteriaUtil;
import org.repeid.manager.api.security.ISecurityContext;

@RequestScoped
public class PersonasNaturalesResourceImpl implements PersonasNaturalesResource {

	@Inject
	private RepeidSession session;

	@Inject
	private ISecurityContext auth;

	@Context
	private UriInfo uriInfo;

	@Inject
	private PersonaNaturalResource personaNaturalResource;

	@Override
	public PersonaNaturalResource personaNatural(String personaNatural) {
		return personaNaturalResource;
	}

	@Override
	public Response create(PersonaNaturalRepresentation rep) {
		if (!auth.hasPermission(PermissionType.personaAdmin))
			throw ExceptionFactory.notAuthorizedException();

		TipoDocumentoModel tipoDocumento = session.tipoDocumentos().findByAbreviatura(rep.getTipoDocumento());

		// Check duplicated tipo y numero de documento
		if (session.personasNaturales().findByTipoNumeroDocumento(tipoDocumento, rep.getNumeroDocumento()) != null) {
			throw ExceptionFactory
					.personaNaturalAlreadyExistsException(rep.getTipoDocumento() + ":" + rep.getNumeroDocumento());
		}

		try {
			PersonaNaturalModel personaNatural = RepresentationToModel.createPersonaNatural(session, rep,
					tipoDocumento);

			if (session.getTransaction().isActive()) {
				session.getTransaction().commit();
			}

			return Response.created(uriInfo.getAbsolutePathBuilder().path(personaNatural.getId()).build()).build();
		} catch (ModelDuplicateException e) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().setRollbackOnly();
			}
			throw ExceptionFactory
					.personaNaturalAlreadyExistsException(rep.getTipoDocumento() + ":" + rep.getNumeroDocumento());
		} catch (ModelException e) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().setRollbackOnly();
			}
			throw new SystemErrorException(e);
		}
	}

	@Override
	public List<PersonaNaturalRepresentation> search(String tipoDocumento, String numeroDocumento,
			String apellidoPaterno, String apellidoMaterno, String nombres, String filterText, Integer firstResult,
			Integer maxResults) {
		if (!auth.hasPermission(PermissionType.personaView))
			throw ExceptionFactory.notAuthorizedException();

		int firstResultParam = firstResult != null ? firstResult : -1;
		int maxResultsParam = maxResults != null ? maxResults : -1;

		List<PersonaNaturalModel> models;
		if (filterText != null) {
			models = session.personasNaturales().search(filterText.trim(), firstResult, maxResults);
		} else if (tipoDocumento != null || numeroDocumento != null) {
			TipoDocumentoModel tipoDocumentoModel = session.tipoDocumentos().findByAbreviatura(tipoDocumento);
			PersonaNaturalModel personaNatural = session.personasNaturales()
					.findByTipoNumeroDocumento(tipoDocumentoModel, numeroDocumento);
			models = new ArrayList<>();
			models.add(personaNatural);
		} else if (apellidoPaterno != null || apellidoMaterno != null || nombres != null || numeroDocumento != null) {
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
			models = session.personasNaturales().searchByAttributes(attributes, firstResult, maxResults);
		} else {
			models = session.personasNaturales().getAll(firstResultParam, maxResultsParam);
		}

		List<PersonaNaturalRepresentation> result = new ArrayList<>();
		for (PersonaNaturalModel model : models) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return result;
	}

	@Override
	public SearchResultsRepresentation<PersonaNaturalRepresentation> search(SearchCriteriaRepresentation criteria) {
		if (!auth.hasPermission(PermissionType.personaView))
			throw ExceptionFactory.notAuthorizedException();

		SearchCriteriaUtil.validateSearchCriteria(criteria);
		SearchCriteriaModel criteriaModel = SearchCriteriaUtil.getSearchCriteriaModel(criteria);

		// extract filterText
		String filterText = criteria.getFilterText();

		// search
		SearchResultsModel<PersonaNaturalModel> models = null;
		if (filterText == null) {
			models = session.personasNaturales().search(criteriaModel);
		} else {
			models = session.personasNaturales().search(criteriaModel, filterText);
		}

		SearchResultsRepresentation<PersonaNaturalRepresentation> result = new SearchResultsRepresentation<>();
		List<PersonaNaturalRepresentation> items = new ArrayList<>();
		for (PersonaNaturalModel model : models.getModels()) {
			items.add(ModelToRepresentation.toRepresentation(model));
		}
		result.setItems(items);
		result.setTotalSize(models.getTotalSize());
		return result;
	}

}
