package org.repeid.provider;

import org.keycloak.Config;
import org.repeid.models.RepeidSession;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public interface ProviderFactory<T extends Provider> {

    public T create(RepeidSession session);

    public void init(Config.Scope config);

    public void close();

    public String getId();

}
