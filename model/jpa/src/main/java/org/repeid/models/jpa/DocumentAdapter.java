package org.repeid.models.jpa;

import org.jboss.logging.Logger;
import org.repeid.models.DocumentModel;
import org.repeid.models.OrganizationModel;
import org.repeid.models.RepeidSession;
import org.repeid.models.jpa.entities.DocumentEntity;

import javax.persistence.EntityManager;

public class DocumentAdapter implements DocumentModel, JpaModel<DocumentEntity> {

	protected static final Logger logger = Logger.getLogger(DocumentAdapter.class);

	protected DocumentEntity document;
	protected EntityManager em;

	public DocumentAdapter(RepeidSession session, OrganizationModel organization, EntityManager em,
			DocumentEntity document) {

	}

	@Override
	public DocumentEntity getEntity() {
		return document;
	}

	@Override
	public String getId() {
		return document.getId();
	}

}
