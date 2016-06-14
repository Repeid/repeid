package org.repeid.models.jpa;

import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.jboss.logging.Logger;
import org.repeid.models.DocumentModel;
import org.repeid.models.NaturalPersonModel;
import org.repeid.models.OrganizationModel;
import org.repeid.models.RepeidSession;
import org.repeid.models.enums.Gender;
import org.repeid.models.enums.MaritalStatus;
import org.repeid.models.jpa.entities.NaturalPersonEntity;

public class NaturalPersonAdapter implements NaturalPersonModel, JpaModel<NaturalPersonEntity> {

	protected static final Logger logger = Logger.getLogger(NaturalPersonAdapter.class);

	protected NaturalPersonEntity naturalPerson;
	protected EntityManager em;
	protected OrganizationModel organization;
	protected RepeidSession session;

	public NaturalPersonAdapter(RepeidSession session, OrganizationModel organization, EntityManager em,
			NaturalPersonEntity naturalPerson) {
		this.em = em;
		this.organization = organization;
		this.naturalPerson = naturalPerson;
		this.session = session;
	}

	@Override
	public NaturalPersonEntity getEntity() {
		return naturalPerson;
	}

	public static NaturalPersonEntity toEntity(NaturalPersonModel model, EntityManager em) {
		if (model instanceof NaturalPersonAdapter) {
			return ((NaturalPersonAdapter)model).getEntity();
		}
		return em.getReference(NaturalPersonEntity.class, model.getId());
	}

	@Override
	public String getId() {
		return naturalPerson.getId();
	}

	@Override
	public OrganizationModel getOrganization() {
		return new OrganizationAdapter(session, em, naturalPerson.getOrganization());
	}

	@Override
	public DocumentModel getDocument() {
		return new DocumentAdapter(session, organization, em, naturalPerson.getDocument());
	}

	@Override
	public String getDocumentNumber() {
		return naturalPerson.getDocumentNumber();
	}

	@Override
	public String getCountry() {
		return naturalPerson.getCountry();
	}

	@Override
	public void setCountry(String country) {
		naturalPerson.setCountry(country);
	}

	@Override
	public String getAddress() {
		return naturalPerson.getAddress();
	}

	@Override
	public void setAddress(String address) {
		naturalPerson.setAddress(address);
	}

	@Override
	public String getReference() {
		return naturalPerson.getReference();
	}

	@Override
	public void setReference(String reference) {
		naturalPerson.setReference(reference);
	}

	@Override
	public String getPhoneNumber() {
		return naturalPerson.getPhoneNumber();
	}

	@Override
	public void setPhoneNumber(String phoneNumber) {
		naturalPerson.setPhoneNumber(phoneNumber);
	}

	@Override
	public String getEmail() {
		return naturalPerson.getEmail();
	}

	@Override
	public void setEmail(String email) {
		naturalPerson.setEmail(email);
	}

	@Override
	public String getFirstName() {
		return naturalPerson.getFirstName();
	}

	@Override
	public void setFirstName(String firstName) {
		naturalPerson.setFirstName(firstName);
	}

	@Override
	public String getMiddleName() {
		return naturalPerson.getMiddleName();
	}

	@Override
	public void setMiddleName(String middleName) {
		naturalPerson.setMiddleName(middleName);
	}

	@Override
	public String getLastName() {
		return naturalPerson.getLastName();
	}

	@Override
	public void setLastName(String lastName) {
		naturalPerson.setLastName(lastName);
	}

	@Override
	public LocalDate getDateBirth() {
		return naturalPerson.getDateBirth();
	}

	@Override
	public void setDateBirth(LocalDate date) {
		naturalPerson.setDatebirth(date);
	}

	@Override
	public Gender getGender() {
		return Gender.valueOf(naturalPerson.getGender());
	}

	@Override
	public void setGender(Gender gender) {
		naturalPerson.setGender(gender.toString());
	}

	@Override
	public MaritalStatus getMarriageStatus() {
		return MaritalStatus.valueOf(naturalPerson.getMarriageStatus());
	}

	@Override
	public void setMarriageStatus(MaritalStatus marriageStatus) {
		naturalPerson.setMarriageStatus(marriageStatus.toString());
	}

	@Override
	public String getJob() {
		return naturalPerson.getJob();
	}

	@Override
	public void setJob(String job) {
		naturalPerson.setJob(job);
	}

}
