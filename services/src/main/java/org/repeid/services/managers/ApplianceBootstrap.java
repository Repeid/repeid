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
package org.repeid.services.managers;

import org.repeid.models.RepeidSession;
import org.repeid.services.ServicesLogger;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class ApplianceBootstrap {

    private static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;
    private final RepeidSession session;

    public ApplianceBootstrap(RepeidSession session) {
        this.session = session;
    }

    public boolean isNewInstall() {
        return false;
    }

    public boolean isNoMasterUser() {
        return false;
    }

    public boolean createMasterRealm(String contextPath) {
        return true;
    }

    public void createMasterRealmUser(String username, String password) {

    }

}
