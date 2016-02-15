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

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.repeid.manager.api.beans.representations.PersonaJuridicaRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchCriteriaRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchResultsRepresentation;

/**
 * @author carlosthe19916@gmail.com
 */

@Consumes(MediaType.APPLICATION_JSON)
public interface PersonasJuridicasResource {

	public static final String PERSONA_JURIDICA_ID = "personaJuridicaId";

	/**
	 * @param personaJuridicaId
	 *            El ID de la PersonaJuridica.
	 */
	@Path("{" + PERSONA_JURIDICA_ID + "}")
	public PersonaJuridicaResource personaJuridica(@PathParam(PERSONA_JURIDICA_ID) String personaJuridicaId);

	/**
	 * Use este endpoint para crear una nueva personaJuridica. Una
	 * PersonaJuridica representa una persona con razon social.
	 * 
	 * @summary Create PersonaJuridica
	 * @servicetag admin
	 * @param rep
	 *            La nueva PersonaJuridica.
	 * @statuscode 200 Si la personaJuridica fue creada satisfactoriamente.
	 * @return La informacion completa de la personaJuridica creada.
	 * @throws PersonaJuridicaAlreadyExistsException
	 *             cuando la personaJuridica ya existe.
	 * @throws NotAuthorizedException
	 *             cuando no esta autorizado para invocar este metodo.
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(PersonaJuridicaRepresentation rep);

	/**
	 * Este endpoint provee una forma de buscar personaJuridicas. Los criterios
	 * de busqueda estan definidos por los parametros enviados.
	 * 
	 * @summary Search for PersonaJuridica
	 * 
	 * @param tipoDocumento
	 *            TipoDocumento de personaJuridica.
	 * @param numeroDocumento
	 *            NumeroDocumento de personaJuridica.
	 * @param razonSocial
	 *            RazonSocial de personaJuridica.
	 * @param nombreComercial
	 *            NombreComercial de personaJuridica.
	 * @param nombres
	 *            Nombres de personaJuridica.
	 * @param filterText
	 *            FilterText representa el patron de busqueda.
	 * @param first
	 *            First representa el primer elemento del resutado de busqueda.
	 * @param max
	 *            Max representa el numero maximo de elementos del resutado de
	 *            busqueda.
	 * 
	 * @statuscode 200 Si la busqueda fue realizada satisfactoriamente.
	 * @return Los resultados de la busqueda (una pagina de personaJuridicas).
	 * @throws NotAuthorizedException
	 *             cuando no esta autorizado para invocar este metodo.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<PersonaJuridicaRepresentation> search(@QueryParam("tipoDocumento") String tipoDocumento,
			@QueryParam("numeroDocumento") String numeroDocumento, @QueryParam("razonSocial") String razonSocial,
			@QueryParam("nombreComercial") String nombreComercial, @QueryParam("filterText") String filterText,
			@QueryParam("first") Integer firstResult, @QueryParam("max") Integer maxResults);

	/**
	 * Este endpoint provee una forma de buscar personaJuridicas. Los criterios
	 * de busqueda estan definidos por los parametros enviados.
	 * 
	 * @summary Search for PersonaJuridica
	 * @param criteria
	 *            Criterio de busqueda.
	 * @statuscode 200 Si la busqueda fue realizada satisfactoriamente.
	 * @return Los resultados de la busqueda (una pagina de personaJuridicas).
	 * @throws InvalidSearchCriteriaException
	 *             cuando el criterio de busqueda no es valido.
	 * @throws NotAuthorizedException
	 *             cuando no esta autorizado para invocar este metodo.
	 */
	@POST
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
	public SearchResultsRepresentation<PersonaJuridicaRepresentation> search(SearchCriteriaRepresentation criteria);

}