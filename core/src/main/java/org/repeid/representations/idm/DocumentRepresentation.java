package org.repeid.representations.idm;

public class DocumentRepresentation {

	private String id;
	private String abbreviation;
	private String name;
	private String personType;
	private boolean enabled;
	private OrganizationRepresentation organization;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public OrganizationRepresentation getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationRepresentation organization) {
		this.organization = organization;
	}

}
