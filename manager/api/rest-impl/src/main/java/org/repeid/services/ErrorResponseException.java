package org.repeid.services;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class ErrorResponseException extends WebApplicationException {

    private static final long serialVersionUID = 1L;

    private final String error;
    private final String errorDescription;
    private final Response.Status status;

    public ErrorResponseException(String error, String errorDescription, Response.Status status) {
        this.error = error;
        this.errorDescription = errorDescription;
        this.status = status;
    }

    @Override
    public Response getResponse() {
        Map<String, String> e = new HashMap<String, String>();
        e.put("error", error);
        if (errorDescription != null) {
            e.put("error_description", errorDescription);
        }
        return Response.status(status).entity(e).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

}
