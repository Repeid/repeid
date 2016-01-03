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

package io.apiman.manager.api.rest.contract;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.apiman.manager.api.beans.idm.CurrentUserBean;
import io.apiman.manager.api.beans.idm.UpdateUserBean;

/**
 * The Current User API. Returns information about the authenticated user.
 * 
 * @author eric.wittmann@redhat.com
 */
@Path("currentuser")
public interface ICurrentUserResource {

    /**
     * Use this endpoint to get information about the currently authenticated
     * user.
     * 
     * @summary Get Current User Information
     * @statuscode 200 If the information is correctly returned.
     * @return Information about the authenticated user.
     */
    @GET
    @Path("info")
    @Produces(MediaType.APPLICATION_JSON)
    public CurrentUserBean getInfo();

    /**
     * This endpoint allows updating information about the authenticated user.
     * 
     * @summary Update Current User Information
     * @param info
     *            Data to use when updating the user.
     * @statuscode 204 If the update is successful.
     */
    @PUT
    @Path("info")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateInfo(UpdateUserBean info);

}
