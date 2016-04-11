package org.repeid.services;

import java.util.Set;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.KeycloakTransactionManager;
import org.keycloak.provider.Provider;

public class DefaultRepeidSession implements KeycloakSession {

	public DefaultRepeidSession(DefaultRepeidSessionFactory factory) {
		// TODO Auto-generated method stub
	}

	@Override
	public KeycloakTransactionManager getTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Provider> T getProvider(Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Provider> T getProvider(Class<T> clazz, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Provider> Set<String> listProviderIds(Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Provider> Set<T> getAllProviders(Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enlistForClose(Provider provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public KeycloakSessionFactory getKeycloakSessionFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}