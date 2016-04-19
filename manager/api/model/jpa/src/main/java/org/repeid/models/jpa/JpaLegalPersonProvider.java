package org.repeid.models.jpa;

import org.jboss.logging.Logger;
import org.repeid.models.LegalPersonProvider;
import org.repeid.models.NaturalPersonProvider;
import org.repeid.models.RepeidSession;

import javax.persistence.EntityManager;

public class JpaLegalPersonProvider implements LegalPersonProvider {

    protected static final Logger logger = Logger.getLogger(JpaLegalPersonProvider.class);
    private final RepeidSession session;
    protected EntityManager em;

    public JpaLegalPersonProvider(RepeidSession session, EntityManager em) {
        this.session = session;
        this.em = em;
    }

    @Override
    public void close() {

    }
    
}
