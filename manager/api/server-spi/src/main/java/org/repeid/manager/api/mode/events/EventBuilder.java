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

package org.repeid.manager.api.mode.events;

import java.util.HashMap;
import java.util.List;

import org.jboss.logging.Logger;
import org.repeid.manager.api.model.system.ClientModel;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class EventBuilder {

	private static final Logger log = Logger.getLogger(EventBuilder.class);

	private EventStoreProvider store;
	private List<EventListenerProvider> listeners;
	private Event event;

	public EventBuilder realm(String realmId) {
		event.setRealmId(realmId);
		return this;
	}

	public EventBuilder client(ClientModel client) {
		event.setClientId(client.getClientId());
		return this;
	}

	public EventBuilder client(String clientId) {
		event.setClientId(clientId);
		return this;
	}

	public EventBuilder user(String userId) {
		event.setUserId(userId);
		return this;
	}

	public EventBuilder session(String sessionId) {
		event.setSessionId(sessionId);
		return this;
	}

	public EventBuilder ipAddress(String ipAddress) {
		event.setIpAddress(ipAddress);
		return this;
	}

	public EventBuilder event(EventType e) {
		event.setType(e);
		return this;
	}

	public EventBuilder detail(String key, String value) {
		if (value == null || value.equals("")) {
			return this;
		}

		if (event.getDetails() == null) {
			event.setDetails(new HashMap<String, String>());
		}
		event.getDetails().put(key, value);
		return this;
	}

	public EventBuilder removeDetail(String key) {
		if (event.getDetails() != null) {
			event.getDetails().remove(key);
		}
		return this;
	}

	public Event getEvent() {
		return event;
	}

	public void success() {
		// send();
	}

	public void error(String error) {
		/*
		 * if (!event.getType().name().endsWith("_ERROR")) {
		 * event.setType(EventType.valueOf(event.getType().name() + "_ERROR"));
		 * } event.setError(error); send();
		 */
	}

}
