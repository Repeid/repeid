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

package org.repeid.models.sessions.infinispan;

import org.infinispan.Cache;
import org.repeid.models.ClientInitialAccessModel;
import org.repeid.models.KeycloakSession;
import org.repeid.models.RealmModel;
import org.repeid.models.sessions.infinispan.entities.ClientInitialAccessEntity;
import org.repeid.models.sessions.infinispan.entities.SessionEntity;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class ClientInitialAccessAdapter implements ClientInitialAccessModel {

    private final KeycloakSession session;
    private final InfinispanUserSessionProvider provider;
    private final Cache<String, SessionEntity> cache;
    private final RealmModel realm;
    private final ClientInitialAccessEntity entity;

    public ClientInitialAccessAdapter(KeycloakSession session, InfinispanUserSessionProvider provider, Cache<String, SessionEntity> cache, RealmModel realm, ClientInitialAccessEntity entity) {
        this.session = session;
        this.provider = provider;
        this.cache = cache;
        this.realm = realm;
        this.entity = entity;
    }

    @Override
    public String getId() {
        return entity.getId();
    }

    @Override
    public RealmModel getRealm() {
        return realm;
    }

    @Override
    public int getTimestamp() {
        return entity.getTimestamp();
    }

    @Override
    public int getExpiration() {
        return entity.getExpiration();
    }

    @Override
    public int getCount() {
        return entity.getCount();
    }

    @Override
    public int getRemainingCount() {
        return entity.getRemainingCount();
    }

    @Override
    public void decreaseRemainingCount() {
        entity.setRemainingCount(entity.getRemainingCount() - 1);
        update();
    }

    void update() {
        provider.getTx().replace(cache, entity.getId(), entity);
    }

}
