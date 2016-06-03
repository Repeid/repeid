package org.repeid.services.resources;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/organizations")
public interface RealmsResource {

	// @Path("{realm}/protocol/{protocol}")
	// public Object getProtocol(final @PathParam("realm") String name, final
	// @PathParam("protocol") String protocol);

	// @GET
	// @Path("{realm}/clients/{client_id}/redirect")
	// public Response getRedirect(final @PathParam("realm") String realmName,
	// final @PathParam("client_id") String clientId);

	// @Path("{realm}/login-actions")
	// public LoginActionsService getLoginActionsService(final
	// @PathParam("realm") String name);

	// @Path("{realm}/clients-registrations")
	// public ClientRegistrationService getClientsService(final
	// @PathParam("realm") String name);

	// @Path("{realm}/clients-managements")
	// public ClientsManagementService getClientsManagementService(final
	// @PathParam("realm") String name);

	// @Path("{realm}/account")
	// public AccountService getAccountService(final @PathParam("realm") String
	// name);

	@Path("{organization}")
	public PublicRealmResource getOrganizationResource(final @PathParam("organization") String name);

	// @Path("{realm}/broker")
	// public IdentityBrokerService getBrokerService(final @PathParam("realm")
	// String name);

	// @GET
	// @Path("{realm}/.well-known/{provider}")
	// @Produces(MediaType.APPLICATION_JSON)
	// public Response getWellKnown(final @PathParam("realm") String name,
	// final @PathParam("provider") String providerName);

	/**
	 * A JAX-RS sub-resource locator that uses the
	 * {@link org.repeid.services.resource.OrganizationResourceSPI} to resolve
	 * sub-resources instances given an <code>unknownPath</code>.
	 *
	 * @param extension
	 *            a path that could be to a REST extension
	 * @return a JAX-RS sub-resource instance for the REST extension if found.
	 *         Otherwise null is returned.
	 */
	@Path("{organization}/{extension}")
	public Object resolveOrganizationExtension(@PathParam("organization") String organizationName,
			@PathParam("extension") String extension);

}
