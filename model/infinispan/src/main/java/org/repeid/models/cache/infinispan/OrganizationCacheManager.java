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

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.infinispan.Cache;
import org.infinispan.notifications.Listener;
import org.jboss.logging.Logger;
import org.repeid.models.cache.infinispan.entities.Revisioned;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
@Listener
public class OrganizationCacheManager extends CacheManager {

	protected static final Logger logger = Logger.getLogger(OrganizationCacheManager.class);

	public OrganizationCacheManager(Cache<String, Revisioned> cache, Cache<String, Long> revisions) {
		super(cache, revisions);
	}

	public void organizationInvalidation(String id, Set<String> invalidations) {
		Predicate<Map.Entry<String, Revisioned>> predicate = getRealmInvalidationPredicate(id);
		addInvalidations(predicate, invalidations);
	}

	public Predicate<Map.Entry<String, Revisioned>> getRealmInvalidationPredicate(String id) {
		return null;
	}

	public void clientInvalidation(String id, Set<String> invalidations) {
		addInvalidations(getClientInvalidationPredicate(id), invalidations);
	}

	public Predicate<Map.Entry<String, Revisioned>> getClientInvalidationPredicate(String id) {
		return null;
	}

	public void roleInvalidation(String id, Set<String> invalidations) {
		addInvalidations(getRoleInvalidationPredicate(id), invalidations);

	}

	public Predicate<Map.Entry<String, Revisioned>> getRoleInvalidationPredicate(String id) {
		return null;
	}

	public void legalPersonInvalidation(String id, Set<String> invalidations) {
		addInvalidations(getGroupInvalidationPredicate(id), invalidations);

	}

	public Predicate<Map.Entry<String, Revisioned>> getGroupInvalidationPredicate(String id) {
		return null;
	}

	public void clientTemplateInvalidation(String id, Set<String> invalidations) {
		addInvalidations(getClientTemplateInvalidationPredicate(id), invalidations);

	}

	public Predicate<Map.Entry<String, Revisioned>> getClientTemplateInvalidationPredicate(String id) {
		return null;
	}

	public void organizationRemoval(String id, Set<String> invalidations) {
		Predicate<Map.Entry<String, Revisioned>> predicate = getRealmRemovalPredicate(id);
		addInvalidations(predicate, invalidations);
	}

	public Predicate<Map.Entry<String, Revisioned>> getRealmRemovalPredicate(String id) {
		return null;
	}

	public void clientAdded(String realmId, String id, Set<String> invalidations) {
		addInvalidations(getClientAddedPredicate(realmId), invalidations);
	}

	public Predicate<Map.Entry<String, Revisioned>> getClientAddedPredicate(String realmId) {
		return null;
	}

	public void clientRemoval(String realmId, String id, Set<String> invalidations) {
		Predicate<Map.Entry<String, Revisioned>> predicate = null;
		predicate = getClientRemovalPredicate(realmId, id);
		addInvalidations(predicate, invalidations);
	}

	public Predicate<Map.Entry<String, Revisioned>> getClientRemovalPredicate(String realmId, String id) {
		return null;
	}

	public void roleRemoval(String id, Set<String> invalidations) {
		addInvalidations(getRoleRemovalPredicate(id), invalidations);

	}

	public Predicate<Map.Entry<String, Revisioned>> getRoleRemovalPredicate(String id) {
		return getRoleInvalidationPredicate(id);
	}

	@Override
	protected Predicate<Map.Entry<String, Revisioned>> getInvalidationPredicate(Object object) {
		return null;
	}

	public void naturalPersonInvalidation(String id, Set<String> invalidations) {
		// TODO Auto-generated method stub

	}

	public void documentInvalidation(String id, Set<String> invalidations) {
		// TODO Auto-generated method stub

	}
}
