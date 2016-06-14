package org.repeid.models.cache.infinispan.entities;

import java.time.LocalDate;

import org.repeid.models.NaturalPersonModel;
import org.repeid.models.OrganizationModel;

public class CachedNaturalPerson extends AbstractRevisioned implements InOrganization {

	private String organization;
	private String document;
	private String documentNumber;
	private String country;
	private String address;
	private String reference;
	private String phoneNumber;
	private String email;
	
	private String firstName;
	private String middleName;
	private String lastName;
	private LocalDate dateBirth;
	private String gender;
	private String marriageStatus;
	private String job;
	
	public CachedNaturalPerson(Long revision, OrganizationModel organization, NaturalPersonModel person) {
		super(revision, person.getId());
		this.organization = organization.getId();
		this.document = person.getDocument().getId();
		this.documentNumber = person.getDocumentNumber();
		this.country = person.getCountry();
		this.address = person.getAddress();
		this.reference = person.getReference();
		this.phoneNumber = person.getPhoneNumber();
		this.email = person.getEmail();
		
		this.firstName = person.getFirstName();
		this.middleName = person.getMiddleName();
		this.lastName = person.getLastName();
		this.dateBirth = person.getDateBirth();
		this.gender = person.getEmail();
		this.marriageStatus = person.getMarriageStatus().toString();
		this.job = person.getJob();
		
	}

	public String getOrganization() {
		return organization;
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

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public LocalDate getDateBirth() {
		return dateBirth;
	}

	public String getGender() {
		return gender;
	}

	public String getMarriageStatus() {
		return marriageStatus;
	}

	public String getJob() {
		return job;
	}

}
