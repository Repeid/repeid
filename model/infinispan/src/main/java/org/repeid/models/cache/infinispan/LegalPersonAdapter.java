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
import org.repeid.models.LegalPersonModel;
import org.repeid.models.OrganizationModel;
import org.repeid.models.cache.infinispan.entities.CachedOrganization;

public class LegalPersonAdapter implements LegalPersonModel {

	protected CachedOrganization cached;
	protected OrganizationCacheSession cacheSession;
	protected OrganizationModel updated;
	protected OrganizationCache cache;
	protected volatile transient PublicKey publicKey;
	protected volatile transient PrivateKey privateKey;
	protected volatile transient Key codeSecretKey;
	protected volatile transient X509Certificate certificate;

	public LegalPersonAdapter(CachedOrganization cached, OrganizationCacheSession cacheSession) {
		this.cached = cached;
		this.cacheSession = cacheSession;
	}

	@Override
	public OrganizationModel getOrganization() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentModel getDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCountry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCountry(String country) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAddress(String address) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReference(String reference) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPhoneNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPhoneNumber(String phoneNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEmail(String email) {
		// TODO Auto-generated method stub
		
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

	public void invalidate() {
		// TODO Auto-generated method stub
		
	}


}
