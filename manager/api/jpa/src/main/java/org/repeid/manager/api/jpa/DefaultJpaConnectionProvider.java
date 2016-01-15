package org.repeid.manager.api.jpa;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */
@Stateless
public class DefaultJpaConnectionProvider implements JpaConnectionProvider {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void close() {
		em.close();
	}

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

}
