package org.repeid.models.jpa;

import org.jboss.logging.Logger;
import org.repeid.models.NaturalPersonProvider;
import org.repeid.models.OrganizationModel;
import org.repeid.models.OrganizationProvider;
import org.repeid.models.RepeidSession;

import javax.persistence.EntityManager;

public class JpaNaturalPersonProvider implements NaturalPersonProvider {

    protected static final Logger logger = Logger.getLogger(JpaNaturalPersonProvider.class);
    private final RepeidSession session;
    protected EntityManager em;

    public JpaNaturalPersonProvider(RepeidSession session, EntityManager em) {
        this.session = session;
        this.em = em;
    }

    @Override
    public void close() {

    }

}
