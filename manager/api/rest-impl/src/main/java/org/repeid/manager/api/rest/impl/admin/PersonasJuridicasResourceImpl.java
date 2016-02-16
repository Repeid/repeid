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
package org.repeid.manager.api.rest.impl.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.repeid.manager.api.beans.representations.PersonaJuridicaRepresentation;
import org.repeid.manager.api.beans.representations.PersonaNaturalRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchCriteriaRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchResultsRepresentation;
import org.repeid.manager.api.beans.representations.security.PermissionType;
import org.repeid.manager.api.model.PersonaJuridicaModel;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;
import org.repeid.manager.api.model.exceptions.ModelException;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;
import org.repeid.manager.api.model.system.RepeidSession;
import org.repeid.manager.api.model.utils.ModelToRepresentation;
import org.repeid.manager.api.model.utils.RepresentationToModel;
import org.repeid.manager.api.rest.admin.PersonaJuridicaResource;
import org.repeid.manager.api.rest.admin.PersonasJuridicasResource;
import org.repeid.manager.api.rest.contract.exceptions.SystemErrorException;
import org.repeid.manager.api.rest.util.ExceptionFactory;
import org.repeid.manager.api.rest.util.SearchCriteriaUtil;
import org.repeid.manager.api.security.ISecurityContext;

@RequestScoped
public class PersonasJuridicasResourceImpl implements PersonasJuridicasResource {

	@Inject
	private RepeidSession session;

	@Inject
	private ISecurityContext auth;

	@Context
	private UriInfo uriInfo;

	@Inject
	private PersonaJuridicaResource personaJuridicaResource;

	@Override
	public PersonaJuridicaResource personaJuridica(String personaJuridica) {
		return personaJuridicaResource;
	}

	@Override
	public Response create(PersonaJuridicaRepresentation rep) {
		if (!auth.hasPermission(PermissionType.personaAdmin))
			throw ExceptionFactory.notAuthorizedException();

		TipoDocumentoModel tipoDocumentoPersonaJuridica = session.tipoDocumentos()
				.findByAbreviatura(rep.getTipoDocumento());

		// Check duplicated tipo y numero de documento
		if (session.personasJuridicas().findByTipoNumeroDocumento(tipoDocumentoPersonaJuridica,
				rep.getNumeroDocumento()) != null) {
			throw ExceptionFactory
					.personaJuridicaAlreadyExistsException(rep.getTipoDocumento() + ":" + rep.getNumeroDocumento());
		}

		PersonaNaturalRepresentation representanteRep = rep.getRepresentanteLegal();
		TipoDocumentoModel tipoDocumentoRepresentante = session.tipoDocumentos()
				.findByAbreviatura(representanteRep.getTipoDocumento());
		PersonaNaturalModel representanteLegal = session.personasNaturales()
				.findByTipoNumeroDocumento(tipoDocumentoRepresentante, representanteRep.getNumeroDocumento());
		try {
			PersonaJuridicaModel personaJuridica = RepresentationToModel.createPersonaJuridica(session, rep,
					tipoDocumentoPersonaJuridica, representanteLegal);

			if (session.getTransaction().isActive()) {
				session.getTransaction().commit();
			}

			return Response.created(uriInfo.getAbsolutePathBuilder().path(personaJuridica.getId()).build()).build();
		} catch (ModelDuplicateException e) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().setRollbackOnly();
			}
			throw ExceptionFactory
					.personaJuridicaAlreadyExistsException(rep.getTipoDocumento() + ":" + rep.getNumeroDocumento());
		} catch (ModelException e) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().setRollbackOnly();
			}
			throw new SystemErrorException(e);
		}
	}

	@Override
	public List<PersonaJuridicaRepresentation> search(String tipoDocumento, String numeroDocumento, String razonSocial,
			String nombreComercial, String filterText, Integer firstResult, Integer maxResults) {
		if (!auth.hasPermission(PermissionType.personaView))
			throw ExceptionFactory.notAuthorizedException();

		firstResult = firstResult != null ? firstResult : -1;
		maxResults = maxResults != null ? maxResults : -1;

		List<PersonaJuridicaModel> models;
		if (filterText != null) {
			models = session.personasJuridicas().search(filterText.trim(), firstResult, maxResults);
		} else if (tipoDocumento != null || numeroDocumento != null) {
			TipoDocumentoModel tipoDocumentoModel = session.tipoDocumentos().findByAbreviatura(tipoDocumento);
			PersonaJuridicaModel personaJuridica = session.personasJuridicas()
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
			models = session.personasJuridicas().searchByAttributes(attributes, firstResult, maxResults);
		} else {
			models = session.personasJuridicas().getAll(firstResult, maxResults);
		}

		List<PersonaJuridicaRepresentation> result = new ArrayList<>();
		for (PersonaJuridicaModel model : models) {
			result.add(ModelToRepresentation.toRepresentation(model));
		}
		return result;
	}

	@Override
	public SearchResultsRepresentation<PersonaJuridicaRepresentation> search(SearchCriteriaRepresentation criteria) {
		if (!auth.hasPermission(PermissionType.personaView))
			throw ExceptionFactory.notAuthorizedException();

		SearchCriteriaUtil.validateSearchCriteria(criteria);
		SearchCriteriaModel criteriaModel = SearchCriteriaUtil.getSearchCriteriaModel(criteria);

		// extract filterText
		String filterText = criteria.getFilterText();

		// search
		SearchResultsModel<PersonaJuridicaModel> models = null;
		if (filterText == null) {
			models = session.personasJuridicas().search(criteriaModel);
		} else {
			models = session.personasJuridicas().search(criteriaModel, filterText);
		}

		SearchResultsRepresentation<PersonaJuridicaRepresentation> result = new SearchResultsRepresentation<>();
		List<PersonaJuridicaRepresentation> items = new ArrayList<>();
		for (PersonaJuridicaModel model : models.getModels()) {
			items.add(ModelToRepresentation.toRepresentation(model));
		}
		result.setItems(items);
		result.setTotalSize(models.getTotalSize());
		return result;
	}

}
