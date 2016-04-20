/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.repeid.models.cache.infinispan;

import org.infinispan.Cache;
import org.jboss.logging.Logger;
import org.repeid.Config;
import org.repeid.connections.infinispan.InfinispanConnectionProvider;
import org.repeid.models.KeycloakSession;
import org.repeid.models.KeycloakSessionFactory;
import org.repeid.models.cache.CacheUserProvider;
import org.repeid.models.cache.CacheUserProviderFactory;
import org.repeid.models.cache.infinispan.entities.Revisioned;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class InfinispanCacheUserProviderFactory implements CacheUserProviderFactory {

    private static final Logger log = Logger.getLogger(InfinispanCacheUserProviderFactory.class);

    protected volatile UserCacheManager userCache;



    @Override
    public CacheUserProvider create(KeycloakSession session) {
        lazyInit(session);
        return new UserCacheSession(userCache, session);
    }

    private void lazyInit(KeycloakSession session) {
        if (userCache == null) {
            synchronized (this) {
                if (userCache == null) {
                    Cache<String, Revisioned> cache = session.getProvider(InfinispanConnectionProvider.class).getCache(InfinispanConnectionProvider.USER_CACHE_NAME);
                    Cache<String, Long> revisions = session.getProvider(InfinispanConnectionProvider.class).getCache(InfinispanConnectionProvider.VERSION_CACHE_NAME);
                    userCache = new UserCacheManager(cache, revisions);
                }
            }
        }
    }

    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return "default";
    }


}
