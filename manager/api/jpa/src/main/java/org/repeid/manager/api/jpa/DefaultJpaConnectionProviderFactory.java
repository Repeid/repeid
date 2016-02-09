/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
package org.repeid.manager.api.jpa;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.InitialContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.hibernate.jpa.AvailableSettings;
import org.jboss.logging.Logger;
import org.repeid.manager.api.core.config.Config;
import org.repeid.manager.api.jpa.utils.JpaUtils;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

@Startup
@Singleton
@DependsOn("RepeidApplication")
@TransactionManagement(TransactionManagementType.BEAN)
public class DefaultJpaConnectionProviderFactory implements JpaConnectionProviderFactory {

	private static final Logger logger = Logger.getLogger(DefaultJpaConnectionProviderFactory.class);

	private volatile EntityManagerFactory emf;

	private Config.Scope config;

	private Map<String, String> operationalInfo;

	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}

	@PostConstruct
	public void init() {
		String realmProvider = Config.getProvider("realm");
		if (realmProvider.equalsIgnoreCase("jpa")) {
			config = Config.scope("connectionsJpa", "default");
			lazyInit();
		}
	}

	private void lazyInit() {
		if (emf == null) {
			logger.debug("Initializing JPA connections");

			Connection connection = null;

			String databaseSchema = config.get("databaseSchema");

			Map<String, Object> properties = new HashMap<String, Object>();

			String unitName = "repeid-defaultJpa";

			String dataSource = config.get("dataSource");
			if (dataSource != null) {
				if (config.getBoolean("jta", false)) {
					properties.put(AvailableSettings.JTA_DATASOURCE, dataSource);
				} else {
					properties.put(AvailableSettings.NON_JTA_DATASOURCE, dataSource);
				}
			} else {
				properties.put(AvailableSettings.JDBC_URL, config.get("url"));
				properties.put(AvailableSettings.JDBC_DRIVER, config.get("driver"));

				String user = config.get("user");
				if (user != null) {
					properties.put(AvailableSettings.JDBC_USER, user);
				}
				String password = config.get("password");
				if (password != null) {
					properties.put(AvailableSettings.JDBC_PASSWORD, password);
				}
			}

			String driverDialect = config.get("driverDialect");
			if (driverDialect != null && driverDialect.length() > 0) {
				properties.put("hibernate.dialect", driverDialect);
			}

			String schema = config.get("schema");
			if (schema != null) {
				properties.put(JpaUtils.HIBERNATE_DEFAULT_SCHEMA, schema);
			}

			if (databaseSchema != null) {
				if (databaseSchema.equals("development-update")) {
					properties.put("hibernate.hbm2ddl.auto", "update");
					databaseSchema = null;
				} else if (databaseSchema.equals("development-validate")) {
					properties.put("hibernate.hbm2ddl.auto", "validate");
					databaseSchema = null;
				}
			}

			properties.put("hibernate.show_sql", config.getBoolean("showSql", false));
			properties.put("hibernate.format_sql", config.getBoolean("formatSql", true));

			connection = getConnection();

			try {
				prepareOperationalInfo(connection);

				if (databaseSchema != null) {
					logger.trace("Updating database");

					if (databaseSchema.equals("update")) {
						properties.put("hibernate.hbm2ddl.auto", "update");
					} else if (databaseSchema.equals("validate")) {
						properties.put("hibernate.hbm2ddl.auto", "validate");
					} else {
						throw new RuntimeException("Invalid value for databaseSchema: " + databaseSchema);
					}

					logger.trace("Database update completed");
				}

				logger.trace("Creating EntityManagerFactory");
				emf = Persistence.createEntityManagerFactory(unitName, properties);
				logger.trace("EntityManagerFactory created");

			} finally {
				// Close after creating EntityManagerFactory to prevent
				// in-mem databases from closing
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						logger.warn(e);
					}
				}
			}
		}
	}

	protected void prepareOperationalInfo(Connection connection) {
		try {
			operationalInfo = new LinkedHashMap<>();
			DatabaseMetaData md = connection.getMetaData();
			operationalInfo.put("databaseUrl", md.getURL());
			operationalInfo.put("databaseUser", md.getUserName());
			operationalInfo.put("databaseProduct", md.getDatabaseProductName() + " " + md.getDatabaseProductVersion());
			operationalInfo.put("databaseDriver", md.getDriverName() + " " + md.getDriverVersion());
		} catch (SQLException e) {
			logger.warn("Unable to prepare operational info due database exception: " + e.getMessage());
		}
	}

	private Connection getConnection() {
		try {
			String dataSourceLookup = config.get("dataSource");
			if (dataSourceLookup != null) {
				System.out.println("carlos datasource:" + dataSourceLookup);
				DataSource dataSource = (DataSource) new InitialContext().lookup(dataSourceLookup);
				System.out.println("carlos datasource:" + dataSourceLookup);
				return dataSource.getConnection();
			} else {
				Class.forName(config.get("driver"));
				return DriverManager.getConnection(config.get("url"), config.get("user"), config.get("password"));
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to connect to database", e);
		}
	}

	// @Override
	public Map<String, String> getOperationalInfo() {
		return operationalInfo;
	}

}
