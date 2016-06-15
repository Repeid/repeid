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
import org.repeid.models.DocumentModel;
import org.repeid.models.LegalPersonModel;
import org.repeid.models.LegalPersonProvider;
import org.repeid.models.OrganizationModel;
import org.repeid.models.OrganizationProvider;
import org.repeid.models.RepeidSession;
import org.repeid.models.cache.CacheLegalPersonProvider;
import org.repeid.models.cache.CacheOrganizationProvider;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LegalPersonCacheSession implements CacheLegalPersonProvider {

	//protected RealmCacheManager cache;
	protected RepeidSession session;
	protected OrganizationProvider delegate;
	protected boolean transactionActive;
	protected boolean setRollbackOnly;

	public LegalPersonCacheSession(LegalPersonCacheManager cache, RepeidSession session) {
		/*this.cache = cache;
		this.session = session;
		this.startupRevision = cache.getCurrentCounter();
		session.getTransaction().enlistPrepare(getPrepareTransaction());
		session.getTransaction().enlistAfterCompletion(getAfterTransaction());*/
	}

	@Override
	public LegalPersonModel addLegalPerson(OrganizationModel organization, String id, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LegalPersonModel addLegalPerson(OrganizationModel organization, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeLegalPerson(OrganizationModel organization, LegalPersonModel person) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LegalPersonModel getLegalPersonById(String id, OrganizationModel organization) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LegalPersonModel getLegalPersonByDocument(DocumentModel document, String documentNumber,
			OrganizationModel organization) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLegalPersonCount(OrganizationModel organization) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<LegalPersonModel> getLegalPersons(OrganizationModel organization, int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LegalPersonModel> searchForLegalPerson(String search, OrganizationModel organization) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LegalPersonModel> searchForLegalPerson(String search, OrganizationModel organization, int firstResult,
			int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LegalPersonModel> searchForLegalPersonByAttributes(Map<String, String> attributes,
			OrganizationModel organization) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LegalPersonModel> searchForLegalPersonByAttributes(Map<String, String> attributes,
			OrganizationModel organization, int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void preRemove(OrganizationModel organization) {
		// TODO Auto-generated method stub
		
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
	public LegalPersonProvider getDelegate() {
		// TODO Auto-generated method stub
		return null;
	}

	

	

}
