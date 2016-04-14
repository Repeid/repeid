package org.repeid.services.resources.admin;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.cache.NoCache;

public interface RealmAdminResource {

    /**
     * Base path for importing clients under this realm.
     *
     * @return
     */
    /*@Path("client-description-converter")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public ClientRepresentation convertClientDescription(String description);*/

    /**
     * Base path for managing attack detection.
     *
     * @return
     */
    /*@Path("attack-detection")
    public AttackDetectionResource getAttackDetection();*/

    /**
     * Base path for managing clients under this realm.
     *
     * @return
     */
    /*@Path("clients")
    public ClientsResource getClients();*/

    /**
     * Base path for managing client templates under this realm.
     *
     * @return
     */
    /*@Path("client-templates")
    public ClientTemplatesResource getClientTemplates();*/

    /**
     * Base path for managing client initial access tokens
     *
     * @return
     */
    /*@Path("clients-initial-access")
    public ClientInitialAccessResource getClientInitialAccess();*/

    /**
     * base path for managing realm-level roles of this realm
     *
     * @return
     */
    /*@Path("roles")
    public RoleContainerResource getRoleContainerResource();*/

    /**
     * Get the top-level representation of the realm
     *
     * It will not include nested information like User and Client
     * representations.
     *
     * @return
     */
    /*@GET
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public RealmRepresentation getRealm();*/

    /**
     * Update the top-level information of the realm
     *
     * Any user, roles or client information in the representation will be
     * ignored. This will only update top-level attributes of the realm.
     *
     * @param rep
     * @return
     */
    /*@PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRealm(final RealmRepresentation rep);*/

    /**
     * Delete the realm
     *
     */
    @DELETE
    public void deleteRealm();

    /**
     * Base path for managing users in this realm.
     *
     * @return
     */
    /*@Path("users")
    public UsersResource users();*/

    /*@Path("user-federation")
    public UserFederationProvidersResource userFederation();

    @Path("authentication")
    public AuthenticationManagementResource flows();*/

    /**
     * Path for managing all realm-level or client-level roles defined in this
     * realm by its id.
     *
     * @return
     */
    /*@Path("roles-by-id")
    public RoleByIdResource rolesById();*/

    /**
     * Push the realm's revocation policy to any client that has an admin url
     * associated with it.
     *
     */
    /*@Path("push-revocation")
    @POST
    public GlobalRequestResult pushRevocation();*/

    /**
     * Removes all user sessions. Any client that has an admin url will also be
     * told to invalidate any sessions they have.
     *
     */
    /*@Path("logout-all")
    @POST
    public GlobalRequestResult logoutAll();*/

    /**
     * Remove a specific user session. Any client that has an admin url will
     * also be told to invalidate this particular session.
     *
     * @param sessionId
     */
    /*@Path("sessions/{session}")
    @DELETE
    public void deleteSession(@PathParam("session") String sessionId);*/

    /**
     * Get client session stats
     *
     * Returns a JSON map. The key is the client id, the value is the number of
     * sessions that currently are active with that client. Only clients that
     * actually have a session associated with them will be in this map.
     *
     * @return
     */
    @Path("client-session-stats")
    @GET
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<String, String>> getClientSessionStats();

    /**
     * Get the events provider configuration
     *
     * Returns JSON object with events provider configuration
     *
     * @return
     */
    /*@GET
    @NoCache
    @Path("events/config")
    @Produces(MediaType.APPLICATION_JSON)
    public RealmEventsConfigRepresentation getRealmEventsConfig();*/

    /**
     * Update the events provider
     *
     * Change the events provider and/or its configuration
     *
     * @param rep
     */
    /*@PUT
    @Path("events/config")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateRealmEventsConfig(final RealmEventsConfigRepresentation rep);*/

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
    /*@Path("events")
    @GET
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public List<EventRepresentation> getEvents(@QueryParam("type") List<String> types,
            @QueryParam("client") String client, @QueryParam("user") String user,
            @QueryParam("dateFrom") String dateFrom, @QueryParam("dateTo") String dateTo,
            @QueryParam("ipAddress") String ipAddress, @QueryParam("first") Integer firstResult,
            @QueryParam("max") Integer maxResults);*/

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
    /*@Path("admin-events")
    @GET
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public List<AdminEventRepresentation> getEvents(@QueryParam("operationTypes") List<String> operationTypes,
            @QueryParam("authRealm") String authRealm, @QueryParam("authClient") String authClient,
            @QueryParam("authUser") String authUser, @QueryParam("authIpAddress") String authIpAddress,
            @QueryParam("resourcePath") String resourcePath, @QueryParam("dateFrom") String dateFrom,
            @QueryParam("dateTo") String dateTo, @QueryParam("first") Integer firstResult,
            @QueryParam("max") Integer maxResults);*/

    /**
     * Delete all events
     *
     */
   /* @Path("events")
    @DELETE
    public void clearEvents();*/

    /**
     * Delete all admin events
     *
     */
    /*@Path("admin-events")
    @DELETE
    public void clearAdminEvents();*/

    /**
     * Test LDAP connection
     *
     * @param action
     * @param connectionUrl
     * @param bindDn
     * @param bindCredential
     * @return
     */
    /*@Path("testLDAPConnection")
    @GET
    @NoCache
    public Response testLDAPConnection(@QueryParam("action") String action,
            @QueryParam("connectionUrl") String connectionUrl, @QueryParam("bindDn") String bindDn,
            @QueryParam("bindCredential") String bindCredential,
            @QueryParam("useTruststoreSpi") String useTruststoreSpi);*/

    /*@Path("identity-provider")
    public IdentityProvidersResource getIdentityProviderResource();*/

    /**
     * Get group hierarchy. Only name and ids are returned.
     *
     * @return
     */
    /*@GET
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    @Path("default-groups")
    public List<GroupRepresentation> getDefaultGroups();*/

    @PUT
    @NoCache
    @Path("default-groups/{groupId}")
    public void addDefaultGroup(@PathParam("groupId") String groupId);

    @DELETE
    @NoCache
    @Path("default-groups/{groupId}")
    public void removeDefaultGroup(@PathParam("groupId") String groupId);

    /*@Path("groups")
    public GroupsResource getGroups();*/

    /*@GET
    @Path("group-by-path/{path: .*}")
    @NoCache
    @Produces(MediaType.APPLICATION_JSON)
    public GroupRepresentation getGroupByPath(@PathParam("path") String path);*/

    /**
     * Partial import from a JSON file to an existing realm.
     *
     * @param rep
     * @return
     */
    /*@Path("partialImport")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response partialImport(PartialImportRepresentation rep);*/

    /**
     * Clear realm cache
     *
     */
    @Path("clear-realm-cache")
    @POST
    public void clearRealmCache();

    /**
     * Clear user cache
     *
     */
    @Path("clear-user-cache")
    @POST
    public void clearUserCache();

}
