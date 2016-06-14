package org.repeid.models.jpa;

import javax.persistence.EntityManager;

import org.jboss.logging.Logger;
import org.repeid.models.DocumentModel;
import org.repeid.models.OrganizationModel;
import org.repeid.models.RepeidSession;
import org.repeid.models.enums.PersonType;
import org.repeid.models.jpa.entities.DocumentEntity;

public class DocumentAdapter implements DocumentModel, JpaModel<DocumentEntity> {

	protected static final Logger logger = Logger.getLogger(DocumentAdapter.class);

	protected OrganizationModel organization;
	protected DocumentEntity document;
	protected EntityManager em;
	protected RepeidSession session;

	public DocumentAdapter(RepeidSession session, OrganizationModel organization, EntityManager em, DocumentEntity document) {
		this.em = em;
		this.organization = organization;
		this.document = document;
		this.session = session;
	}

	@Override
	public DocumentEntity getEntity() {
		return document;
	}

	@Override
	public String getId() {
		return document.getId();
	}

	@Override
	public String getAbbreviature() {
		return document.getAbbreviation();
	}

	@Override
	public void setAbbreviature(String abbreviature) {
		document.setAbbreviation(abbreviature);
		em.flush();
	}

	@Override
	public String getName() {
		return document.getName();
	}

	@Override
	public void setName(String name) {
		document.setName(name);
	}

	@Override
	public int getSize() {
		return document.getSize();
	}

	@Override
	public void setSize(int size) {
		document.setSize(size);
	}

	@Override
	public PersonType getPersonType() {
		return PersonType.valueOf(document.getPersonType());
	}

	@Override
	public void setPersonType(PersonType personType) {
		document.setPersonType(personType.toString());
	}

	@Override
	public boolean isEnabled() {
		return document.isEnabled();
	}

}
