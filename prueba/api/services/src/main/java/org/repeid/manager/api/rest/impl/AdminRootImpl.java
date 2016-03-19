package org.repeid.manager.api.rest.impl;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.repeid.manager.api.model.provider.ClientConnection;
import org.repeid.manager.api.model.provider.KeycloakSession;
import org.repeid.manager.api.rest.AdminRoot;
import org.repeid.manager.api.rest.ServerInfoAdminResource;
import org.repeid.manager.api.rest.admin.MaestroResource;
import org.repeid.manager.api.rest.admin.PersonasResource;
import org.repeid.manager.api.rest.admin.TiposDocumentoResource;
import org.repeid.manager.api.rest.impl.admin.MaestroResourceImpl;
import org.repeid.manager.api.rest.impl.admin.PersonasResourceImpl;
import org.repeid.manager.api.rest.impl.admin.TiposDocumentoResourceImpl;
import org.repeid.manager.api.rest.services.ServicesLogger;

/**
 * Root resource for admin console and admin REST API
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */

public class AdminRootImpl implements AdminRoot {

	protected static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

	@Context
	protected UriInfo uriInfo;

	@Context
	protected ClientConnection clientConnection;

	@Context
	protected HttpRequest request;

	@Context
	protected HttpResponse response;

	@Context
	protected KeycloakSession session;

	public AdminRootImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public TiposDocumentoResource getTiposDocumentoResource(HttpHeaders headers) {
		TiposDocumentoResource tiposDocumentoResource = new TiposDocumentoResourceImpl();
		ResteasyProviderFactory.getInstance().injectProperties(tiposDocumentoResource);
		return tiposDocumentoResource;
	}

	@Override
	public PersonasResource getPersonasResource(HttpHeaders headers) {
		PersonasResource personasResource = new PersonasResourceImpl();
		ResteasyProviderFactory.getInstance().injectProperties(personasResource);
		return personasResource;
	}

	@Override
	public MaestroResource getUtilsResource(HttpHeaders headers) {
		MaestroResource maestroResource = new MaestroResourceImpl();
		ResteasyProviderFactory.getInstance().injectProperties(maestroResource);
		return maestroResource;
	}

	public ServerInfoAdminResource getServerInfo(@Context final HttpHeaders headers) {
		ServerInfoAdminResource adminResource = new ServerInfoAdminResourceImpl();
		ResteasyProviderFactory.getInstance().injectProperties(adminResource);
		return adminResource;
	}

}
