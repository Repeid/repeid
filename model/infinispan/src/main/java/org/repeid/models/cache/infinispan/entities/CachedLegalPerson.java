package org.repeid.models.cache.infinispan.entities;

import org.repeid.models.LegalPersonModel;
import org.repeid.models.OrganizationModel;

public class CachedLegalPerson extends AbstractRevisioned implements InOrganization {

	private String organization;
	private String name;
	private String document;
	private String documentNumber;
	private String country;
	private String address;
	private String reference;
	private String phoneNumber;
	private String email;

	public CachedLegalPerson(Long revision, OrganizationModel organization, LegalPersonModel person) {
		super(revision, person.getId());
		this.organization = organization.getId();
		this.name = person.getName();
		this.document = person.getDocument().getId();
		this.documentNumber = person.getDocumentNumber();
		this.country = person.getCountry();
		this.address = person.getAddress();
		this.reference = person.getReference();
		this.phoneNumber = person.getPhoneNumber();
		this.email = person.getEmail();
	}

	public String getOrganization() {
		return organization;
	}

	public String getName() {
		return name;
	}

	public String getDocument() {
		return document;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public String getCountry() {
		return country;
	}

	public String getAddress() {
		return address;
	}

	public String getReference() {
		return reference;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmail() {
		return email;
	}

}
