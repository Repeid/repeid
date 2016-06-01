package org.repeid.models.jpa;

import org.repeid.Config;
import org.repeid.connections.jpa.JpaConnectionProvider;
import org.repeid.models.OrganizationProvider;
import org.repeid.models.OrganizationProviderFactory;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;

import javax.persistence.EntityManager;

public class JpaOrganizationProviderFactory implements OrganizationProviderFactory {

    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public void postInit(RepeidSessionFactory factory) {

    }

    @Override
    public String getId() {
        return "jpa";
    }

    @Override
    public OrganizationProvider create(RepeidSession session) {
        EntityManager em = session.getProvider(JpaConnectionProvider.class).getEntityManager();
        return new JpaOrganizationProvider(session, em);
    }

    @Override
    public void close() {
    }

}
