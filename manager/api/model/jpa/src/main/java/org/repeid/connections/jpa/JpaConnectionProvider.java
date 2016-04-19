package org.repeid.connections.jpa;

import javax.persistence.EntityManager;

import org.repeid.provider.Provider;

import java.sql.Connection;

public interface JpaConnectionProvider extends Provider {

    EntityManager getEntityManager();

}
