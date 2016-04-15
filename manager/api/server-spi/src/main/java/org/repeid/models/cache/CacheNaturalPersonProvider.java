package org.repeid.models.cache;

import org.repeid.models.NaturalPersonProvider;
import org.repeid.models.OrganizationProvider;

public interface CacheNaturalPersonProvider extends NaturalPersonProvider {
    OrganizationProvider getDelegate();

    boolean isEnabled();

    void setEnabled(boolean enabled);

    void registerRealmInvalidation(String id);

    void registerApplicationInvalidation(String id);

    void registerRoleInvalidation(String id);

    void registerOAuthClientInvalidation(String id);

    void registerUserInvalidation(String id);
}
