package org.repeid.services.resources.admin;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.repeid.representations.idm.AdminEventRepresentation;
import org.repeid.representations.idm.EventRepresentation;
import org.repeid.representations.idm.OrganizationEventsConfigRepresentation;
import org.repeid.representations.idm.OrganizationRepresentation;

public interface OrganizationAdminResource {

    /**
     * Get the top-level representation of the organization
     *
     * It will not include nested information like User and Client
     * representations.
     *
     * @return
     */
    @GET
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public OrganizationRepresentation getOrganization();

    /**
     * Update the top-level information of the organization
     *
     * Any user, roles or client information in the representation will be
     * ignored. This will only update top-level attributes of the realm.
     *
     * @param rep
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRealm(final OrganizationRepresentation rep);

    /**
     * Delete the organization
     */
    @DELETE
    public void deleteOrganization();

    /**
     * Base path for managing documents in this organization.
     *
     * @return
     */
    @Path("documents")
    public DocumentsResource getDocumentsResource();

    /**
     * base path for managing organization-level persons of this organization
     *
     * @return
     */
    @Path("persons")
    public PersonsResource getPersonsResource();

    /**
     * Get the events provider configuration
     *
     * Returns JSON object with events provider configuration
     *
     * @return
     */
    @GET
    @NoCache
    @Path("events/config")
    @Produces(MediaType.APPLICATION_JSON)
    public OrganizationEventsConfigRepresentation getOrganizationEventsConfig();

    /**
     * Update the events provider
     *
     * Change the events provider and/or its configuration
     *
     * @param rep
     */
    @PUT
    @Path("events/config")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateOrganizationEventsConfig(final OrganizationEventsConfigRepresentation rep);

    /**
     * Get events
     *
     * Returns all events, or filters them based on URL query parameters listed
     * here
     *
     * @param types
     *            The types of events to return
     * @param client
     *            App or oauth client name
     * @param user
     *            User id
     * @param ipAddress
     *            IP address
     * @param dateTo
     *            To date
     * @param dateFrom
     *            From date
     * @param firstResult
     *            Paging offset
     * @param maxResults
     *            Paging size
     * @return
     */
    @Path("events")
    @GET
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public List<EventRepresentation> getEvents(@QueryParam("type") List<String> types,
            @QueryParam("client") String client, @QueryParam("user") String user,
            @QueryParam("dateFrom") String dateFrom, @QueryParam("dateTo") String dateTo,
            @QueryParam("ipAddress") String ipAddress, @QueryParam("first") Integer firstResult,
            @QueryParam("max") Integer maxResults);

    /**
     * Get admin events
     *
     * Returns all admin events, or filters events based on URL query parameters
     * listed here
     *
     * @param operationTypes
     * @param authRealm
     * @param authClient
     * @param authUser
     *            user id
     * @param authIpAddress
     * @param resourcePath
     * @param dateTo
     * @param dateFrom
     * @param firstResult
     * @param maxResults
     * @return
     */
    @Path("admin-events")
    @GET
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public List<AdminEventRepresentation> getEvents(@QueryParam("operationTypes") List<String> operationTypes,
            @QueryParam("authRealm") String authRealm, @QueryParam("authClient") String authClient,
            @QueryParam("authUser") String authUser, @QueryParam("authIpAddress") String authIpAddress,
            @QueryParam("resourcePath") String resourcePath, @QueryParam("dateFrom") String dateFrom,
            @QueryParam("dateTo") String dateTo, @QueryParam("first") Integer firstResult,
            @QueryParam("max") Integer maxResults);

    /**
     * Delete all events
     *
     */
    @Path("events")
    @DELETE
    public void clearEvents();

    /**
     * Delete all admin events
     *
     */
    @Path("admin-events")
    @DELETE
    public void clearAdminEvents();

    /**
     * Clear organization cache
     */
    @Path("clear-organization-cache")
    @POST
    public void clearOrganizationCache();

    /**
     * Clear person cache
     */
    @Path("clear-person-cache")
    @POST
    public void clearPersonCache();

}
