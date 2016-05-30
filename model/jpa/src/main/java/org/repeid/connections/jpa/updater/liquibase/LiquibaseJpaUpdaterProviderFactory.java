package org.repeid.connections.jpa.updater.liquibase;

import org.repeid.Config;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;
import org.repeid.connections.jpa.updater.JpaUpdaterProvider;
import org.repeid.connections.jpa.updater.JpaUpdaterProviderFactory;

public class LiquibaseJpaUpdaterProviderFactory implements JpaUpdaterProviderFactory {

    @Override
    public JpaUpdaterProvider create(RepeidSession session) {
        return new LiquibaseJpaUpdaterProvider(session);
    }

    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public void postInit(RepeidSessionFactory factory) {

    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return "liquibase";
    }

}
