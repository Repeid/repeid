package org.repeid.services.resources;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.repeid.models.OrganizationModel;
import org.repeid.models.RepeidSession;
import org.repeid.services.ServicesLogger;
import org.repeid.services.managers.OrganizationManager;
import org.repeid.services.resource.OrganizationResourceProvider;

public class OrganizationsResourceImpl implements RealmsResource {

	protected static ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

	@Context
	protected RepeidSession session;

	@Override
	public PublicRealmResource getOrganizationResource(String name) {
		OrganizationModel organization = init(name);
		PublicRealmResource organizationResource = new PublicOrganizationResourceImpl(organization);
		ResteasyProviderFactory.getInstance().injectProperties(organizationResource);
		return organizationResource;
	}

	@Override
	public Object resolveOrganizationExtension(String organizationName, String extension) {
		OrganizationResourceProvider provider = session.getProvider(OrganizationResourceProvider.class, extension);
		if (provider != null) {
			init(organizationName);
			Object resource = provider.getResource();
			if (resource != null) {
				return resource;
			}
		}
		throw new NotFoundException();
	}

	private OrganizationModel init(String organizationName) {
		OrganizationManager organizationManager = new OrganizationManager(session);
		OrganizationModel organization = organizationManager.getOrganizationByName(organizationName);
		if (organization == null) {
			throw new NotFoundException("Organization does not exist");
		}
		session.getContext().setOrganization(organization);
		return organization;
	}

}
