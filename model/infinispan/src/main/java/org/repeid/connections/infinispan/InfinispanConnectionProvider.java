package org.repeid.connections.infinispan;

import org.infinispan.Cache;
import org.repeid.provider.Provider;

public interface InfinispanConnectionProvider extends Provider {

    public static final String VERSION_CACHE_NAME = "organizationVersions";
    static final String ORGANIZATION_CACHE_NAME = "organization";
    static final String NATURALPERSON_CACHE_NAME = "naturalPerson";
    static final String LEGALPERSON_CACHE_NAME = "legalPerson";
    /*static final String USER_CACHE_NAME = "users";
    static final String SESSION_CACHE_NAME = "sessions";
    static final String OFFLINE_SESSION_CACHE_NAME = "offlineSessions";
    static final String LOGIN_FAILURE_CACHE_NAME = "loginFailures";*/
    static final String WORK_CACHE_NAME = "work";

    <K, V> Cache<K, V> getCache(String name);

}
