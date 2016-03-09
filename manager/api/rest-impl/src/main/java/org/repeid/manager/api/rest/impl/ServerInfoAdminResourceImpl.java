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

package org.repeid.manager.api.rest.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Context;

import org.repeid.manager.api.beans.representations.info.MemoryInfoRepresentation;
import org.repeid.manager.api.beans.representations.info.ServerInfoRepresentation;
import org.repeid.manager.api.beans.representations.info.SystemInfoRepresentation;
import org.repeid.manager.api.mode.events.EventType;
import org.repeid.manager.api.mode.events.admin.OperationType;
import org.repeid.manager.api.model.provider.KeycloakSession;
import org.repeid.manager.api.rest.ServerInfoAdminResource;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class ServerInfoAdminResourceImpl implements ServerInfoAdminResource {

	private static final Map<String, List<String>> ENUMS = createEnumsMap(EventType.class, OperationType.class);

	@Context
	private KeycloakSession session;

	public ServerInfoRepresentation getInfo() {
		ServerInfoRepresentation info = new ServerInfoRepresentation();
		info.setSystemInfo(SystemInfoRepresentation.create(session.getKeycloakSessionFactory().getServerStartupTimestamp()));
		info.setMemoryInfo(MemoryInfoRepresentation.create());

		info.setEnums(ENUMS);
		return info;
	}

	@SuppressWarnings("rawtypes")
	private static Map<String, List<String>> createEnumsMap(Class... enums) {
		Map<String, List<String>> m = new HashMap<>();
		for (Class e : enums) {
			String n = e.getSimpleName();
			n = Character.toLowerCase(n.charAt(0)) + n.substring(1);

			List<String> l = new LinkedList<>();
			for (Object c : e.getEnumConstants()) {
				l.add(c.toString());
			}
			Collections.sort(l);

			m.put(n, l);
		}
		return m;
	}
}
