package org.repeid.connections.jpa;

import javax.persistence.EntityManager;

import org.repeid.provider.Provider;

public interface JpaConnectionProvider extends Provider {

    EntityManager getEntityManager();

}
