package org.repeid.models.jpa;

import org.jboss.logging.Logger;
import org.repeid.models.DocumentModel;
import org.repeid.models.LegalPersonModel;
import org.repeid.models.OrganizationModel;
import org.repeid.models.RepeidSession;
import org.repeid.models.jpa.entities.LegalPersonEntity;

import javax.persistence.EntityManager;

public class LegalPersonAdapter implements LegalPersonModel, JpaModel<LegalPersonEntity> {

	protected static final Logger logger = Logger.getLogger(LegalPersonAdapter.class);

	protected LegalPersonEntity legalPerson;
	protected EntityManager em;
	protected OrganizationModel organization;
	protected RepeidSession session;

	public LegalPersonAdapter(RepeidSession session, OrganizationModel organization, EntityManager em,
			LegalPersonEntity legalPerson) {
		this.em = em;
		this.organization = organization;
		this.legalPerson = legalPerson;
		this.session = session;
	}

	@Override
	public LegalPersonEntity getEntity() {
		return legalPerson;
	}

	@Override
	public String getId() {
		return legalPerson.getId();
	}

	@Override
	public OrganizationModel getOrganization() {
		return new OrganizationAdapter(session, em, legalPerson.getOrganization());
	}

	@Override
	public DocumentModel getDocument() {
		return new DocumentAdapter(session, organization, em, legalPerson.getDocument());
	}

	@Override
	public String getDocumentNumber() {
		return legalPerson.getDocumentNumber();
	}

	@Override
	public String getCountry() {
		return legalPerson.getCountry();
	}

	@Override
	public void setCountry(String country) {
		legalPerson.setCountry(country);
	}

	@Override
	public String getAddress() {
		return legalPerson.getAddress();
	}

	@Override
	public void setAddress(String address) {
		legalPerson.setAddress(address);
	}

	@Override
	public String getReference() {
		return legalPerson.getReference();
	}

	@Override
	public void setReference(String reference) {
		legalPerson.setReference(reference);
	}

	@Override
	public String getPhoneNumber() {
		return legalPerson.getPhoneNumber();
	}

	@Override
	public void setPhoneNumber(String phoneNumber) {
		legalPerson.setPhoneNumber(phoneNumber);
	}

	@Override
	public String getEmail() {
		return legalPerson.getEmail();
	}

	@Override
	public void setEmail(String email) {
		legalPerson.setEmail(email);
	}

	@Override
	public String getName() {
		return legalPerson.getName();
	}

	@Override
	public void setName(String name) {
		legalPerson.setName(name);
	}

}
