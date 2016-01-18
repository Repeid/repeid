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

package org.repeid.manager.api.rest.contract;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.repeid.manager.api.beans.representations.search.SearchCriteriaRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchResultsRepresentation;
import org.repeid.manager.api.beans.representations.security.RoleRepresentation;
import org.repeid.manager.api.rest.contract.exceptions.InvalidSearchCriteriaException;
import org.repeid.manager.api.rest.contract.exceptions.NotAuthorizedException;
import org.repeid.manager.api.rest.contract.exceptions.RoleAlreadyExistsException;
import org.repeid.manager.api.rest.contract.exceptions.RoleNotFoundException;

/**
 * The Role API. Used to manage roles. Note: not used to manage users or user
 * membership in roles. This API simply provides a way to create and manage role
 * definitions. Typically this API is only available to system admins.
 * 
 * @author eric.wittmann@redhat.com
 */
@Path("roles")
public interface IRoleResource {

    /**
     * Use this endpoint to create a new apiman role. A role consists of a set
     * of permissions granted to a user when that user is given the role within
     * the context of an organization.
     * 
     * @summary Create Role
     * @servicetag admin
     * @param bean
     *            The new role.
     * @statuscode 200 If the role is created successfully.
     * @return Full information about the created role.
     * @throws RoleAlreadyExistsException
     *             when role already exists
     * @throws NotAuthorizedException
     *             when not authorized to invoke this method
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RoleRepresentation create(RoleRepresentation bean)
            throws RoleAlreadyExistsException, NotAuthorizedException;

    /**
     * This endpoint lists all of the roles currently defined in apiman.
     * 
     * @summary List all Roles
     * @statuscode 200 If the role list is returned successfully.
     * @return A list of roles.
     * @throws NotAuthorizedException
     *             when not authorized to invoke this method
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<RoleRepresentation> list() throws NotAuthorizedException;

    /**
     * Use this endpoint to retrieve information about a single Role by its ID.
     * 
     * @summary Get a Role by ID
     * @param roleId
     *            The role ID.
     * @statuscode 200 If the role is returned successfully.
     * @return A role.
     * @throws RoleNotFoundException
     *             when a request is sent for a role that does not exist
     * @throws NotAuthorizedException
     *             when not authorized to invoke this method
     */
    @GET
    @Path("{roleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public RoleRepresentation get(@PathParam("roleId") String roleId)
            throws RoleNotFoundException, NotAuthorizedException;

    /**
     * Use this endpoint to update the information about an existing role. The
     * role is identified by its ID.
     * 
     * @summary Update a Role by ID
     * @servicetag admin
     * @param roleId
     *            The role ID.
     * @param bean
     *            Updated role information.
     * @statuscode 204 If the role is updated successfully.
     * @throws RoleNotFoundException
     *             when a request is sent for a role that does not exist
     * @throws NotAuthorizedException
     *             when not authorized to invoke this method
     */
    @PUT
    @Path("{roleId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("roleId") String roleId, RoleRepresentation bean)
            throws RoleNotFoundException, NotAuthorizedException;

    /**
     * Use this endpoint to delete a role by its ID.
     * 
     * @summary Delete a Role by ID
     * @servicetag admin
     * @param roleId
     *            The role ID.
     * @statuscode 204 If the role is deleted.
     * @throws RoleNotFoundException
     *             when a request is sent for a role that does not exist
     * @throws NotAuthorizedException
     *             when not authorized to invoke this method
     */
    @DELETE
    @Path("{roleId}")
    public void delete(@PathParam("roleId") String roleId)
            throws RoleNotFoundException, NotAuthorizedException;

    /**
     * This endpoint provides a way to search for roles. The search criteria is
     * provided in the body of the request, including filters, order-by, and
     * paging information.
     * 
     * @summary Search for Roles
     * @param criteria
     *            The search criteria.
     * @statuscode 200 If the search completes successfully.
     * @return The search results (a page of roles).
     * @throws InvalidSearchCriteriaException
     *             when provided criteria are invalid
     * @throws NotAuthorizedException
     *             when not authorized to invoke this method
     */
    @POST
    @Path("search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SearchResultsRepresentation<RoleRepresentation> search(SearchCriteriaRepresentation criteria)
            throws InvalidSearchCriteriaException, NotAuthorizedException;

}
