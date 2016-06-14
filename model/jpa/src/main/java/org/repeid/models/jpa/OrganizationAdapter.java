package org.repeid.models.jpa;

import java.util.Set;

import org.jboss.logging.Logger;
import org.repeid.models.DocumentModel;
import org.repeid.models.OrganizationModel;
import org.repeid.models.RepeidSession;
import org.repeid.models.jpa.entities.OrganizationEntity;

import javax.persistence.EntityManager;

public class OrganizationAdapter implements OrganizationModel, JpaModel<OrganizationEntity> {

	protected static final Logger logger = Logger.getLogger(OrganizationAdapter.class);
	protected OrganizationEntity organization;
	protected EntityManager em;
	protected RepeidSession session;

	public OrganizationAdapter(RepeidSession session, EntityManager em, OrganizationEntity organization) {
		this.session = session;
		this.em = em;
		this.organization = organization;
	}

	@Override
	public OrganizationEntity getEntity() {
		return organization;
	}

	@Override
	public String getId() {
		return organization.getId();
	}

	@Override
	public String getName() {
		return organization.getName();
	}

	@Override
	public void setName(String name) {
		organization.setName(name);
		em.flush();
	}

	@Override
	public boolean isEnabled() {
		return organization.isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled) {
		organization.setEnabled(enabled);
	}

	@Override
	public DocumentModel addDocument(String abbreviation) {
		return session.organizations().addOrganizationDocument(this, abbreviation);
	}

	@Override
	public DocumentModel addDocument(String id, String abbreviation) {
		return session.organizations().addOrganizationDocument(this, id, abbreviation);
	}

	@Override
	public DocumentModel getDocumentById(String id) {
		return session.organizations().getDocumentById(id, this);
	}
		
	@Override
	public boolean removeDocument(DocumentModel document) {
		return session.organizations().removeDocument(this, document);
	}

	@Override
	public boolean removeDocumentById(String id) {
		DocumentModel document = getDocumentById(id);
        if (document == null) return false;
        //return document.getContainer().removeRole(document);
        return session.organizations().removeDocument(this, document);
	}
	
	@Override
	public Set<DocumentModel> getDocuments() {
		return session.organizations().getOrganizationDocuments(this);
	}

}
