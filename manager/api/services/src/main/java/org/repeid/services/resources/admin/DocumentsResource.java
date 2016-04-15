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
package org.repeid.services.resources.admin;

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

import org.repeid.manager.api.beans.representations.TipoDocumentoRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchCriteriaRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchResultsRepresentation;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

@Consumes(MediaType.APPLICATION_JSON)
public interface DocumentsResource {

	public static final String TIPO_DOCUMENTO_ID = "tipoDocumentoId";

	/**
	 * @param tipoDocumentoId
	 *            El ID del TipoDocumento.
	 */
	@Path("{" + TIPO_DOCUMENTO_ID + "}")
	public DocumentResource tipoDocumento(@PathParam(TIPO_DOCUMENTO_ID) String tipoDocumentoId);

	/**
	 * Use este endpoint para crear un nuevo tipoDocumento. Un TipoDocumento
	 * representa el documento con el que se identifica una persona en un
	 * determinado pais.
	 * 
	 * @summary Create TipoDocumento
	 * @servicetag admin
	 * @param rep
	 *            El nuevo TipoDocumento.
	 * @statuscode 200 Si el tipoDocumento fue creado satisfactoriamente.
	 * @return La informacion completa del tipoDocumento creado.
	 * @throws TipoDocumentoAlreadyExistsException
	 *             cuando el tipoDocumento ya existe.
	 * @throws NotAuthorizedException
	 *             cuando no esta autorizado para invocar este metodo.
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(TipoDocumentoRepresentation rep);

	/**
	 * Este endpoint provee una forma de buscar tipoDocumentos. Los criterios de
	 * busqueda estan definidos por los parametros enviados.
	 * 
	 * @summary Search for TipoDocumento
	 * 
	 * @param abreviatura
	 *            Abreviatura de tipoDocumento.
	 * @param denominacion
	 *            Denominacion de tipoDocumento.
	 * @param tipoPersona
	 *            TipoPersona puede ser NATURAL o JURIDICA.
	 * @param estado
	 *            Estado puede ser True o False.
	 * @param filterText
	 *            FilterText representa el patron de busqueda.
	 * @param first
	 *            First representa el primer elemento del resutado de busqueda.
	 * @param max
	 *            Max representa el numero maximo de elementos del resutado de
	 *            busqueda.
	 * 
	 * @statuscode 200 Si la busqueda fue realizada satisfactoriamente.
	 * @return Los resultados de la busqueda (una pagina de tipoDocumentos).
	 * @throws NotAuthorizedException
	 *             cuando no esta autorizado para invocar este metodo.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TipoDocumentoRepresentation> search(@QueryParam("abreviatura") String abreviatura,
													@QueryParam("denominacion") String denominacion, @QueryParam("tipoPersona") String tipoPersona,
													@QueryParam("estado") Boolean estado, @QueryParam("filterText") String filterText,
													@QueryParam("first") Integer firstResult, @QueryParam("max") Integer maxResults);

	/**
	 * Este endpoint provee una forma de buscar tipoDocumentos. Los criterios de
	 * busqueda estan definidos por los parametros enviados.
	 * 
	 * @summary Search for TipoDocumento
	 * @param criteria
	 *            Criterio de busqueda.
	 * @statuscode 200 Si la busqueda fue realizada satisfactoriamente.
	 * @return Los resultados de la busqueda (una pagina de tipoDocumentos).
	 * @throws InvalidSearchCriteriaException
	 *             cuando el criterio de busqueda no es valido.
	 * @throws NotAuthorizedException
	 *             cuando no esta autorizado para invocar este metodo.
	 */
	@POST
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
	public SearchResultsRepresentation<TipoDocumentoRepresentation> search(SearchCriteriaRepresentation criteria);

}