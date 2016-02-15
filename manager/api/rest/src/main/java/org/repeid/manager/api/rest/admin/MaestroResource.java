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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author carlosthe19916@gmail.com
 */

@Path("/utils")
@Produces(MediaType.APPLICATION_JSON)
public interface MaestroResource {

	/**
	 * Este endpoint retorna todos los tipos de persona soportados por repeid.
	 * 
	 * @summary Get all TipoPersona
	 * 
	 * @statuscode 200 Si la busqueda fue realizada satisfactoriamente.
	 * @return Los tipos de persona soportados por repeid.
	 */
	@GET
	@Path("tiposPersona")
	public List<String> getAllTipoPersonas();

	/**
	 * Este endpoint retorna todos los estados civiles soportados por repeid.
	 * 
	 * @summary Get all EstadoCivil
	 * 
	 * @statuscode 200 Si la busqueda fue realizada satisfactoriamente.
	 * @return Los estado civiles soportados por repeid.
	 */
	@GET
	@Path("estadosCiviles")
	public List<String> getAllEstadosCiviles();

	/**
	 * Este endpoint retorna todos los sexos soportados por repeid.
	 * 
	 * @summary Get all Sexo
	 * 
	 * @statuscode 200 Si la busqueda fue realizada satisfactoriamente.
	 * @return Los sexos soportados por repeid.
	 */
	@GET
	@Path("sexos")
	public List<String> getAllSexos();

	/**
	 * Este endpoint retorna todos los tipos de empresa soportados por repeid.
	 * 
	 * @summary Get all TipoEmpresa
	 * 
	 * @statuscode 200 Si la busqueda fue realizada satisfactoriamente.
	 * @return Los tipos de empresa soportados por repeid.
	 */
	@GET
	@Path("tiposEmpresa")
	public List<String> getAllTiposEmpresa();

}
