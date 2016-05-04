package org.repeid.services.resources.admin;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.NoLogWebApplicationException;

import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.repeid.models.RepeidSession;
import org.repeid.services.ServicesLogger;
import org.repeid.services.resources.admin.AdminConsole;
import org.repeid.services.resources.admin.AdminRoot;
import org.repeid.services.resources.admin.CommonsResource;
import org.repeid.services.resources.admin.OrganizationsAdminResource;
import org.repeid.services.resources.admin.ServerInfoAdminResource;

public class AdminRootImpl implements AdminRoot {

    protected static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

    @Context
    protected UriInfo uriInfo;

    //@Context
    //protected ClientConnection clientConnection;

    @Context
    protected HttpRequest request;

    @Context
    protected HttpResponse response;

    //protected AppAuthManager authManager;
    //protected TokenManager tokenManager;

    @Context
    protected RepeidSession session;

    public AdminRootImpl() {

    }

    @Override
    public Response masterRealmAdminConsoleRedirect() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response masterRealmAdminConsoleRedirectHtml() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AdminConsole getAdminConsole(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OrganizationsAdminResource getRealmsAdmin(HttpHeaders headers) {
        OrganizationsAdminResource adminResource = new OrganizationsResourceImpl();
        ResteasyProviderFactory.getInstance().injectProperties(adminResource);
        return adminResource;
    }

    @Override
    public CommonsResource getUtilsResource(HttpHeaders headers) {
        return new CommonsResourceImpl();
    }

    @Override
    public ServerInfoAdminResource getServerInfo(HttpHeaders headers) {
        // TODO Auto-generated method stub
        return null;
    }

}
