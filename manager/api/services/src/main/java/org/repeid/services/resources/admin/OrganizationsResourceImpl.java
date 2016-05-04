package org.repeid.services.resources.admin;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.repeid.models.ModelDuplicateException;
import org.repeid.models.RepeidSession;
import org.repeid.representations.idm.OrganizationRepresentation;
import org.repeid.services.ServicesLogger;
import org.repeid.services.resources.RepeidApplication;
import org.repeid.services.resources.admin.OrganizationAdminResource;
import org.repeid.services.resources.admin.OrganizationsAdminResource;

public class OrganizationsResourceImpl implements OrganizationsAdminResource {

    protected static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

    //protected AdminAuth auth;
    //protected TokenManager tokenManager;

    @Context
    protected RepeidSession session;

    @Context
    protected RepeidApplication repeid;

    //@Context
    //protected ClientConnection clientConnection;

    public OrganizationsResourceImpl() {

    }

    @Override
    public List<OrganizationRepresentation> getRealms() {
        /*RealmManager realmManager = new RealmManager(session);
        List<RealmRepresentation> reps = new ArrayList<>();
        if (auth.getRealm().equals(realmManager.getKeycloakAdminstrationRealm())) {
            List<RealmModel> realms = session.realms().getRealms();
            for (RealmModel realm : realms) {
                addRealmRep(reps, realm, realm.getMasterAdminClient());
            }
        } else {
            ClientModel adminApp = auth.getRealm().getClientByClientId(realmManager.getRealmAdminClientId(auth.getRealm()));
            addRealmRep(reps, auth.getRealm(), adminApp);
        }
        logger.debug(("getRealms()"));
        return reps;*/
        return new ArrayList<>();
    }

    @Override
    public Response importRealm(UriInfo uriInfo, OrganizationRepresentation rep) {
       /* RealmManager realmManager = new RealmManager(session);
        realmManager.setContextPath(keycloak.getContextPath());
        if (!auth.getRealm().equals(realmManager.getKeycloakAdminstrationRealm())) {
            throw new ForbiddenException();
        }
        if (!auth.hasRealmRole(AdminRoles.CREATE_REALM)) {
            throw new ForbiddenException();
        }

        logger.debugv("importRealm: {0}", rep.getRealm());

        try {
            RealmModel realm = realmManager.importRealm(rep);
            grantPermissionsToRealmCreator(realm);

            URI location = AdminRoot.realmsUrl(uriInfo).path(realm.getName()).build();
            logger.debugv("imported realm success, sending back: {0}", location.toString());

            return Response.created(location).build();
        } catch (ModelDuplicateException e) {
            return ErrorResponse.exists("Realm " + rep.getRealm() + " already exists");
        }*/

        return null;
    }

    @Override
    public OrganizationAdminResource getRealmAdmin(HttpHeaders headers, String name) {
        // TODO Auto-generated method stub
        return null;
    }

}
