package org.repeid.manager.api.mongo;

import javax.persistence.EntityManager;

import org.repeid.manager.api.model.provider.Provider;

/**
 * @author <a href="mailto:carlosthe19916@carlosthe19916">Carlos Feria</a>
 */
public interface MongoConnectionProvider extends Provider {

	EntityManager getEntityManager();

}
