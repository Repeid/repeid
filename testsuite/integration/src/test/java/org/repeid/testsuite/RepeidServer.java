/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.repeid.testsuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jboss.logging.Logger;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.repeid.common.util.system.JsonSerialization;
import org.repeid.manager.api.rest.KeycloakApplication;

import io.undertow.Undertow;
import io.undertow.Undertow.Builder;
import io.undertow.servlet.api.DefaultServletConfig;
import io.undertow.servlet.api.DeploymentInfo;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */
@SuppressWarnings("deprecation")
public class RepeidServer {

	private static final Logger log = Logger.getLogger(RepeidServer.class);

	private boolean sysout = false;

	private UndertowJaxrsServer server;

	private RepeidServerConfig config;

	public static class RepeidServerConfig {
		private String host = "localhost";
		private int port = 8081;
		private int workerThreads = Math.max(Runtime.getRuntime().availableProcessors(), 2) * 8;
		private String resourcesHome;

		public String getHost() {
			return host;
		}

		public int getPort() {
			return port;
		}

		public String getResourcesHome() {
			return resourcesHome;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public void setResourcesHome(String resourcesHome) {
			this.resourcesHome = resourcesHome;
		}

		public int getWorkerThreads() {
			return workerThreads;
		}

		public void setWorkerThreads(int workerThreads) {
			this.workerThreads = workerThreads;
		}
	}

	public RepeidServer() {
		this(new RepeidServerConfig());
	}

	public RepeidServer(RepeidServerConfig config) {
		this.config = config;
	}

	public static <T> T loadJson(InputStream is, Class<T> type) {
		try {
			return JsonSerialization.readValue(is, type);
		} catch (IOException e) {
			throw new RuntimeException("Failed to parse json", e);
		}
	}

	public static void main(String[] args) throws Throwable {
		bootstrapRepeidServer(args);
	}

	public static RepeidServer bootstrapRepeidServer(String[] args) throws Throwable {
		File f = new File(System.getProperty("user.home"), ".repeid-server.properties");
		if (f.isFile()) {
			Properties p = new Properties();
			p.load(new FileInputStream(f));
			System.getProperties().putAll(p);
		}

		RepeidServerConfig config = new RepeidServerConfig();

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-b")) {
				config.setHost(args[++i]);
			}

			if (args[i].equals("-p")) {
				config.setPort(Integer.valueOf(args[++i]));
			}
		}

		if (System.getProperty("repeid.port") != null) {
			config.setPort(Integer.valueOf(System.getProperty("repeid.port")));
		}

		if (System.getProperty("repeid.bind.address") != null) {
			config.setHost(System.getProperty("repeid.bind.address"));
		}

		if (System.getenv("REPEID_DEV_PORT") != null) {
			config.setPort(Integer.valueOf(System.getenv("REPEID_DEV_PORT")));
		}

		if (System.getProperties().containsKey("undertowWorkerThreads")) {
			int undertowWorkerThreads = Integer.parseInt(System.getProperty("undertowWorkerThreads"));
			config.setWorkerThreads(undertowWorkerThreads);
		}

		final RepeidServer repeid = new RepeidServer(config);
		repeid.sysout = true;
		repeid.start();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				repeid.stop();
			}
		});

		return repeid;
	}

	public UndertowJaxrsServer getServer() {
		return server;
	}

	public RepeidServerConfig getConfig() {
		return config;
	}

	public void start() throws Throwable {
		long start = System.currentTimeMillis();

		ResteasyDeployment deployment = new ResteasyDeployment();
		deployment.setApplicationClass(KeycloakApplication.class.getName());

		Builder builder = Undertow.builder().addHttpListener(config.getPort(), config.getHost())
				.setWorkerThreads(config.getWorkerThreads()).setIoThreads(config.getWorkerThreads() / 8);

		server = new UndertowJaxrsServer();
		try {
			server.start(builder);

			DeploymentInfo di = server.undertowDeployment(deployment, "");
			di.setClassLoader(getClass().getClassLoader());
			di.setContextPath("/repeid");
			di.setDeploymentName("Repeid");
			di.setDefaultEncoding("UTF-8");

			di.setDefaultServletConfig(new DefaultServletConfig(true));

			// FilterInfo filter = Servlets.filter("SessionFilter",
			// RepeidSessionServletFilter.class);
			// di.addFilter(filter);
			// di.addFilterUrlMapping("SessionFilter", "/*",
			// DispatcherType.REQUEST);

			server.deploy(di);

			// sessionFactory = ((KeycloakApplication)
			// deployment.getApplication()).getSessionFactory();

			setupDevConfig();

			if (config.getResourcesHome() != null) {
				info("Loading resources from " + config.getResourcesHome());
			}

			info("Started Repeid (http://" + config.getHost() + ":" + config.getPort() + "/repeid) in "
					+ (System.currentTimeMillis() - start) + " ms\n");
		} catch (RuntimeException e) {
			server.stop();
			throw e;
		}
	}

	protected void setupDevConfig() {

	}

	public void stop() {
		server.stop();

		info("Stopped Repeid");
	}

	private void info(String message) {
		if (sysout) {
			System.out.println(message);
		} else {
			log.info(message);
		}
	}

	@SuppressWarnings("unused")
	private static File file(String... path) {
		StringBuilder s = new StringBuilder();
		for (String p : path) {
			s.append(File.separator);
			s.append(p);
		}
		return new File(s.toString());
	}
}
