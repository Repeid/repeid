package org.repeid.services.resources;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.repeid.models.OrganizationModel;
import org.repeid.representations.idm.PublishedOrganizationRepresentation;
import org.repeid.services.ServicesLogger;

public class PublicOrganizationResourceImpl implements PublicOrganizationResource {

	protected static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

	@Context
	protected UriInfo uriInfo;

	@Context
	protected HttpRequest request;

	@Context
	protected HttpResponse response;

	protected OrganizationModel organization;

	public PublicOrganizationResourceImpl(OrganizationModel organization) {
		this.organization = organization;
	}

	@Override
	public Response accountPreflight() {
		// return Cors.add(request, Response.ok()).auth().preflight().build();
		return null;
	}

	@Override
	public PublishedOrganizationRepresentation getOrganization() {
		// Cors.add(request).allowedOrigins(Cors.ACCESS_CONTROL_ALLOW_ORIGIN_WILDCARD).auth().build(response);
		return organizationRep(organization, uriInfo);
	}

	public static PublishedOrganizationRepresentation organizationRep(OrganizationModel organization, UriInfo uriInfo) {
		PublishedOrganizationRepresentation rep = new PublishedOrganizationRepresentation();
		/*
		 * rep.setRealm(organization.getName()); rep.setTokenServiceUrl(
		 * OIDCLoginProtocolService.tokenServiceBaseUrl(uriInfo).build(
		 * organization.getName()).toString()); rep.setAccountServiceUrl(
		 * AccountService.accountServiceBaseUrl(uriInfo).build(organization.
		 * getName()).toString());
		 * rep.setAdminApiUrl(uriInfo.getBaseUriBuilder().path(AdminRoot.class).
		 * build().toString());
		 * rep.setPublicKeyPem(organization.getPublicKeyPem());
		 * rep.setNotBefore(organization.getNotBefore());
		 */
		return rep;
	}

}
