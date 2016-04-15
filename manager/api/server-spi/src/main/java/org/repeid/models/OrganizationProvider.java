package org.repeid.models;

import org.repeid.provider.Provider;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface OrganizationProvider extends Provider {
    // Note: The reason there are so many query methods here is for layering a cache on top of an persistent KeycloakSession

    OrganizationModel createRealm(String name);
    OrganizationModel createRealm(String id, String name);
    OrganizationModel getRealm(String id);
    OrganizationModel getRealmByName(String name);

    /*RoleModel getRoleById(String id, RealmModel realm);
    ApplicationModel getApplicationById(String id, RealmModel realm);
    OAuthClientModel getOAuthClientById(String id, RealmModel realm);
    List<RealmModel> getRealms();*/
    boolean removeRealm(String id);

    void close();
}
