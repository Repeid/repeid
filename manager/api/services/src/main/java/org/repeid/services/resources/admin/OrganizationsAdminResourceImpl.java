package org.repeid.services.resources.admin;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.repeid.models.AdminRoles;
import org.repeid.models.ModelDuplicateException;
import org.repeid.models.OrganizationModel;
import org.repeid.models.RepeidSession;
import org.repeid.representations.idm.OrganizationRepresentation;
import org.repeid.services.ErrorResponse;
import org.repeid.services.ServicesLogger;
import org.repeid.services.managers.OrganizationManager;
import org.repeid.services.resources.RepeidApplication;
import org.repeid.services.resources.admin.OrganizationAdminResource;
import org.repeid.services.resources.admin.OrganizationsAdminResource;

public class OrganizationsAdminResourceImpl implements OrganizationsAdminResource {

	protected static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

	protected AdminAuth auth;
	// protected TokenManager tokenManager;

	@Context
	protected RepeidSession session;

	@Context
	protected RepeidApplication repeid;

	// @Context
	// protected ClientConnection clientConnection;

	public OrganizationsAdminResourceImpl() {

	}

	@Override
	public List<OrganizationRepresentation> getOrganizations() {
		OrganizationManager organizationManager = new OrganizationManager(session);
		List<OrganizationRepresentation> reps = new ArrayList<>();
		if (auth.getOrganization().equals(organizationManager.getRepeidAdminstrationRealm())) {
			List<OrganizationModel> realms = session.organizations().getOrganizations();
			for (OrganizationModel realm : realms) {
				addRealmRep(reps, realm, realm.getMasterAdminClient());
			}
		} else {
			ClientModel adminApp = auth.getOrganization()
					.getClientByClientId(organizationManager.getRealmAdminClientId(auth.getOrganization()));
			addRealmRep(reps, auth.getOrganization(), adminApp);
		}
		logger.debug(("getRealms()"));
		return reps;
	}

	@Override
	public OrganizationAdminResource getOrganizationAdmin(HttpHeaders headers, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response importOrganization(UriInfo uriInfo, OrganizationRepresentation rep) {
		System.out.println(session);
		OrganizationManager organizationManager = new OrganizationManager(session);
		organizationManager.setContextPath(repeid.getContextPath());

		logger.debugv("importRealm: {0}", rep.getName());

		try {
			OrganizationModel organization = organizationManager.importOrganization(rep);
			// logger.debugv("imported organization success, sending back: {0}",
			// location.toString());

			return Response.ok().build();
		} catch (ModelDuplicateException e) {
			return ErrorResponse.exists("Organization " + rep.getName() + " already exists");
		}
	}

	protected void addRealmRep(List<OrganizationRepresentation> reps, OrganizationModel organization, ClientModel realmManagementClient) {
		if (!auth.hasOneOfAppRole(realmManagementClient, AdminRoles.ALL_REALM_ROLES)) {
			throw new ForbiddenException();
		}

		if (auth.hasAppRole(realmManagementClient, AdminRoles.VIEW_REALM)) {
			reps.add(ModelToRepresentation.toRepresentation(organization, false));
		} else if (auth.hasOneOfAppRole(realmManagementClient, AdminRoles.ALL_REALM_ROLES)) {
			OrganizationRepresentation rep = new OrganizationRepresentation();
			rep.setRealm(organization.getName());
			reps.add(rep);
		}
	}

}
