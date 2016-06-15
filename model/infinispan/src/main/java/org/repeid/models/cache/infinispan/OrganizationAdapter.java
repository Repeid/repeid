package org.repeid.models.cache.infinispan;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.repeid.Config;
import org.repeid.models.DocumentModel;
import org.repeid.models.OrganizationModel;
import org.repeid.models.cache.infinispan.entities.CachedOrganization;

public class OrganizationAdapter implements OrganizationModel {

	protected CachedOrganization cached;
	protected OrganizationCacheSession cacheSession;
	protected OrganizationModel updated;
	protected OrganizationCache cache;
	protected volatile transient PublicKey publicKey;
	protected volatile transient PrivateKey privateKey;
	protected volatile transient Key codeSecretKey;
	protected volatile transient X509Certificate certificate;

	public OrganizationAdapter(CachedOrganization cached, OrganizationCacheSession cacheSession) {
		this.cached = cached;
		this.cacheSession = cacheSession;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
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
	public DocumentModel addDocument(String abbreviation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentModel addDocument(String id, String abbreviation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentModel getDocumentById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeDocument(DocumentModel document) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeDocumentById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<DocumentModel> getDocuments() {
		// TODO Auto-generated method stub
		return null;
	}

	public void invalidate() {
		// TODO Auto-generated method stub
		
	}



}
