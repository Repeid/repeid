package org.repeid.services.resources.admin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.repeid.models.OrganizationModel;
import org.repeid.services.ServicesLogger;
import org.repeid.services.resources.RepeidApplication;

public class AdminConsoleImpl implements AdminConsole {

    protected static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

    @Context
    protected UriInfo uriInfo;

    @Context
    protected RepeidApplication repeid;

    // protected AppAuthManager authManager;
    protected OrganizationModel organization;

    public AdminConsoleImpl(OrganizationModel organization) {
        this.organization = organization;
        // this.authManager = new AppAuthManager();
    }

    @Override
    public Response config() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response whoAmI(HttpHeaders headers) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response logout() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response getMainPage() throws URISyntaxException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response getIndexHtmlRedirect() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Properties getMessages(String lang) {
        // TODO Auto-generated method stub
        return null;
    }

}
