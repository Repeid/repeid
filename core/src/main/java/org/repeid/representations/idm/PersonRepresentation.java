package org.repeid.representations.idm;

public abstract class PersonRepresentation {

	protected OrganizationRepresentation organization;
	protected DocumentRepresentation document;
	protected String documentNumber;
	protected String country;
	protected String address;
	protected String reference;
	protected String phoneNumber;
	protected String email;

	public OrganizationRepresentation getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationRepresentation organization) {
		this.organization = organization;
	}

	public DocumentRepresentation getDocument() {
		return document;
	}

	public void setDocument(DocumentRepresentation document) {
		this.document = document;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
