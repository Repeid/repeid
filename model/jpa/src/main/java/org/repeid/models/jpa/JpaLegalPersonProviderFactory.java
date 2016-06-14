package org.repeid.models.jpa;

import org.repeid.Config;
import org.repeid.connections.jpa.JpaConnectionProvider;
import org.repeid.models.LegalPersonProvider;
import org.repeid.models.LegalPersonProviderFactory;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;

import javax.persistence.EntityManager;

public class JpaLegalPersonProviderFactory implements LegalPersonProviderFactory {

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
	public LegalPersonProvider create(RepeidSession session) {
		EntityManager em = session.getProvider(JpaConnectionProvider.class).getEntityManager();
		return new JpaLegalPersonProvider(session, em);
	}

	@Override
	public void close() {
	}

}
