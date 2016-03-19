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

package org.repeid.manager.api.core.config;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.repeid.common.util.SystemEnvProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

@Startup
@Singleton
public class RepeidConfigStarter {

	private static final Logger log = LoggerFactory.getLogger(RepeidConfigStarter.class);

	@PostConstruct
	public void init() {
		lazyInit();
		log.info("RepeidApplication started");
	}

	@PreDestroy
	public void close() {
		log.info("Stopping RepeidApplication");
	}

	public void lazyInit() {
		try {
			JsonNode node = null;

			String configDir = System.getProperty("jboss.server.config.dir");
			if (configDir != null) {
				File f = new File(configDir + File.separator + "repeid-server.json");
				if (f.isFile()) {
					log.info("Load config from " + f.getAbsolutePath());
					node = new ObjectMapper().readTree(f);
				}
			}

			if (node == null) {
				URL resource = Thread.currentThread().getContextClassLoader()
						.getResource("META-INF/repeid-server.json");
				if (resource != null) {
					log.info("Load config from " + resource);
					node = new ObjectMapper().readTree(resource);
				}
			}

			if (node != null) {
				Properties properties = new SystemEnvProperties();
				Config.init(new JsonConfigProvider(node, properties));
				return;
			} else {
				throw new RuntimeException("Config 'repeid-server.json' not found");
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to load config", e);
		}
	}

}
