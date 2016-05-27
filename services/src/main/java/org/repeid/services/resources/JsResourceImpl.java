package org.repeid.services.resources;

import java.io.InputStream;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;

import org.repeid.Config;
import org.repeid.common.Version;
import org.repeid.services.resources.JsResource;

public class JsResourceImpl implements JsResource {

    @Override
    public Response getJs() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("repeid.js");
        if (inputStream != null) {
            CacheControl cacheControl = new CacheControl();
            cacheControl.setNoTransform(false);
            cacheControl.setMaxAge(Config.scope("theme").getInt("staticMaxAge", -1));

            return Response.ok(inputStream).type("text/javascript").cacheControl(cacheControl).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Override
    public Response getJsWithVersion(String version) {
        if (!version.equals(Version.RESOURCES_VERSION)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return getJs();
    }

    @Override
    public Response getMinJs() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("repeid.min.js");
        if (inputStream != null) {
            CacheControl cacheControl = new CacheControl();
            cacheControl.setNoTransform(false);
            cacheControl.setMaxAge(Config.scope("theme").getInt("staticMaxAge", -1));

            return Response.ok(inputStream).type("text/javascript").cacheControl(cacheControl).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Override
    public Response getMinJsWithVersion(String version) {
        if (!version.equals(Version.RESOURCES_VERSION)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return getMinJs();
    }

}
