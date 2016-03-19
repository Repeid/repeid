package org.repeid.manager.api.rest.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.repeid.common.util.SystemEnvProperties;
import org.repeid.common.util.system.JsonSerialization;
import org.repeid.manager.api.core.config.Config;
import org.repeid.manager.api.core.config.JsonConfigProvider;
import org.repeid.manager.api.model.migration.MigrationModelManager;
import org.repeid.manager.api.model.provider.KeycloakSession;
import org.repeid.manager.api.model.provider.KeycloakSessionFactory;
import org.repeid.manager.api.model.provider.TimerProvider;
import org.repeid.manager.api.model.utils.PostMigrationEvent;
import org.repeid.manager.api.rest.filters.RepeidTransactionCommitter;
import org.repeid.manager.api.rest.impl.admin.MaestroResourceImpl;
import org.repeid.manager.api.rest.impl.admin.TiposDocumentoResourceImpl;
import org.repeid.manager.api.rest.impl.info.ServerVersionResourceImpl;
import org.repeid.manager.api.rest.managers.ApplianceBootstrap;
import org.repeid.manager.api.rest.managers.UsersSyncManager;
import org.repeid.manager.api.rest.services.DefaultKeycloakSessionFactory;
import org.repeid.manager.api.rest.services.ServicesLogger;
import org.repeid.manager.api.rest.services.sheduled.ClearExpiredEvents;
import org.repeid.manager.api.rest.services.sheduled.ClearExpiredUserSessions;
import org.repeid.manager.api.rest.services.sheduled.ScheduledTaskRunner;
import org.repeid.manager.api.rest.util.ObjectMapperResolver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RepeidApplication extends Application {

	private static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

	protected Set<Object> singletons = new HashSet<Object>();
	protected Set<Class<?>> classes = new HashSet<Class<?>>();

	protected KeycloakSessionFactory sessionFactory;
	protected String contextPath;

	public RepeidApplication(@Context ServletContext context, @Context Dispatcher dispatcher) {
		loadConfig();

		this.contextPath = context.getContextPath();
		this.sessionFactory = createSessionFactory();

		dispatcher.getDefaultContextObjects().put(RepeidApplication.class, this);
		ResteasyProviderFactory.pushContext(RepeidApplication.class, this); // for
																			// injection
		context.setAttribute(KeycloakSessionFactory.class.getName(), this.sessionFactory);

		singletons.add(new ServerVersionResourceImpl());
		singletons.add(new MaestroResourceImpl());
		singletons.add(new TiposDocumentoResourceImpl());
		singletons.add(new ModelExceptionMapper());

		classes.add(RepeidTransactionCommitter.class);

		singletons.add(new ObjectMapperResolver(
				Boolean.parseBoolean(System.getProperty("keycloak.jsonPrettyPrint", "false"))));

		migrateModel();

		@SuppressWarnings("unused")
		boolean bootstrapAdminUser = false;

		KeycloakSession session = sessionFactory.create();
		// ExportImportManager exportImportManager;
		try {
			session.getTransaction().begin();

			ApplianceBootstrap applianceBootstrap = new ApplianceBootstrap(session);
			// exportImportManager = new ExportImportManager(session);

			boolean createMasterRealm = applianceBootstrap.isNewInstall();
			/*
			 * if (exportImportManager.isRunImport() &&
			 * exportImportManager.isImportMasterIncluded()) { createMasterRealm
			 * = false; }
			 */

			if (createMasterRealm) {
				applianceBootstrap.createMasterRealm(contextPath);
			}
			session.getTransaction().commit();
		} finally {
			session.close();
		}

		/*
		 * if (exportImportManager.isRunImport()) {
		 * exportImportManager.runImport(); } else { importRealms(); }
		 */

		// importAddUser();

		/*
		 * if (exportImportManager.isRunExport()) {
		 * exportImportManager.runExport(); }
		 */

		session = sessionFactory.create();
		try {
			session.getTransaction().begin();
			bootstrapAdminUser = new ApplianceBootstrap(session).isNoMasterUser();

			session.getTransaction().commit();
		} finally {
			session.close();
		}

		sessionFactory.publish(new PostMigrationEvent());

		// singletons.add(new WelcomeResource(bootstrapAdminUser));

		setupScheduledTasks(sessionFactory);
	}

	protected void migrateModel() {
		KeycloakSession session = sessionFactory.create();
		try {
			session.getTransaction().begin();
			MigrationModelManager.migrate(session);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			logger.migrationFailure(e);
		} finally {
			session.close();
		}
	}

	public String getContextPath() {
		return contextPath;
	}

	/**
	 * Get base URI of WAR distribution, not JAX-RS
	 *
	 * @param uriInfo
	 * @return
	 */
	public URI getBaseUri(UriInfo uriInfo) {
		return uriInfo.getBaseUriBuilder().replacePath(getContextPath()).build();
	}

	public static void loadConfig() {
		try {
			JsonNode node = null;

			String configDir = System.getProperty("jboss.server.config.dir");
			if (configDir != null) {
				File f = new File(configDir + File.separator + "keycloak-server.json");
				if (f.isFile()) {
					logger.loadingFrom(f.getAbsolutePath());
					node = new ObjectMapper().readTree(f);
				}
			}

			if (node == null) {
				URL resource = Thread.currentThread().getContextClassLoader()
						.getResource("META-INF/keycloak-server.json");
				if (resource != null) {
					logger.loadingFrom(resource);
					node = new ObjectMapper().readTree(resource);
				}
			}

			if (node != null) {
				Properties properties = new SystemEnvProperties();
				Config.init(new JsonConfigProvider(node, properties));
				return;
			} else {
				throw new RuntimeException("Config 'keycloak-server.json' not found");
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to load config", e);
		}
	}

	public static KeycloakSessionFactory createSessionFactory() {
		DefaultKeycloakSessionFactory factory = new DefaultKeycloakSessionFactory();
		factory.init();
		return factory;
	}

	public static void setupScheduledTasks(final KeycloakSessionFactory sessionFactory) {
		long interval = Config.scope("scheduled").getLong("interval", 60L) * 1000;

		KeycloakSession session = sessionFactory.create();
		try {
			TimerProvider timer = session.getProvider(TimerProvider.class);
			timer.schedule(new ScheduledTaskRunner(sessionFactory, new ClearExpiredEvents()), interval,
					"ClearExpiredEvents");
			timer.schedule(new ScheduledTaskRunner(sessionFactory, new ClearExpiredUserSessions()), interval,
					"ClearExpiredUserSessions");
			new UsersSyncManager().bootstrapPeriodic(sessionFactory, timer);
		} finally {
			session.close();
		}
	}

	public KeycloakSessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

	@SuppressWarnings("unused")
	private static <T> T loadJson(InputStream is, Class<T> type) {
		try {
			return JsonSerialization.readValue(is, type);
		} catch (IOException e) {
			throw new RuntimeException("Failed to parse json", e);
		}
	}

}
