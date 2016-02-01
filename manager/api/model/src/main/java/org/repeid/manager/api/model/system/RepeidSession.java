package org.repeid.manager.api.model.system;

import java.util.Set;

import org.repeid.manager.api.model.PersonaJuridicaProvider;
import org.repeid.manager.api.model.PersonaNaturalProvider;
import org.repeid.manager.api.model.TipoDocumentoProvider;
import org.repeid.manager.api.model.provider.Provider;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public interface RepeidSession {

	RepeidTransactionManager getTransaction();

	<T extends Provider> T getProvider(Class<T> clazz);

	<T extends Provider> T getProvider(Class<T> clazz, String id);

	<T extends Provider> Set<String> listProviderIds(Class<T> clazz);

	<T extends Provider> Set<T> getAllProviders(Class<T> clazz);

	void enlistForClose(Provider provider);

	KeycloakSessionFactory getKeycloakSessionFactory();

	/**
	 * Returns a managed provider instance. Will start a provider transaction.
	 * This transaction is managed by the KeycloakSession transaction.
	 *
	 * @return
	 * @throws IllegalStateException
	 *             if transaction is not active
	 */
	TipoDocumentoProvider documentos();

	/**
	 * Returns a managed provider instance. Will start a provider transaction.
	 * This transaction is managed by the KeycloakSession transaction.
	 *
	 * @return
	 * @throws IllegalStateException
	 *             if transaction is not active
	 */
	PersonaNaturalProvider personasNaturales();

	/**
	 * Returns a managed provider instance. Will start a provider transaction.
	 * This transaction is managed by the KeycloakSession transaction.
	 *
	 * @return
	 * @throws IllegalStateException
	 *             if transaction is not active
	 */
	PersonaJuridicaProvider personasJuridicas();

	void close();

}
