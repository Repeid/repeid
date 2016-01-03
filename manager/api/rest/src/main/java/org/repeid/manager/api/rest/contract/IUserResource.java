/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.repeid.manager.api.rest.contract;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.repeid.manager.api.rest.contract.exceptions.InvalidSearchCriteriaException;
import org.repeid.manager.api.rest.contract.exceptions.NotAuthorizedException;
import org.repeid.manager.api.rest.contract.exceptions.UserNotFoundException;
import org.repeid.representations.idm.security.UserRepresentation;

import io.apiman.manager.api.beans.search.SearchCriteriaBean;
import io.apiman.manager.api.beans.search.SearchResultsBean;

/**
 * The User API.
 * 
 * @author eric.wittmann@redhat.com
 */
@Path("users")
public interface IUserResource {

    /**
     * Use this endpoint to get information about a specific user by the User
     * ID.
     * 
     * @summary Get User by ID
     * @param userId
     *            The user ID.
     * @statuscode 200 If the user exists and information is returned.
     * @return Full user information.
     * @throws UserNotFoundException
     *             when specified user not found
     */
    @GET
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserRepresentation get(@PathParam("userId") String userId) throws UserNotFoundException;

    /**
     * Use this endpoint to update the information about a user. This will fail
     * unless the authenticated user is an admin or identical to the user being
     * updated.
     * 
     * @summary Update a User by ID
     * @param userId
     *            The user ID.
     * @param user
     *            Updated user information.
     * @statuscode 204 If the user information is successfully updated.
     * @throws UserNotFoundException
     *             when specified user not found
     * @throws NotAuthorizedException
     *             when not authorized to invoke this method
     */
    @PUT
    @Path("{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("userId") String userId, UserRepresentation user)
            throws UserNotFoundException, NotAuthorizedException;

    /**
     * Use this endpoint to search for users. The search criteria is provided in
     * the body of the request, including filters, order-by, and paging
     * information.
     * 
     * @summary Search for Users
     * @param criteria
     *            The search criteria.
     * @statuscode 200 If the search is successful.
     * @return The search results (a page of organizations).
     * @throws InvalidSearchCriteriaException
     *             when provided criteria are invalid
     */
    /*@POST
    @Path("search")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SearchResultsBean<UserRepresentation> search(SearchCriteriaBean criteria)
            throws InvalidSearchCriteriaException;*/

}
