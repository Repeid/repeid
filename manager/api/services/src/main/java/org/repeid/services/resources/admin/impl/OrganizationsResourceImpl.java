package org.repeid.services.resources.admin.impl;

import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.keycloak.representations.idm.OrganizationRepresentation;
import org.repeid.services.resources.admin.OrganizationAdminResource;
import org.repeid.services.resources.admin.OrganizationsAdminResource;

public class OrganizationsResourceImpl implements OrganizationsAdminResource {

    @Override
    public List<OrganizationRepresentation> getRealms() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response importRealm(UriInfo uriInfo, OrganizationRepresentation rep) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OrganizationAdminResource getRealmAdmin(HttpHeaders headers, String name) {
        // TODO Auto-generated method stub
        return null;
    }

}
