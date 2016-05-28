package org.repeid.services.resources.admin;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.repeid.manager.api.beans.representations.idm.AdminEventRepresentation;
import org.repeid.manager.api.beans.representations.idm.EventRepresentation;
import org.repeid.manager.api.beans.representations.idm.OrganizationEventsConfigRepresentation;
import org.repeid.models.AdminRoles;
import org.repeid.models.ModelDuplicateException;
import org.repeid.models.OrganizationModel;
import org.repeid.models.RepeidSession;
import org.repeid.models.utils.ModelToRepresentation;
import org.repeid.representations.idm.OrganizationRepresentation;
import org.repeid.services.ErrorResponse;
import org.repeid.services.ServicesLogger;
import org.repeid.services.managers.OrganizationManager;
import org.repeid.services.resources.RepeidApplication;
import org.repeid.services.resources.admin.OrganizationAdminResource;
import org.repeid.services.resources.admin.OrganizationsAdminResource;

public class OrganizationAdminResourceImpl implements OrganizationAdminResource {

	@Override
	public OrganizationRepresentation getOrganization() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response updateRealm(OrganizationRepresentation rep) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOrganization() {
		// TODO Auto-generated method stub

	}

	@Override
	public DocumentsResource getDocumentsResource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonsResource getPersonsResource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrganizationEventsConfigRepresentation getOrganizationEventsConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateOrganizationEventsConfig(OrganizationEventsConfigRepresentation rep) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<EventRepresentation> getEvents(List<String> types, String client, String user, String dateFrom,
			String dateTo, String ipAddress, Integer firstResult, Integer maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AdminEventRepresentation> getEvents(List<String> operationTypes, String authRealm, String authClient,
			String authUser, String authIpAddress, String resourcePath, String dateFrom, String dateTo,
			Integer firstResult, Integer maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearEvents() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearAdminEvents() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearOrganizationCache() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearPersonCache() {
		// TODO Auto-generated method stub

	}

}
