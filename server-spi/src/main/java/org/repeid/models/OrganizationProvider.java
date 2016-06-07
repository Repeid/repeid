package org.repeid.models;

import java.util.List;

import org.repeid.migration.MigrationModel;
import org.repeid.provider.Provider;

public interface OrganizationProvider extends Provider {
    // Note: The reason there are so many query methods here is for layering a cache on top of an persistent RepeidSession
    MigrationModel getMigrationModel();
    
    OrganizationModel createOrganization(String name);

    OrganizationModel createOrganization(String id, String name);

    OrganizationModel getOrganization(String id);

    OrganizationModel getOrganizationByName(String name);

    boolean removeOrganization(String id);

    void close();

	List<OrganizationModel> getOrganizations();
}
