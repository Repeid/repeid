package org.repeid.models;

import org.repeid.provider.Provider;

public interface OrganizationProvider extends Provider {
    // Note: The reason there are so many query methods here is for layering a cache on top of an persistent RepeidSession
    OrganizationModel createOrganization(String name);

    OrganizationModel createOrganization(String id, String name);

    OrganizationModel getOrganization(String id);

    OrganizationModel getOrganizationByName(String name);

    boolean removeOrganization(String id);

    void close();
}
