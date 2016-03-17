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

package org.repeid.manager.api.model.utils;

import java.security.Key;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

/**
 * Set of helper methods, which are useful in various model implementations.
 *
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public final class KeycloakModelUtils {

	private KeycloakModelUtils() {
	}

	public static String generateId() {
		return UUID.randomUUID().toString();
	}

	public static Key getSecretKey(String secret) {
		return secret != null ? new SecretKeySpec(secret.getBytes(), "HmacSHA256") : null;
	}

	public static String getDefaultClientAuthenticatorType() {
		return "client-secret";
	}

	public static String generateCodeSecret() {
		return UUID.randomUUID().toString();
	}

	public static String getMasterRealmAdminApplicationClientId(String realmName) {
		return realmName + "-realm";
	}

	// END USER FEDERATION RELATED STUFF

	public static String toLowerCaseSafe(String str) {
		return str == null ? null : str.toLowerCase();
	}

	// Used for hardcoded role mappers
	public static String[] parseRole(String role) {
		int scopeIndex = role.lastIndexOf('.');
		if (scopeIndex > -1) {
			String appName = role.substring(0, scopeIndex);
			role = role.substring(scopeIndex + 1);
			String[] rtn = { appName, role };
			return rtn;
		} else {
			String[] rtn = { null, role };
			return rtn;

		}
	}

}
