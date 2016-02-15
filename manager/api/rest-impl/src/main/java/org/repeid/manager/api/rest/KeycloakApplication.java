package org.repeid.manager.api.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.logging.Logger;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.keycloak.services.filters.KeycloakTransactionCommitter;
import org.repeid.common.util.SystemEnvProperties;
import org.repeid.common.util.system.JsonSerialization;
import org.repeid.manager.api.core.config.Config;
import org.repeid.manager.api.core.config.JsonConfigProvider;
import org.repeid.manager.api.model.migration.MigrationModelManager;
import org.repeid.manager.api.model.provider.KeycloakSession;
import org.repeid.manager.api.model.provider.KeycloakSessionFactory;
import org.repeid.manager.api.model.utils.PostMigrationEvent;
import org.repeid.manager.api.rest.exportimport.ExportImportManager;
import org.repeid.manager.api.rest.impl.util.ObjectMapperResolver;
import org.repeid.manager.api.rest.managers.ApplianceBootstrap;
import org.repeid.manager.api.rest.services.ServicesLogger;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

public class KeycloakApplication extends Application {

	private static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

	protected Set<Object> singletons = new HashSet<Object>();
	protected Set<Class<?>> classes = new HashSet<Class<?>>();

	protected KeycloakSessionFactory sessionFactory;
	protected String contextPath;

	public KeycloakApplication(@Context ServletContext context, @Context Dispatcher dispatcher) {
		loadConfig();

		this.contextPath = context.getContextPath();
		this.sessionFactory = createSessionFactory();

		dispatcher.getDefaultContextObjects().put(KeycloakApplication.class, this);
		ResteasyProviderFactory.pushContext(KeycloakApplication.class, this); // for
																				// injection
		context.setAttribute(KeycloakSessionFactory.class.getName(), this.sessionFactory);

		singletons.add(new ServerVersionResource());
		singletons.add(new RealmsResource());
		singletons.add(new AdminRoot());
		singletons.add(new ModelExceptionMapper());
		classes.add(QRCodeResource.class);
		classes.add(ThemeResource.class);
		classes.add(JsResource.class);

		classes.add(KeycloakTransactionCommitter.class);

		singletons.add(new ObjectMapperResolver(
				Boolean.parseBoolean(System.getProperty("keycloak.jsonPrettyPrint", "false"))));

		migrateModel();

		boolean bootstrapAdminUser = false;

		KeycloakSession session = sessionFactory.create();
		ExportImportManager exportImportManager;
		try {
			session.getTransaction().begin();

			ApplianceBootstrap applianceBootstrap = new ApplianceBootstrap(session);
			exportImportManager = new ExportImportManager(session);

			boolean createMasterRealm = applianceBootstrap.isNewInstall();
			if (exportImportManager.isRunImport() && exportImportManager.isImportMasterIncluded()) {
				createMasterRealm = false;
			}

			if (createMasterRealm) {
				applianceBootstrap.createMasterRealm(contextPath);
			}
			session.getTransaction().commit();
		} finally {
			session.close();
		}

		if (exportImportManager.isRunImport()) {
			exportImportManager.runImport();
		} else {
			importRealms();
		}

		importAddUser();

		if (exportImportManager.isRunExport()) {
			exportImportManager.runExport();
		}

		session = sessionFactory.create();
		try {
			session.getTransaction().begin();
			bootstrapAdminUser = new ApplianceBootstrap(session).isNoMasterUser();

			session.getTransaction().commit();
		} finally {
			session.close();
		}

		sessionFactory.publish(new PostMigrationEvent());

		singletons.add(new WelcomeResource(bootstrapAdminUser));

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

	public void importRealms() {
		String files = System.getProperty("keycloak.import");
		if (files != null) {
			StringTokenizer tokenizer = new StringTokenizer(files, ",");
			while (tokenizer.hasMoreTokens()) {
				String file = tokenizer.nextToken().trim();
				RealmRepresentation rep;
				try {
					rep = loadJson(new FileInputStream(file), RealmRepresentation.class);
				} catch (FileNotFoundException e) {
					throw new RuntimeException(e);
				}
				importRealm(rep, "file " + file);
			}
		}
	}

	public void importRealm(RealmRepresentation rep, String from) {
		KeycloakSession session = sessionFactory.create();
		boolean exists = false;
		try {
			session.getTransaction().begin();

			try {
				RealmManager manager = new RealmManager(session);
				manager.setContextPath(getContextPath());

				if (rep.getId() != null && manager.getRealm(rep.getId()) != null) {
					logger.realmExists(rep.getRealm(), from);
					exists = true;
				}

				if (manager.getRealmByName(rep.getRealm()) != null) {
					logger.realmExists(rep.getRealm(), from);
					exists = true;
				}
				if (!exists) {
					RealmModel realm = manager.importRealm(rep);
					logger.importedRealm(realm.getName(), from);
				}
				session.getTransaction().commit();
			} catch (Throwable t) {
				session.getTransaction().rollback();
				if (!exists) {
					logger.unableToImportRealm(t, rep.getRealm(), from);
				}
			}
		} finally {
			session.close();
		}
	}

	public void importAddUser() {
		String configDir = System.getProperty("jboss.server.config.dir");
		if (configDir != null) {
			File addUserFile = new File(configDir + File.separator + "keycloak-add-user.json");
			if (addUserFile.isFile()) {
				logger.imprtingUsersFrom(addUserFile);

				List<RealmRepresentation> realms;
				try {
					realms = JsonSerialization.readValue(new FileInputStream(addUserFile),
							new TypeReference<List<RealmRepresentation>>() {
							});
				} catch (IOException e) {
					logger.failedToLoadUsers(e);
					return;
				}

				for (RealmRepresentation realmRep : realms) {
					for (UserRepresentation userRep : realmRep.getUsers()) {
						KeycloakSession session = sessionFactory.create();
						try {
							session.getTransaction().begin();

							RealmModel realm = session.realms().getRealmByName(realmRep.getRealm());
							if (realm == null) {
								logger.addUserFailedRealmNotFound(userRep.getUsername(), realmRep.getRealm());
							} else {
								UserModel user = session.users().addUser(realm, userRep.getUsername());
								user.setEnabled(userRep.isEnabled());
								RepresentationToModel.createCredentials(userRep, user);
								RepresentationToModel.createRoleMappings(userRep, user, realm);
							}

							session.getTransaction().commit();
							logger.addUserSuccess(userRep.getUsername(), realmRep.getRealm());
						} catch (ModelDuplicateException e) {
							session.getTransaction().rollback();
							logger.addUserFailedUserExists(userRep.getUsername(), realmRep.getRealm());
						} catch (Throwable t) {
							session.getTransaction().rollback();
							logger.addUserFailed(t, userRep.getUsername(), realmRep.getRealm());
						} finally {
							session.close();
						}
					}
				}

				if (!addUserFile.delete()) {
					logger.failedToDeleteFile(addUserFile.getAbsolutePath());
				}
			}
		}
	}

	private static <T> T loadJson(InputStream is, Class<T> type) {
		try {
			return JsonSerialization.readValue(is, type);
		} catch (IOException e) {
			throw new RuntimeException("Failed to parse json", e);
		}
	}

}
