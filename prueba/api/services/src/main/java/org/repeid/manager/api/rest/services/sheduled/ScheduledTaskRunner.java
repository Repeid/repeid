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

package org.repeid.manager.api.rest.services.sheduled;

import org.repeid.manager.api.model.provider.KeycloakSession;
import org.repeid.manager.api.model.provider.KeycloakSessionFactory;
import org.repeid.manager.api.rest.services.ServicesLogger;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class ScheduledTaskRunner implements Runnable {

    private static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

    private final KeycloakSessionFactory sessionFactory;
    private final ScheduledTask task;

    public ScheduledTaskRunner(org.repeid.manager.api.model.provider.KeycloakSessionFactory sessionFactory, ScheduledTask task) {
        this.sessionFactory = sessionFactory;
        this.task = task;
    }

    @Override
    public void run() {
        KeycloakSession session = sessionFactory.create();
        try {
            session.getTransaction().begin();
            task.run(session);
            session.getTransaction().commit();

            logger.debug("Executed scheduled task " + task.getClass().getSimpleName());
        } catch (Throwable t) {
            logger.failedToRunScheduledTask(t, task.getClass().getSimpleName());

            session.getTransaction().rollback();
        } finally {
            try {
                session.close();
            } catch (Throwable t) {
                logger.failedToCloseProviderSession(t);
            }
        }
    }

}
