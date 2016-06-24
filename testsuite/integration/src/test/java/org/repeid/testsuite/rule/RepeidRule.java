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
package org.repeid.testsuite.rule;

import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.repeid.Config;
import org.repeid.models.RepeidSession;
import org.repeid.services.managers.RealmManager;
import org.repeid.testsuite.ApplicationServlet;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class RepeidRule extends AbstractRepeidRule {

    private KeycloakSetup setup;

    public RepeidRule() {
    }

    public RepeidRule(KeycloakSetup setup) {
        this.setup = setup;
    }

    /*@Override
    protected void setupKeycloak() {
        importRealm();

        if (setup != null) {
            configure(setup);
        }

        deployServlet("app", "/app", ApplicationServlet.class);
    }*/

    protected void importRealm() {
        server.importRealm(getClass().getResourceAsStream("/testrealm.json"));
    }

    public void configure(KeycloakSetup configurer) {
        RepeidSession session = server.getSessionFactory().create();
        session.getTransaction().begin();

        try {
           /* RealmManager manager = new RealmManager(session);
            manager.setContextPath("/auth");

            RealmModel adminstrationRealm = manager.getRealm(Config.getAdminRealm());
            RealmModel appRealm = manager.getRealm("test");

            configurer.session = session;
            configurer.config(manager, adminstrationRealm, appRealm);

            session.getTransaction().commit();*/
        } finally {
            session.close();
        }
    }

    /*public void update(KeycloakSetup configurer) {
        update(configurer, "test");
    }*/


    /*public void removeUserSession(String sessionId) {
        RepeidSession session = startSession();
        RealmModel realm = session.realms().getRealm("test");
        UserSessionModel userSession = session.sessions().getUserSession(realm, sessionId);
        assertNotNull(userSession);
        session.sessions().removeUserSession(realm, userSession);
        stopSession(session, true);
    }*/

    /*public ClientSessionCode verifyCode(String code) {
        RepeidSession session = startSession();
        try {
            RealmModel realm = session.realms().getRealm("test");
            try {
                ClientSessionCode accessCode = ClientSessionCode.parse(code, session, realm);
                if (accessCode == null) {
                    Assert.fail("Invalid code");
                }
                return accessCode;
            } catch (Throwable t) {
                throw new AssertionError("Failed to parse code", t);
            }
        } finally {
            stopSession(session, false);
        }
    }*/

    public abstract static class KeycloakSetup {

        protected RepeidSession session;

        //public abstract void config(RealmManager manager, RealmModel adminstrationRealm, RealmModel appRealm);

    }

}
