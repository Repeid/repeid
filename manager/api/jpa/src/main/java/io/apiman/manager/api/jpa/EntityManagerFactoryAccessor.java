/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.apiman.manager.api.jpa;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Produces an instance of {@link EntityManagerFactory}.
 *
 * @author eric.wittmann@redhat.com
 */
public class EntityManagerFactoryAccessor implements IEntityManagerFactoryAccessor {

    @Inject
    private IJpaProperties jpaProperties;

    private final static Logger logger = LoggerFactory.getLogger(EntityManagerFactoryAccessor.class);
    // private volatile EntityManagerFactory emf;
    private EntityManagerFactory emf;
    private Map<String, String> operationalInfo;

    /**
     * Constructor.
     */
    public EntityManagerFactoryAccessor() {
    }

    @PostConstruct
    public void postConstruct() {
        Map<String, String> properties = new HashMap<>();

        // Get properties from apiman.properties
        Map<String, String> cp = jpaProperties.getAllHibernateProperties();
        if (cp != null) {
            properties.putAll(cp);
        }

        // Get two specific properties from the System (for backward
        // compatibility only)
        String s = properties.get("hibernate.hbm2ddl.auto"); //$NON-NLS-1$
        if (s == null) {
            s = "validate"; //$NON-NLS-1$
        }
        String autoValue = System.getProperty("apiman.hibernate.hbm2ddl.auto", s); //$NON-NLS-1$
        s = properties.get("hibernate.dialect"); //$NON-NLS-1$
        if (s == null) {
            s = "org.hibernate.dialect.H2Dialect"; //$NON-NLS-1$
        }
        String dialect = System.getProperty("apiman.hibernate.dialect", s); //$NON-NLS-1$
        properties.put("hibernate.hbm2ddl.auto", autoValue); //$NON-NLS-1$
        properties.put("hibernate.dialect", dialect); //$NON-NLS-1$

        // First try using standard JPA to load the persistence unit. If that
        // fails, then
        // try using hibernate directly in a couple ways (depends on hibernate
        // version and
        // platform we're running on).
        try {
            emf = Persistence.createEntityManagerFactory("apiman-manager-api-jpa", properties); //$NON-NLS-1$
        } catch (Throwable t1) {
            try {
                emf = new HibernatePersistenceProvider().createEntityManagerFactory("apiman-manager-api-jpa", //$NON-NLS-1$
                        properties);
            } catch (Throwable t2) {
                throw t2;
            }
        }
    }

    /**
     * @see io.apiman.manager.api.jpa.IEntityManagerFactoryAccessor#getEntityManagerFactory()
     */
    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    protected void prepareOperationalInfo(Connection connection) {
        try {
            operationalInfo = new LinkedHashMap<>();
            DatabaseMetaData md = connection.getMetaData();
            operationalInfo.put("databaseUrl", md.getURL());
            operationalInfo.put("databaseUser", md.getUserName());
            operationalInfo.put("databaseProduct",
                    md.getDatabaseProductName() + " " + md.getDatabaseProductVersion());
            operationalInfo.put("databaseDriver", md.getDriverName() + " " + md.getDriverVersion());
        } catch (SQLException e) {
            logger.warn("Unable to prepare operational info due database exception: " + e.getMessage());
        }
    }

    private Connection getConnection() {
        Map<String, String> cp = jpaProperties.getAllHibernateProperties();
        try {
            String dataSourceLookup = cp.get("dataSource");
            if (dataSourceLookup != null) {
                DataSource dataSource = (DataSource) new InitialContext().lookup(dataSourceLookup);
                return dataSource.getConnection();
            } else {
                Class.forName(cp.get("driver"));
                return DriverManager.getConnection(cp.get("url"), cp.get("user"), cp.get("password"));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    @Override
    public Map<String, String> getOperationalInfo() {
        return operationalInfo;
    }

}
