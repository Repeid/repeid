package org.repeid.services.resources.admin;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.repeid.representations.idm.OrganizationRepresentation;

public interface OrganizationsAdminResource {

    @GET
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrganizationRepresentation> getOrganizations();

    /**
     * Import a organization
     * <p>
     * Imports a organization from a full representation of that organization. Organization name must
     * be unique.
     *
     * @param uriInfo
     * @param rep JSON representation of the organization
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response importOrganization(@Context final UriInfo uriInfo, final OrganizationRepresentation rep);

    /**
     * Base path for the admin REST API for one particular organization.
     *
     * @param headers
     * @param name organization name (not id!)
     * @return
     */
    @Path("{organization}")
    public OrganizationAdminResource getOrganizationAdmin(@Context final HttpHeaders headers,
                                                   @PathParam("organization") final String name);

}
