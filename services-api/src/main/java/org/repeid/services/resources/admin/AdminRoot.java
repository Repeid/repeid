package org.repeid.services.resources.admin;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.repeid.services.resources.admin.info.ServerInfoAdminResource;

@Path("/admin")
public interface AdminRoot {

    /**
     * Convenience path to master organization admin console
     *
     * @return
     * @exclude
     */
    @GET
    public Response masterOrganizationAdminConsoleRedirect();

    /**
     * Convenience path to master organization admin console
     *
     * @return
     * @exclude
     */
    @Path("index.{html:html}") // expression is actually "index.html" but this is a hack to get around jax-doclet bug
    @GET
    public Response masterOrganizationAdminConsoleRedirectHtml();

    /**
     * path to organization admin console ui
     *
     * @param name Organizacion name (not id!)
     * @return
     * @exclude
     */
    @Path("{organization}/console")
    public AdminConsole getAdminConsole(final @PathParam("organization") String name);

    /**
     * Base Path to organization admin REST interface
     *
     * @param headers
     * @return
     */
    @Path("organizations")
    public OrganizationsAdminResource getOrganizationsAdmin(@Context final HttpHeaders headers);

    /**
     * Base Path to commons admin REST interface
     *
     * @param headers
     * @return
     */
    @Path("commons")
    public CommonsAdminResource getCommonsResource(@Context final HttpHeaders headers);

    /**
     * General information about the server
     *
     * @param headers
     * @return
     */
    @Path("serverinfo")
    public ServerInfoAdminResource getServerInfo(@Context final HttpHeaders headers);

}
