package org.repeid.services.resources.admin;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.repeid.services.resources.admin.AdminConsole;
import org.repeid.services.resources.admin.AdminRoot;
import org.repeid.services.resources.admin.CommonsResource;
import org.repeid.services.resources.admin.OrganizationsAdminResource;
import org.repeid.services.resources.admin.ServerInfoAdminResource;

public class AdminRootImpl implements AdminRoot {

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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommonsResource getUtilsResource(HttpHeaders headers) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ServerInfoAdminResource getServerInfo(HttpHeaders headers) {
        // TODO Auto-generated method stub
        return null;
    }

}
