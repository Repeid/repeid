package org.repeid.services.resources;

import org.repeid.models.OrganizationModel;

public class OrganizationsResourceImpl implements OrganizationsResource {

	@Override
	public PublicOrganizationResource getOrganizationResource(String name) {
		OrganizationModel realm = init(name);
		PublicRealmResource realmResource = new PublicRealmResource(realm);
		ResteasyProviderFactory.getInstance().injectProperties(realmResource);
		return realmResource;
	}

	@Override
	public Object resolveOrganizationExtension(String organizationName, String extension) {
		// TODO Auto-generated method stub
		return null;
	}

	private OrganizationModel init(String realmName) {
		RealmManager realmManager = new RealmManager(session);
		RealmModel realm = realmManager.getRealmByName(realmName);
		if (realm == null) {
			throw new NotFoundException("Realm does not exist");
		}
		session.getContext().setRealm(realm);
		return realm;
	}

}
