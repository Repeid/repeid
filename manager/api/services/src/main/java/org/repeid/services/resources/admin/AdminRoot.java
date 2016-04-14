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
     * Convenience path to master realm admin console
     *
     * @exclude
     * @return
     */
    @GET
    public Response masterRealmAdminConsoleRedirect();

    /**
     * Convenience path to master realm admin console
     *
     * @exclude
     * @return
     */
    @Path("index.{html:html}") // expression is actually "index.html" but this
                               // is a hack to get around jax-doclet bug
    @GET
    public Response masterRealmAdminConsoleRedirectHtml();

    /**
     * path to realm admin console ui
     *
     * @exclude
     * @param name
     *            Realm name (not id!)
     * @return
     */
    @Path("{realm}/console")
    public AdminConsole getAdminConsole(final @PathParam("realm") String name);

    /**
     * Base Path to realm admin REST interface
     *
     * @param headers
     * @return
     */
    @Path("realms")
    public RealmsAdminResource getRealmsAdmin(@Context final HttpHeaders headers);

    /**
     * Base Path to utils admin REST interface
     *
     * @param headers
     * @return
     */
    @Path("utils")
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
