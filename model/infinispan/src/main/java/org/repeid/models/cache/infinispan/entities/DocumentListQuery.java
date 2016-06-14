package org.repeid.models.cache.infinispan.entities;

import org.repeid.models.OrganizationModel;

import java.util.HashSet;
import java.util.Set;

public class DocumentListQuery extends AbstractRevisioned implements DocumentQuery {

	private final Set<String> documents;
	private final String organization;
	private final String organizationName;

	public DocumentListQuery(Long revisioned, String id, OrganizationModel organization, Set<String> documents) {
		super(revisioned, id);
		this.organization = organization.getId();
		this.organizationName = organization.getName();
		this.documents = documents;
	}

	public DocumentListQuery(Long revisioned, String id, OrganizationModel organization, String document) {
		super(revisioned, id);
		this.organization = organization.getId();
		this.organizationName = organization.getName();
		this.documents = new HashSet<>();
		this.documents.add(document);
	}

	@Override
	public Set<String> getDocuments() {
		return documents;
	}

	@Override
	public String getOrganization() {
		return organization;
	}

	@Override
	public String toString() {
		return "DocumentListQuery{" + "id='" + getId() + "'" + "organizationName='" + organizationName + '\'' + '}';
	}
}
