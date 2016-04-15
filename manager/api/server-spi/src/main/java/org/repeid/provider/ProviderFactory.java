package org.repeid.provider;

import org.keycloak.Config;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public interface ProviderFactory<T extends Provider> {

    public T create(RepeidSession session);

    /**
     * Only called once when the factory is first created. This config is pulled
     * from keycloak_server.json
     *
     * @param config
     */
    public void init(Config.Scope config);

    /**
     * Called after all provider factories have been initialized
     */
    public void postInit(RepeidSessionFactory factory);

    /**
     * This is called when the server shuts down.
     *
     */
    public void close();

    public String getId();

}
