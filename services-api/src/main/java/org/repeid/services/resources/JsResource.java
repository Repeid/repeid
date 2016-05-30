package org.repeid.services.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/js")
public interface JsResource {

    /**
     * Get repeid.js file for javascript clients
     *
     * @return
     */
    @GET
    @Path("/repeid.js")
    @Produces("text/javascript")
    public Response getJs();

    @GET
    @Path("/{version}/repeid.js")
    @Produces("text/javascript")
    public Response getJsWithVersion(@PathParam("version") String version);

    @GET
    @Path("/repeid.min.js")
    @Produces("text/javascript")
    public Response getMinJs();

    @GET
    @Path("/{version}/repeid.min.js")
    @Produces("text/javascript")
    public Response getMinJsWithVersion(@PathParam("version") String version);

}
