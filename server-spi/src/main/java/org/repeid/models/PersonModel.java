package org.repeid.models;

public interface PersonModel {

	OrganizationModel getOrganization();

	DocumentModel getDocument();

	String getDocumentNumber();

	String getCountry();

	void setCountry(String country);

	String getAddress();

	void setAddress(String address);

	String getReference();

	void setReference(String reference);

	String getPhoneNumber();

	void setPhoneNumber(String phoneNumber);

	String getEmail();

	void setEmail(String email);

}
