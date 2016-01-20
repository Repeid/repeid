/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
package org.repeid.manager.api.core.config;

import java.util.Properties;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public class SystemEnvProperties extends Properties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getProperty(String key) {
		if (key.startsWith("env.")) {
			return System.getenv().get(key.substring(4));
		} else {
			return System.getProperty(key);
		}
	}

	@Override
	public String getProperty(String key, String defaultValue) {
		String value = getProperty(key);
		return value != null ? value : defaultValue;
	}

}
