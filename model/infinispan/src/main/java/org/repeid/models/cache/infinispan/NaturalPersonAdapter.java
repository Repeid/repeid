package org.repeid.models.cache.infinispan;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
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
import org.repeid.models.NaturalPersonModel;
import org.repeid.models.OrganizationModel;
import org.repeid.models.cache.infinispan.entities.CachedOrganization;
import org.repeid.models.enums.Gender;
import org.repeid.models.enums.MaritalStatus;

public class NaturalPersonAdapter implements NaturalPersonModel {

	protected CachedOrganization cached;
	protected OrganizationCacheSession cacheSession;
	protected OrganizationModel updated;
	protected OrganizationCache cache;
	protected volatile transient PublicKey publicKey;
	protected volatile transient PrivateKey privateKey;
	protected volatile transient Key codeSecretKey;
	protected volatile transient X509Certificate certificate;

	public NaturalPersonAdapter(CachedOrganization cached, OrganizationCacheSession cacheSession) {
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
	public String getFirstName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFirstName(String firstName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getMiddleName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMiddleName(String middleName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLastName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastName(String lastName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LocalDate getDateBirth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDateBirth(LocalDate date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Gender getGender() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGender(Gender gender) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MaritalStatus getMarriageStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMarriageStatus(MaritalStatus marriageStatus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getJob() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setJob(String job) {
		// TODO Auto-generated method stub
		
	}

	public void invalidate() {
		// TODO Auto-generated method stub
		
	}

	

}
