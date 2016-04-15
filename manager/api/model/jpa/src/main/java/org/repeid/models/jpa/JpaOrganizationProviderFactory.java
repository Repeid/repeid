package org.repeid.models.jpa;

import org.keycloak.Config;
import org.repeid.models.OrganizationProvider;
import org.repeid.models.RealmProviderFactory;
import org.repeid.models.RepeidSession;

import javax.persistence.EntityManager;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class JpaOrganizationProviderFactory implements RealmProviderFactory {

    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public String getId() {
        return "jpa";
    }

    @Override
    public OrganizationProvider create(RepeidSession session) {
        /*
         * EntityManager em =
         * session.getProvider(JpaConnectionProvider.class).getEntityManager();
         * return new JpaRealmProvider(session, em);
         */
        return null;
    }

    @Override
    public void close() {
    }

}
