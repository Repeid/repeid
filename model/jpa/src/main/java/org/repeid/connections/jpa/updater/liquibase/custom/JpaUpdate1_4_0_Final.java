package org.repeid.connections.jpa.updater.liquibase.custom;

import liquibase.exception.CustomChangeException;

public class JpaUpdate1_4_0_Final extends CustomRepeidTask {

    @Override
    protected void generateStatementsImpl() throws CustomChangeException {

    }

    @Override
    protected String getTaskId() {
        return "Update 1.4.0.Final";
    }
}
