package org.repeid.models.jpa;

import org.jboss.logging.Logger;
import org.repeid.models.NaturalPersonModel;
import org.repeid.models.OrganizationModel;
import org.repeid.models.RepeidSession;
import org.repeid.models.jpa.entities.LegalPersonEntity;

import javax.persistence.EntityManager;

public class LegalPersonAdapter implements NaturalPersonModel, JpaModel<LegalPersonEntity> {

    protected static final Logger logger = Logger.getLogger(LegalPersonAdapter.class);

    protected LegalPersonEntity legalPerson;
    protected EntityManager em;
    protected OrganizationModel organization;
    protected RepeidSession session;

    public LegalPersonAdapter(RepeidSession session, OrganizationModel organization, EntityManager em, LegalPersonEntity legalPerson) {
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

}
