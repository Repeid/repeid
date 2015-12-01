package org.repeid.provider;

/**
 * At boot time, keycloak discovers all factories. For each discovered factory,
 * the init() method is called. After all factories have been initialized, the
 * postInit() method is called. close() is called when the server shuts down.
 *
 * Only one instance of a factory exists per server.
 *
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public interface ProviderFactory<T extends Provider> {

}