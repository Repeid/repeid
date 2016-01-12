package io.apiman.manager.api.jpa;

import javax.persistence.EntityManager;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */
public class DefaultJpaConnectionProvider implements JpaConnectionProvider {

	private final EntityManager em;

	public DefaultJpaConnectionProvider(EntityManager em) {
		this.em = em;
	}

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public void close() {
		em.close();
	}

}
