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

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public class SystemPropertiesScope implements Scope {

	private String prefix;

	public SystemPropertiesScope(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public String get(String key) {
		return get(key, null);
	}

	@Override
	public String get(String key, String defaultValue) {
		return System.getProperty(prefix + key, defaultValue);
	}

	@Override
	public String[] getArray(String key) {
		String value = get(key);
		if (value != null) {
			String[] a = value.split(",");
			for (int i = 0; i < a.length; i++) {
				a[i] = a[i].trim();
			}
			return a;
		} else {
			return null;
		}
	}

	@Override
	public Integer getInt(String key) {
		return getInt(key, null);
	}

	@Override
	public Integer getInt(String key, Integer defaultValue) {
		String v = get(key, null);
		return v != null ? Integer.parseInt(v) : defaultValue;
	}

	@Override
	public Long getLong(String key) {
		return getLong(key, null);
	}

	@Override
	public Long getLong(String key, Long defaultValue) {
		String v = get(key, null);
		return v != null ? Long.parseLong(v) : defaultValue;
	}

	@Override
	public Boolean getBoolean(String key) {
		return getBoolean(key, null);
	}

	@Override
	public Boolean getBoolean(String key, Boolean defaultValue) {
		String v = get(key, null);
		return v != null ? Boolean.parseBoolean(v) : defaultValue;
	}

	@Override
	public Scope scope(String... scope) {
		StringBuilder sb = new StringBuilder();
		sb.append(prefix + ".");
		for (String s : scope) {
			sb.append(s);
			sb.append(".");
		}
		return new SystemPropertiesScope(sb.toString());
	}
}
