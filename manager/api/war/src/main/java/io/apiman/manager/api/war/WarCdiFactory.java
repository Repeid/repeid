/*
 * Copyright 2015 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.apiman.manager.api.war;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.repeid.manager.api.security.ISecurityContext;
import org.repeid.manager.api.security.impl.DefaultSecurityContext;
import org.repeid.manager.api.security.impl.KeycloakSecurityContext;

/**
 * Attempt to create producer methods for CDI beans.
 *
 * @author eric.wittmann@redhat.com
 */
@ApplicationScoped
public class WarCdiFactory {	

	@Produces
	@ApplicationScoped
	public static ISecurityContext provideSecurityContext(WarApiManagerConfig config,
			@Inject DefaultSecurityContext defaultSC, @New KeycloakSecurityContext keycloakSC) {
		if ("default".equals(config.getSecurityContextType())) { //$NON-NLS-1$
			return defaultSC;
		} else if ("keycloak".equals(config.getSecurityContextType())) { //$NON-NLS-1$
			return keycloakSC;
		} else {
			throw new RuntimeException("Unknown security context type: " + config.getSecurityContextType()); //$NON-NLS-1$
		}
	}
	
}
