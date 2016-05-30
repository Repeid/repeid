package org.repeid.connections.jpa;

import java.sql.Connection;

import org.repeid.provider.ProviderFactory;

public interface JpaConnectionProviderFactory extends ProviderFactory<JpaConnectionProvider> {

    // Caller is responsible for closing connection
    Connection getConnection();

    String getSchema();

}
