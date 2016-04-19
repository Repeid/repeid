package org.repeid.models.jpa;

import org.jboss.logging.Logger;
import org.keycloak.models.jpa.JpaRealmProvider;
import org.repeid.models.OrganizationModel;
import org.repeid.models.OrganizationProvider;
import org.repeid.models.RepeidSession;

import javax.persistence.EntityManager;

public class JpaOrganizationProvider implements OrganizationProvider {

    protected static final Logger logger = Logger.getLogger(JpaOrganizationProvider.class);
    private final RepeidSession session;
    protected EntityManager em;

    public JpaOrganizationProvider(RepeidSession session, EntityManager em) {
        this.session = session;
        this.em = em;
    }

    @Override
    public OrganizationModel createRealm(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OrganizationModel createRealm(String id, String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OrganizationModel getRealm(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OrganizationModel getRealmByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removeRealm(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        
    }
   
}
