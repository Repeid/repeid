package org.repeid.services.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.repeid.services.util.CacheControlUtil;
import org.repeid.theme.FreeMarkerUtil;

public class WelcomeResourceImpl implements WelcomeResource {

    @Context
    private UriInfo uriInfo;

    public WelcomeResourceImpl() {

    }

    @Override
    public Response getWelcomePage() throws URISyntaxException {
        String requestUri = uriInfo.getRequestUri().toString();
        if (!requestUri.endsWith("/")) {
            return Response.seeOther(new URI(requestUri + "/")).build();
        } else {
            return createWelcomePage(null, null);
        }
    }

    @Override
    public Response createUser(MultivaluedMap<String, String> formData) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response getResource(String path) {
        // TODO Auto-generated method stub
        return null;
    }

    private Response createWelcomePage(String successMessage, String errorMessage) {
        try {
            //FreeMarkerUtil freeMarkerUtil = new FreeMarkerUtil();            
            ResponseBuilder rb = Response.status(errorMessage == null ? Status.OK : Status.BAD_REQUEST)
                    .entity("Welcome to repeid").cacheControl(CacheControlUtil.noCache());
            return rb.build();
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
