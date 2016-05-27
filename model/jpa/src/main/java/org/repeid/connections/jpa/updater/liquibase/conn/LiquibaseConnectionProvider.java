package org.repeid.connections.jpa.updater.liquibase.conn;

import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import org.repeid.provider.Provider;

import java.sql.Connection;

public interface LiquibaseConnectionProvider extends Provider {

    Liquibase getLiquibase(Connection connection, String defaultSchema) throws LiquibaseException;

}
