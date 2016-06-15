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
import org.repeid.models.cache.infinispan.entities.CachedDocument;
import org.repeid.models.cache.infinispan.entities.CachedOrganization;
import org.repeid.models.enums.PersonType;

public class DocumentAdapter implements DocumentModel {

	protected CachedOrganization cached;
	protected OrganizationCacheSession cacheSession;
	protected OrganizationModel updated;
	protected OrganizationCache cache;
	protected volatile transient PublicKey publicKey;
	protected volatile transient PrivateKey privateKey;
	protected volatile transient Key codeSecretKey;
	protected volatile transient X509Certificate certificate;

	public DocumentAdapter(CachedDocument cached, OrganizationCacheSession cacheSession,
			OrganizationModel organization) {
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAbbreviature() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAbbreviature(String abbreviature) {
		// TODO Auto-generated method stub
		
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
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSize(int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PersonType getPersonType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPersonType(PersonType personType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public void invalidate() {
		// TODO Auto-generated method stub
		
	}

	

}
