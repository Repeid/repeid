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
import org.keycloak.Config;
import org.keycloak.Config.Scope;
import org.repeid.models.RepeidSession;
import org.repeid.models.cache.CacheRealmProvider;
import org.repeid.models.cache.CacheRealmProviderFactory;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class InfinispanCacheRealmProviderFactory implements CacheRealmProviderFactory {

    @Override
    public CacheRealmProvider create(RepeidSession session) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void init(Scope config) {
        // TODO Auto-generated method stub

    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getId() {
        return "default";
    }

}
