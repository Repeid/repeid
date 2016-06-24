package org.repeid.testsuite.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.repeid.Config;
import org.repeid.models.OrganizationProvider;
import org.repeid.models.RepeidSession;
import org.repeid.services.managers.OrganizationManager;
import org.repeid.services.managers.RealmManager;
import org.repeid.testsuite.rule.RepeidRule;
import org.repeid.util.JsonSerialization;


public class AbstractModelTest {

    @ClassRule
    public static RepeidRule kc = new RepeidRule();

    protected RepeidSession session;

    protected OrganizationManager organizationManager;
    protected OrganizationProvider model;

    @Before
    public void before() throws Exception {
        //session = kc.startSession();
        //model = session.realms();
        //organizationManager = new RealmManager(session);
    }

    @After
    public void after() throws Exception {
        /*kc.stopSession(session, true);

        session = kc.startSession();
        try {
            model = session.realms();

            RealmManager rm = new RealmManager(session);
            for (RealmModel realm : model.getRealms()) {
                if (!realm.getName().equals(Config.getAdminRealm())) {
                    rm.removeRealm(realm);
                }
            }
        } finally {
            kc.stopSession(session, true);
        }*/
    }

    protected void commit() {
        commit(false);
    }

    protected void commit(boolean rollback) {
        if (rollback) {
            session.getTransaction().rollback();
        } else {
            session.getTransaction().commit();
        }
        //resetSession();
    }

    /*protected void resetSession() {
        if (session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }
        kc.stopSession(session, false);
        session = kc.startSession();
        model = session.realms();
        organizationManager = new RealmManager(session);
    }

    public static RealmRepresentation loadJson(String path) throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int c;
        while ((c = is.read()) != -1) {
            os.write(c);
        }
        byte[] bytes = os.toByteArray();
        return JsonSerialization.readValue(bytes, RealmRepresentation.class);
    }*/


    // Helper methods for role equality

    /*public static void assertRolesEquals(Set<RoleModel> expected, Set<RoleModel> actual) {
        Assert.assertEquals(expected.size(), actual.size());
        for (RoleModel current : actual) {
            assertRolesContains(current, expected);
        }
    }

    public static void assertRolesContains(RoleModel expected, Set<RoleModel> actual) {
        for (RoleModel current : actual) {
            if (current.getId().equals(expected.getId())) {
                assertRolesEquals(current, expected);
                return;
            }
        }

        Assert.fail("Role with id=" + expected.getId() + " name=" + expected.getName() + " not in set " + actual);
    }*/

    /*public static void assertRolesEquals(RoleModel expected, RoleModel actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getDescription(), actual.getDescription());
        Assert.assertEquals(expected.isScopeParamRequired(), actual.isScopeParamRequired());
        Assert.assertEquals(expected.getContainer(), actual.getContainer());
        Assert.assertEquals(expected.getComposites().size(), actual.getComposites().size());
    }*/
}
