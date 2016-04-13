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

import org.repeid.models.RealmModel;
import org.repeid.models.RealmProvider;
import org.repeid.models.cache.CacheRealmProvider;


/**
 * - the high level architecture of this cache is an invalidation cache.
 * - the cache is manual/custom versioned.  When a model is updated, we remove it from the cache
 * which causes an invalidation message to be sent across the cluster.
 * - We had to do it this way because Infinispan REPEATABLE_READ
 * wouldn't cut it in invalidation mode.  Also, REPEATABLE_READ doesn't work very well on relationships and items that are
 * not in the cache.
 * - There are two Infinispan caches.  One clustered that holds actual objects and a another local one that holds revision
 * numbers of cached objects.  Whenever a cached object is removed (invalidated), the local revision
 * cache number or that key is bumped higher based on a local version counter.  Whenever a cache entry is fetched, this
 * revision number is also fetched and compared against the revision number in the cache entry to see if the cache entry
 * is stale.  Whenever a cache entry is added, this revision number is also checked against the revision cache.
 * - Revision entries are actually never removed (although they could be evicted by cache eviction policies).  The reason for this
 * is that it is possible for a stale object to be inserted if one thread loads and the data is updated in the database before
 * it is added to the cache.  So, we keep the version number around for this.
 * - In a transaction, objects are registered to be invalidated.  If an object is marked for invalidation within a transaction
 * a cached object should never be returned.  An DB adapter should always be returned.
 * - After DB commits, the objects marked for invalidation are invalidated, or rather removed from the cache.  At this time
 * the revision cache entry for this object has its version number bumped.
 * - Whenever an object is marked for invalidation, the cache is also searched for any objects that are related to this object
 * and need to also be evicted/removed.  We use the Infinispan Stream SPI for this.
 *
 * ClientList caches:
 * - lists of clients are cached in a specific cache entry i.e. realm clients, find client by clientId
 * - realm client lists need to be invalidated and evited whenever a client is added or removed from a realm.  RealmProvider
 * now has addClient/removeClient at its top level.  All adapaters should use these methods so that the appropriate invalidations
 * can be registered.
 * - whenever a client is added/removed the realm of the client is added to a listInvalidations set
 * this set must be checked before sending back or caching a cached query.  This check is required to
 * avoid caching an uncommitted removal/add in a query cache.
 * - when a client is removed, any queries that contain that client must also be removed.
 * - a client removal will also cause anything that is contained and cached within that client to be removed
 *
 * Clustered caches:
 * - There is a Infinispan @Listener registered.  If an invalidation event happens, this is treated like
 * the object was removed from the database and will perform evictions based on that assumption.
 * - Eviction events will also cascade other evictions, but not assume this is a db removal.
 * - With an invalidation cache, if you remove an entry on node 1 and this entry does not exist on node 2, node 2 will not receive a @Listener invalidation event.
 * so, hat we have to put a marker entry in the invalidation cache before we read from the DB, so if the DB changes in between reading and adding a cache entry, the cache will be notified and bump
 * the version information.
 *
 * DBs with Repeatable Read:
 * - DBs like MySQL are Repeatable Read by default.  So, if you query a Client for instance, it will always return the same result in the same transaction even if the DB
 * was updated in between these queries.  This makes it possible to store stale cache entries.  To avoid this problem, this class stores the current local version counter
 * at the beginningof the transaction.  Whenever an entry is added to the cache, the current coutner is compared against the counter at the beginning of the tx.  If the current
 * is greater, then don't cache.
 *
 * Groups and Roles:
 * - roles are tricky because of composites.  Composite lists are cached too.  So, when a role is removed
 * we also iterate and invalidate any role or group that contains that role being removed.
 *
 * - any relationship should be resolved from session.realms().  For example if JPA.getClientByClientId() is invoked,
 *  JPA should find the id of the client and then call session.realms().getClientById().  THis is to ensure that the cached
 *  object is invoked and all proper invalidation are being invoked.
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class RealmCacheSession implements CacheRealmProvider {

    @Override
    public RealmModel createRealm(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RealmModel createRealm(String id, String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RealmModel getRealm(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RealmModel getRealmByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removeRealm(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public RealmProvider getDelegate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setEnabled(boolean enabled) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void registerRealmInvalidation(String id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void registerApplicationInvalidation(String id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void registerRoleInvalidation(String id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void registerOAuthClientInvalidation(String id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void registerUserInvalidation(String id) {
        // TODO Auto-generated method stub
        
    }
  
    /*public RealmCacheSession(RealmCacheManager cache, KeycloakSession session) {
       
    }*/

   

}
