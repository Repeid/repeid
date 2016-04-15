package org.repeid.services.resources.admin;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("/admin")
public interface AdminRoot {

    /**
     * Convenience path to master organization admin console
     *
     * @exclude
     * @return
     */
    @GET
    public Response masterRealmAdminConsoleRedirect();

    /**
     * Convenience path to master organization admin console
     *
     * @exclude
     * @return
     */
    @Path("index.{html:html}") // expression is actually "index.html" but this
                               // is a hack to get around jax-doclet bug
    @GET
    public Response masterRealmAdminConsoleRedirectHtml();

    /**
     * path to organization admin console ui
     *
     * @exclude
     * @param name
     *            Organizacion name (not id!)
     * @return
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
    public OrganizationsAdminResource getRealmsAdmin(@Context final HttpHeaders headers);

    /**
     * Base Path to utils admin REST interface
     *
     * @param headers
     * @return
     */
    @Path("commons")
    public CommonsResource getUtilsResource(@Context final HttpHeaders headers);

    /**
     * General information about the server
     *
     * @param headers
     * @return
     */
    @Path("serverinfo")
    public ServerInfoAdminResource getServerInfo(@Context final HttpHeaders headers);

}
