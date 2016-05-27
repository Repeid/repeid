package org.repeid.models.jpa;

import org.jboss.logging.Logger;
import org.repeid.models.DocumentModel;
import org.repeid.models.NaturalPersonModel;
import org.repeid.models.OrganizationModel;
import org.repeid.models.RepeidSession;
import org.repeid.models.jpa.entities.DocumentEntity;
import org.repeid.models.jpa.entities.NaturalPersonEntity;

import javax.persistence.EntityManager;

public class NaturalPersonAdapter implements NaturalPersonModel, JpaModel<NaturalPersonEntity> {

    protected static final Logger logger = Logger.getLogger(NaturalPersonAdapter.class);

    protected NaturalPersonEntity naturalPerson;
    protected EntityManager em;
    protected OrganizationModel organization;
    protected RepeidSession session;

    public NaturalPersonAdapter(RepeidSession session, OrganizationModel organization, EntityManager em, NaturalPersonEntity naturalPerson) {
        this.em = em;
        this.organization = organization;
        this.naturalPerson = naturalPerson;
        this.session = session;
    }

    @Override
    public NaturalPersonEntity getEntity() {
        return naturalPerson;
    }

    @Override
    public String getId() {
        return naturalPerson.getId();
    }

}
