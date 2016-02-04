/*******************************************************************************
 * Repeid, Home of Professional Open Source
 *
 * Copyright 2015 Sistcoop, Inc. and/or its affiliates.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.repeid.manager.api.mongo;

import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.net.ssl.SSLSocketFactory;

import org.jboss.logging.Logger;
import org.repeid.manager.api.core.config.Config;
import org.repeid.manager.api.mongo.api.MongoStore;
import org.repeid.manager.api.mongo.api.context.MongoStoreInvocationContext;
import org.repeid.manager.api.mongo.impl.context.TransactionMongoStoreInvocationContext;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public class DefaultMongoConnectionProvider implements MongoConnectionProvider {

	private String[] entities = new String[] {};

	private static final Logger logger = Logger.getLogger(DefaultMongoConnectionProvider.class);

	private volatile MongoClient client;
	private DB db;

	private MongoStore mongoStore;

	private Config.Scope config;

	private Map<String, String> operationalInfo;

	@PostConstruct
	public void init() {
		String realmProvider = Config.getProvider("realm");
		if (realmProvider.equalsIgnoreCase("mongo")) {
			config = Config.scope("connectionsMongo", "default");
			lazyInit();
		}
	}

	@PreDestroy
	@Override
	public void close() {
		if (client != null) {
			client.close();
		}
	}

	@Override
	public DB getDB() {
		return db;
	}

	@Override
	public MongoStore getMongoStore() {
		return mongoStore;
	}

	@Override
	public MongoStoreInvocationContext getInvocationContext() {
		return new TransactionMongoStoreInvocationContext(mongoStore);
	}

	@SuppressWarnings("deprecation")
	private void lazyInit() {
		if (client == null) {
			synchronized (this) {
				if (client == null) {
					try {
						this.client = createMongoClient();

						String dbName = config.get("db", "repeid");
						this.db = client.getDB(dbName);

						String databaseSchema = config.get("databaseSchema");
						if (databaseSchema != null) {
							if (databaseSchema.equals("update")) {
								// MongoUpdaterProvider mongoUpdater =
								// session.getProvider(MongoUpdaterProvider.class);

								/*
								 * if (mongoUpdater == null) { throw new
								 * RuntimeException(
								 * "Can't update database: Mongo updater provider not found"
								 * ); }
								 * 
								 * mongoUpdater.update(session, db);
								 */
							} else {
								throw new RuntimeException("Invalid value for databaseSchema: " + databaseSchema);
							}
						}

						// this.mongoStore = new MongoStoreImpl(db,
						// getManagedEntities());
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private Class[] getManagedEntities() throws ClassNotFoundException {
		Class[] entityClasses = new Class[entities.length];
		for (int i = 0; i < entities.length; i++) {
			entityClasses[i] = Thread.currentThread().getContextClassLoader().loadClass(entities[i]);
		}
		return entityClasses;
	}

	/**
	 * Override this method if you want more possibility to configure Mongo
	 * client. It can be also used to inject mongo client from different source.
	 *
	 * This method can assume that "config" is already set and can use it.
	 *
	 * @return mongoClient instance, which will be shared for whole Repeid
	 *
	 * @throws UnknownHostException
	 */
	protected MongoClient createMongoClient() throws UnknownHostException {
		operationalInfo = new LinkedHashMap<>();
		String dbName = config.get("db", "repeid");

		String uriString = config.get("uri");
		if (uriString != null) {
			MongoClientURI uri = new MongoClientURI(uriString);
			MongoClient client = new MongoClient(uri);

			StringBuilder hostsBuilder = new StringBuilder();
			for (int i = 0; i < uri.getHosts().size(); i++) {
				if (i != 0) {
					hostsBuilder.append(", ");
				}
				hostsBuilder.append(uri.getHosts().get(i));
			}
			String hosts = hostsBuilder.toString();

			operationalInfo.put("mongoHosts", hosts);
			operationalInfo.put("mongoDatabaseName", dbName);
			operationalInfo.put("mongoUser", uri.getUsername());

			logger.debugv("Initialized mongo model. host(s): %s, db: %s", uri.getHosts(), dbName);
			return client;
		} else {
			String host = config.get("host", ServerAddress.defaultHost());
			int port = config.getInt("port", ServerAddress.defaultPort());

			String user = config.get("user");
			String password = config.get("password");

			MongoClientOptions clientOptions = getClientOptions();

			MongoClient client;
			if (user != null && password != null) {
				MongoCredential credential = MongoCredential.createMongoCRCredential(user, dbName,
						password.toCharArray());
				client = new MongoClient(new ServerAddress(host, port), Collections.singletonList(credential),
						clientOptions);
			} else {
				client = new MongoClient(new ServerAddress(host, port), clientOptions);
			}

			operationalInfo.put("mongoServerAddress", client.getAddress().toString());
			operationalInfo.put("mongoDatabaseName", dbName);
			operationalInfo.put("mongoUser", user);

			logger.debugv("Initialized mongo model. host: %s, port: %d, db: %s", host, port, dbName);
			return client;
		}
	}

	protected MongoClientOptions getClientOptions() {
		MongoClientOptions.Builder builder = MongoClientOptions.builder();
		checkIntOption("connectionsPerHost", builder);
		checkIntOption("threadsAllowedToBlockForConnectionMultiplier", builder);
		checkIntOption("maxWaitTime", builder);
		checkIntOption("connectTimeout", builder);
		checkIntOption("socketTimeout", builder);
		checkBooleanOption("socketKeepAlive", builder);
		checkBooleanOption("autoConnectRetry", builder);
		if (config.getBoolean("ssl", false)) {
			builder.socketFactory(SSLSocketFactory.getDefault());
		}

		return builder.build();
	}

	protected void checkBooleanOption(String optionName, MongoClientOptions.Builder builder) {
		Boolean val = config.getBoolean(optionName);
		if (val != null) {
			try {
				Method m = MongoClientOptions.Builder.class.getMethod(optionName, boolean.class);
				m.invoke(builder, val);
			} catch (Exception e) {
				throw new IllegalStateException(
						"Problem configuring boolean option " + optionName
								+ " for mongo client. Ensure you used correct value true or false and if this option is supported by mongo driver",
						e);
			}
		}
	}

	protected void checkIntOption(String optionName, MongoClientOptions.Builder builder) {
		Integer val = config.getInt(optionName);
		if (val != null) {
			try {
				Method m = MongoClientOptions.Builder.class.getMethod(optionName, int.class);
				m.invoke(builder, val);
			} catch (Exception e) {
				throw new IllegalStateException(
						"Problem configuring int option " + optionName
								+ " for mongo client. Ensure you used correct value (number) and if this option is supported by mongo driver",
						e);
			}
		}
	}

	// @Override
	public Map<String, String> getOperationalInfo() {
		return operationalInfo;
	}

}
