package org.repeid.connections.jpa.updater.liquibase.custom;

import liquibase.change.custom.CustomSqlChange;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.UpdateStatement;
import liquibase.structure.core.Table;
import org.repeid.connections.jpa.updater.liquibase.LiquibaseJpaUpdaterProvider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AddRealmCodeSecret implements CustomSqlChange {

    private String confirmationMessage;

    @Override
    public SqlStatement[] generateStatements(Database database) throws CustomChangeException {
        return null;
    }

    @Override
    public String getConfirmationMessage() {
        return confirmationMessage;
    }

    @Override
    public void setUp() throws SetupException {

    }

    @Override
    public void setFileOpener(ResourceAccessor resourceAccessor) {

    }

    @Override
    public ValidationErrors validate(Database database) {
        return null;
    }

}
