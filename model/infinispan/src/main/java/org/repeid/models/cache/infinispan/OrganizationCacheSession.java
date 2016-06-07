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

import org.jboss.logging.Logger;
import org.repeid.migration.MigrationModel;
import org.repeid.models.OrganizationModel;
import org.repeid.models.OrganizationProvider;
import org.repeid.models.RepeidSession;
import org.repeid.models.cache.CacheOrganizationProvider;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OrganizationCacheSession implements CacheOrganizationProvider {

	//protected RealmCacheManager cache;
	protected RepeidSession session;
	protected OrganizationProvider delegate;
	protected boolean transactionActive;
	protected boolean setRollbackOnly;

	public OrganizationCacheSession(/*RealmCacheManager cache, RepeidSession session*/) {
		/*this.cache = cache;
		this.session = session;
		this.startupRevision = cache.getCurrentCounter();
		session.getTransaction().enlistPrepare(getPrepareTransaction());
		session.getTransaction().enlistAfterCompletion(getAfterTransaction());*/
	}

	@Override
	public OrganizationProvider getDelegate() {
		/*if (!transactionActive)
			throw new IllegalStateException("Cannot access delegate without a transaction");
		if (delegate != null)
			return delegate;*/
		delegate = session.getProvider(OrganizationProvider.class);
		return delegate;
	}
	
	@Override
    public MigrationModel getMigrationModel() {
        return getDelegate().getMigrationModel();
    }

	@Override
	public List<OrganizationModel> getOrganizations() {
		// Retrieve realms from backend
		List<OrganizationModel> backendRealms = getDelegate().getOrganizations();

		// Return cache delegates to ensure cache invalidated during write
		// operations
		List<OrganizationModel> cachedRealms = new LinkedList<OrganizationModel>();
		for (OrganizationModel realm : backendRealms) {
			// OrganizationModel cached = getRealm(realm.getId());
			// cachedRealms.add(cached);
		}
		// return cachedRealms;
		return backendRealms;
	}

	@Override
	public OrganizationModel createOrganization(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrganizationModel createOrganization(String id, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrganizationModel getOrganization(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrganizationModel getOrganizationByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeOrganization(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerOrganizationInvalidation(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerDocumentInvalidation(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerNaturalPersonInvalidation(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerLegalPersonInvalidation(String id) {
		// TODO Auto-generated method stub

	}

}
