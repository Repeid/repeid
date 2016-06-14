package org.repeid.models.jpa;

import org.repeid.Config;
import org.repeid.connections.jpa.JpaConnectionProvider;
import org.repeid.models.*;

import javax.persistence.EntityManager;

public class JpaNaturalPersonProviderFactory implements NaturalPersonProviderFactory {

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
	public NaturalPersonProvider create(RepeidSession session) {
		EntityManager em = session.getProvider(JpaConnectionProvider.class).getEntityManager();
		return new JpaNaturalPersonProvider(session, em);
	}

	@Override
	public void close() {
	}

}
