package org.repeid.services.managers;

import org.repeid.models.OrganizationModel;
import org.repeid.models.OrganizationProvider;
import org.repeid.models.RepeidSession;
import org.repeid.representations.idm.OrganizationRepresentation;

public class OrganizationManager {

    protected RepeidSession session;
    protected OrganizationProvider model;
    protected String contextPath = "";
    
    public OrganizationManager(RepeidSession session) {
        this.session = session;
        this.model = session.organizations();
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public RepeidSession getSession() {
        return session;
    }

    public OrganizationModel getOrganization(String id) {
        return model.getOrganization(id);
    }

    public OrganizationModel getOrganizationByName(String name) {
        return model.getOrganizationByName(name);
    }

    public OrganizationModel createOrganization(String name) {
        return createOrganization(name, name);
    }
    
    public OrganizationModel createOrganization(String id, String name) {
        /*if (id == null) id = KeycloakModelUtils.generateId();
        RealmModel realm = model.createRealm(id, name);
        realm.setName(name);

        // setup defaults
        setupRealmDefaults(realm);

        setupMasterAdminManagement(realm);
        setupRealmAdminManagement(realm);
        setupAccountManagement(realm);
        setupBrokerService(realm);
        setupAdminConsole(realm);
        setupAdminConsoleLocaleMapper(realm);
        setupAdminCli(realm);
        setupImpersonationService(realm);
        setupAuthenticationFlows(realm);
        setupRequiredActions(realm);
        setupOfflineTokens(realm);

        return realm;*/
        return null;
    }

    public OrganizationModel importOrganization(OrganizationRepresentation rep) {
        /*String id = rep.getId();
        if (id == null) {
            id = KeycloakModelUtils.generateId();
        }
        RealmModel realm = model.createRealm(id, rep.getRealm());
        realm.setName(rep.getRealm());

        // setup defaults

        setupRealmDefaults(realm);

        boolean postponeMasterClientSetup = postponeMasterClientSetup(rep);
        if (!postponeMasterClientSetup) {
            setupMasterAdminManagement(realm);
        }

        if (!hasRealmAdminManagementClient(rep)) setupRealmAdminManagement(realm);
        if (!hasAccountManagementClient(rep)) setupAccountManagement(realm);

        boolean postponeImpersonationSetup = false;
        if (hasRealmAdminManagementClient(rep)) {
            postponeImpersonationSetup = true;
        } else {
            setupImpersonationService(realm);
        }


        if (!hasBrokerClient(rep)) setupBrokerService(realm);
        if (!hasAdminConsoleClient(rep)) setupAdminConsole(realm);

        boolean postponeAdminCliSetup = false;
        if (!hasAdminCliClient(rep)) {
            if (hasRealmAdminManagementClient(rep)) {
                postponeAdminCliSetup = true;
            } else {
                setupAdminCli(realm);
            }
        }

        if (!hasRealmRole(rep, Constants.OFFLINE_ACCESS_ROLE)) setupOfflineTokens(realm);

        RepresentationToModel.importRealm(session, rep, realm);

        setupAdminConsoleLocaleMapper(realm);

        if (postponeMasterClientSetup) {
            setupMasterAdminManagement(realm);
        }

        // Assert all admin roles are available once import took place. This is needed due to import from previous version where JSON file may not contain all admin roles
        checkMasterAdminManagementRoles(realm);
        checkRealmAdminManagementRoles(realm);

        // Could happen when migrating from older version and I have exported JSON file, which contains "realm-management" client but not "impersonation" client
        // I need to postpone impersonation because it needs "realm-management" client and its roles set
        if (postponeImpersonationSetup) {
            setupImpersonationService(realm);
        }

        if (postponeAdminCliSetup) {
            setupAdminCli(realm);
        }

        setupAuthenticationFlows(realm);
        setupRequiredActions(realm);

        // Refresh periodic sync tasks for configured federationProviders
        List<UserFederationProviderModel> federationProviders = realm.getUserFederationProviders();
        UsersSyncManager usersSyncManager = new UsersSyncManager();
        for (final UserFederationProviderModel fedProvider : federationProviders) {
            usersSyncManager.notifyToRefreshPeriodicSync(session, realm, fedProvider, false);
        }
        return realm;*/
        return null;
    }

}
