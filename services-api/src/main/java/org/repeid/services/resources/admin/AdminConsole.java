package org.repeid.services.resources.admin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.cache.NoCache;

public interface AdminConsole {

	/**
	 * Adapter configuration for the admin console for this organization
	 *
	 * @return
	 */
	@Path("config")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@NoCache
	// public ClientManager.InstallationAdapterConfig config();
	public Response config();

	/**
	 * Permission information
	 *
	 * @param headers
	 * @return
	 */
	@Path("whoami")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@NoCache
	public Response whoAmI(final @Context HttpHeaders headers);

	/**
	 * Logout from the admin console
	 *
	 * @return
	 */
	@Path("logout")
	@GET
	@NoCache
	public Response logout();

	/**
	 * Main page of this organization's admin console
	 *
	 * @return
	 * @throws URISyntaxException
	 */
	@GET
	@NoCache
	public Response getMainPage() throws URISyntaxException, IOException;

	@GET
	@Path("{indexhtml: index.html}") // this expression is a hack to get around
										// jaxdoclet generation bug. Doesn't
										// like index.html
	public Response getIndexHtmlRedirect();

	@GET
	@Path("messages.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Properties getMessages(@QueryParam("lang") String lang);

}
