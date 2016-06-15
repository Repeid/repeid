package org.repeid.models.cache.infinispan.entities;

import org.repeid.models.DocumentModel;
import org.repeid.models.OrganizationModel;

public class CachedDocument extends AbstractRevisioned implements InOrganization {

	private String organization;
	private String abbreviation;
	private String name;
	private int size;
	private String personType;
	private boolean enabled;

	public CachedDocument(Long revision, DocumentModel document, OrganizationModel organization) {
		super(revision, document.getId());
		this.organization = organization.getId();
		this.abbreviation = document.getAbbreviature();
		this.name = document.getName();
		this.size = document.getSize();
		this.personType = document.getPersonType().toString();
		this.enabled = document.isEnabled();
	}

	public String getOrganization() {
		return organization;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public String getPersonType() {
		return personType;
	}

	public boolean isEnabled() {
		return enabled;
	}

}
