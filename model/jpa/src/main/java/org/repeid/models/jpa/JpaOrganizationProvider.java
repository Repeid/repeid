package org.repeid.models.jpa;

import org.jboss.logging.Logger;
import org.repeid.migration.MigrationModel;
import org.repeid.models.OrganizationModel;
import org.repeid.models.OrganizationProvider;
import org.repeid.models.RepeidSession;

import java.util.List;

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
    public void close() {
        // TODO Auto-generated method stub

    }

    @Override
    public MigrationModel getMigrationModel() {
        return new MigrationModelAdapter(em);
    }

    @Override
    public OrganizationModel createOrganization(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OrganizationModel createOrganization(String id, String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OrganizationModel getOrganization(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public OrganizationModel getOrganizationByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removeOrganization(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<OrganizationModel> getOrganizations() {
        // TODO Auto-generated method stub
        return null;
    }

}
