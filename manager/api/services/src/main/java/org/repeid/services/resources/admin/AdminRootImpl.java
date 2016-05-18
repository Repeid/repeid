package org.repeid.services.resources.admin;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.repeid.models.OrganizationModel;
import org.repeid.models.RepeidSession;
import org.repeid.services.ServicesLogger;
import org.repeid.services.managers.OrganizationManager;
import org.repeid.services.resources.admin.info.ServerInfoAdminResource;

public class AdminRootImpl implements AdminRoot {

    protected static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

    @Context
    protected UriInfo uriInfo;

    @Context
    protected HttpRequest request;

    @Context
    protected HttpResponse response;

    // protected AppAuthManager authManager;
    // protected TokenManager tokenManager;

    @Context
    protected RepeidSession session;

    public AdminRootImpl() {
        // this.tokenManager = new TokenManager();
        // this.authManager = new AppAuthManager();
    }

    @Override
    public Response masterOrganizationAdminConsoleRedirect() {
        OrganizationModel master = new OrganizationManager(session).getRepeidAdminstrationRealm();
        return Response.status(302).location(uriInfo.getBaseUriBuilder().path(AdminRoot.class)
                .path(AdminRoot.class, "getAdminConsole").path("/").build(master.getName())).build();
    }

    @Override
    public Response masterOrganizationAdminConsoleRedirectHtml() {
        return masterOrganizationAdminConsoleRedirect();
    }

    protected OrganizationModel locateOrganization(String name, OrganizationManager organizationManager) {
        OrganizationModel organization = organizationManager.getOrganizationByName(name);
        if (organization == null) {
            throw new NotFoundException("Organization not found. Did you type in a bad URL?");
        }
        return organization;
    }

    @Override
    public AdminConsole getAdminConsole(String name) {
        OrganizationManager organizationManager = new OrganizationManager(session);
        OrganizationModel organization = locateOrganization(name, organizationManager);
        AdminConsole service = new AdminConsoleImpl(organization);
        ResteasyProviderFactory.getInstance().injectProperties(service);
        return service;
    }

    @Override
    public OrganizationsAdminResource getOrganizationsAdmin(HttpHeaders headers) {
        OrganizationsAdminResource adminResource = new OrganizationsResourceImpl();
        ResteasyProviderFactory.getInstance().injectProperties(adminResource);
        return adminResource;
    }

    @Override
    public CommonsResource getCommonsResource(HttpHeaders headers) {
        CommonsResource commonsResource = new CommonsResourceImpl();
        ResteasyProviderFactory.getInstance().injectProperties(commonsResource);
        return commonsResource;
    }

    @Override
    public ServerInfoAdminResource getServerInfo(HttpHeaders headers) {
        ServerInfoAdminResource adminResource = new ServerInfoAdminResourceImpl();
        ResteasyProviderFactory.getInstance().injectProperties(adminResource);
        return adminResource;
    }

}
