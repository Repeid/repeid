/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
package org.repeid.manager.api.core.config;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huertas
 *
 */
public class KeycloakApplication {

	private static final Logger log = LoggerFactory.getLogger(KeycloakApplication.class);

	public static void loadConfig() {
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
				RepeidConfig.init(new JsonConfigProvider(node, properties));
				return;
			} else {
				throw new RuntimeException("Config 'keycloak-server.json' not found");
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to load config", e);
		}
	}

}
