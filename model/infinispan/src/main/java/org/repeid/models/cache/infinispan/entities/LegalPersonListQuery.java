package org.repeid.models.cache.infinispan.entities;

import org.repeid.models.OrganizationModel;

import java.util.HashSet;
import java.util.Set;

public class LegalPersonListQuery extends AbstractRevisioned implements LegalPersonQuery {

	private final Set<String> legalPersons;
	private final String organization;
	private final String organizationName;

	public LegalPersonListQuery(Long revisioned, String id, OrganizationModel organization, Set<String> legalPersons) {
		super(revisioned, id);
		this.organization = organization.getId();
		this.organizationName = organization.getName();
		this.legalPersons = legalPersons;
	}

	public LegalPersonListQuery(Long revisioned, String id, OrganizationModel organization, String legalPerson) {
		super(revisioned, id);
		this.organization = organization.getId();
		this.organizationName = organization.getName();
		this.legalPersons = new HashSet<>();
		this.legalPersons.add(legalPerson);
	}

	@Override
	public Set<String> getLegalPersons() {
		return legalPersons;
	}

	@Override
	public String getOrganization() {
		return organization;
	}

	@Override
	public String toString() {
		return "LegalPersonListQuery{" + "id='" + getId() + "'" + "organizationName='" + organizationName + '\'' + '}';
	}
}
