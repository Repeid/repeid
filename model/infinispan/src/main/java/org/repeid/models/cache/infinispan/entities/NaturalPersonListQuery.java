package org.repeid.models.cache.infinispan.entities;

import org.repeid.models.OrganizationModel;

import java.util.HashSet;
import java.util.Set;

public class NaturalPersonListQuery extends AbstractRevisioned implements NaturalPersonQuery {

	private final Set<String> naturalPersons;
	private final String organization;
	private final String organizationName;

	public NaturalPersonListQuery(Long revisioned, String id, OrganizationModel organization, Set<String> naturalPersons) {
		super(revisioned, id);
		this.organization = organization.getId();
		this.organizationName = organization.getName();
		this.naturalPersons = naturalPersons;
	}

	public NaturalPersonListQuery(Long revisioned, String id, OrganizationModel organization, String naturalPerson) {
		super(revisioned, id);
		this.organization = organization.getId();
		this.organizationName = organization.getName();
		this.naturalPersons = new HashSet<>();
		this.naturalPersons.add(naturalPerson);
	}

	@Override
	public Set<String> getNaturalPersons() {
		return naturalPersons;
	}

	@Override
	public String getOrganization() {
		return organization;
	}

	@Override
	public String toString() {
		return "NaturalPersonListQuery{" + "id='" + getId() + "'" + "organizationName='" + organizationName + '\'' + '}';
	}
}
