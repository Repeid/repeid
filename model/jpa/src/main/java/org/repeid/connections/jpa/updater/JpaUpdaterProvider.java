package org.repeid.connections.jpa.updater;

import org.repeid.provider.Provider;

import java.sql.Connection;

public interface JpaUpdaterProvider extends Provider {

    public String FIRST_VERSION = "1.0.0.Beta1";

    public void update(Connection connection, String defaultSchema);

    public void validate(Connection connection, String defaultSchema);

}
