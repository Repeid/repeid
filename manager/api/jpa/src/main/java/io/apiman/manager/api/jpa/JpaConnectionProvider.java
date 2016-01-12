package io.apiman.manager.api.jpa;

import javax.persistence.EntityManager;

import org.repeid.manager.api.model.provider.Provider;

/**
 * @author <a href="mailto:carlosthe19916@carlosthe19916">Carlos Feria</a>
 */
public interface JpaConnectionProvider extends Provider {

	EntityManager getEntityManager();

}
