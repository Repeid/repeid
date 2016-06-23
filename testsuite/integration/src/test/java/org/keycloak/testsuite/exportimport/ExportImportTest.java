package org.keycloak.testsuite.exportimport;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.keycloak.testsuite.model.AbstractModelTest;
import org.keycloak.testsuite.model.ImportTest;
import org.keycloak.testsuite.rule.KeycloakRule;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;

/**
 * TODO: Move to integration-arquillian and make subclass of AbstractExportImportTest
 */
public class ExportImportTest {

    //private static SystemPropertiesHelper propsHelper = new SystemPropertiesHelper();

    private static final String JPA_CONNECTION_URL = "repeid.connectionsJpa.url";

    // We want data to be persisted among server restarts
    /*private static ExternalResource persistenceSetupRule = new ExternalResource() {

        @Override
        protected void before() throws Throwable {
            if (System.getProperty(JPA_CONNECTION_URL) == null) {
                String baseExportImportDir = getExportImportTestDirectory();

                File oldDBFile = new File(baseExportImportDir, "repeidDB.h2.db");
                if (oldDBFile.exists()) {
                    oldDBFile.delete();
                }

                String dbDir = baseExportImportDir + "/repeidDB";
                propsHelper.pushProperty(JPA_CONNECTION_URL, "jdbc:h2:file:" + dbDir + ";DB_CLOSE_DELAY=-1");
            }
        }

        @Override
        protected void after() {
            propsHelper.pullProperty(JPA_CONNECTION_URL);
        }
    };*/

    /*private static KeycloakRule keycloakRule = new KeycloakRule( new KeycloakRule.KeycloakSetup() {

        @Override
        public void config(RealmManager manager, RealmModel adminstrationRealm, RealmModel appRealm) {
            // Create some users in "test" and "master" realms
            addUser(manager.getSession().users(), appRealm, "user1", "password");
            addUser(manager.getSession().users(), appRealm, "user2", "password");
            addUser(manager.getSession().users(), appRealm, "user3", "password");

            // Import "test-realm" realm
            try {
                RealmRepresentation rep = AbstractModelTest.loadJson("model/testrealm.json");
                rep.setId("test-realm");
                RealmModel demoRealm = manager.importRealm(rep);
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        }

    }) {
        @Override
        protected void after() {
            super.after();

            // TODO: Make this subclass of AbstractExportImportTest and use AbstractExportImportTest.clearExportImportProperties

            // Clear export/import properties after test
            Properties systemProps = System.getProperties();
            Set<String> propsToRemove = new HashSet<String>();

            for (Object key : systemProps.keySet()) {
                if (key.toString().startsWith(ExportImportConfig.PREFIX)) {
                    propsToRemove.add(key.toString());
                }
            }

            for (String propToRemove : propsToRemove) {
                systemProps.remove(propToRemove);
            }
        }

        protected String[] getTestRealms() {
            return new String[]{"test", "demo", "test-realm"};
        }
    };*/

    /*@ClassRule
    public static TestRule chain = RuleChain
            .outerRule(persistenceSetupRule)
            .around(keycloakRule);

    @Test
    public void testDirFullExportImport() throws Throwable {
        ExportImportConfig.setProvider(DirExportProviderFactory.PROVIDER_ID);
        String targetDirPath = getExportImportTestDirectory() + File.separator + "dirExport";
        DirExportProvider.recursiveDeleteDir(new File(targetDirPath));
        ExportImportConfig.setDir(targetDirPath);
        ExportImportConfig.setUsersPerFile(ExportImportConfig.DEFAULT_USERS_PER_FILE);

        testFullExportImport();

        // There should be 6 files in target directory (3 realm, 3 user)
        Assert.assertEquals(6, new File(targetDirPath).listFiles().length);
    }

    @Test
    public void testDirRealmExportImport() throws Throwable {
        ExportImportConfig.setProvider(DirExportProviderFactory.PROVIDER_ID);
        String targetDirPath = getExportImportTestDirectory() + File.separator + "dirRealmExport";
        DirExportProvider.recursiveDeleteDir(new File(targetDirPath));
        ExportImportConfig.setDir(targetDirPath);
        ExportImportConfig.setUsersPerFile(3);

        testRealmExportImport();

        // There should be 3 files in target directory (1 realm, 3 user)
        File[] files = new File(targetDirPath).listFiles();
        Assert.assertEquals(4, files.length);
    }*/

    /*@Test
    public void testSingleFileFullExportImport() throws Throwable {
        ExportImportConfig.setProvider(SingleFileExportProviderFactory.PROVIDER_ID);
        String targetFilePath = getExportImportTestDirectory() + File.separator + "singleFile-full.json";
        ExportImportConfig.setFile(targetFilePath);

        testFullExportImport();
    }

    @Test
    public void testSingleFileRealmExportImport() throws Throwable {
        ExportImportConfig.setProvider(SingleFileExportProviderFactory.PROVIDER_ID);
        String targetFilePath = getExportImportTestDirectory() + File.separator + "singleFile-realm.json";
        ExportImportConfig.setFile(targetFilePath);

        testRealmExportImport();
    }

    @Test
    public void testSingleFileRealmWithoutBuiltinsImport() throws Throwable {
        // Remove test realm
        KeycloakSession session = keycloakRule.startSession();
        try {
            new RealmManager(session).removeRealm(session.realms().getRealmByName("test-realm"));
        } finally {
            keycloakRule.stopSession(session, true);
        }

        // Set the realm, which doesn't have builtin clients/roles inside JSON
        ExportImportConfig.setProvider(SingleFileExportProviderFactory.PROVIDER_ID);
        URL url = ExportImportTest.class.getResource("/model/testrealm.json");
        String targetFilePath = new File(url.getFile()).getAbsolutePath();
        ExportImportConfig.setFile(targetFilePath);

        ExportImportConfig.setAction(ExportImportConfig.ACTION_IMPORT);

        // Restart server to trigger import
        keycloakRule.restartServer();

        // Ensure realm imported
        session = keycloakRule.startSession();
        try {
            RealmModel testRealmRealm = session.realms().getRealmByName("test-realm");
            ImportTest.assertDataImportedInRealm(session, testRealmRealm);
        } finally {
            keycloakRule.stopSession(session, true);
        }
    }*/

    /*private void testFullExportImport() {
        ExportImportConfig.setAction(ExportImportConfig.ACTION_EXPORT);
        ExportImportConfig.setRealmName(null);

        // Restart server, which triggers export
        keycloakRule.restartServer();

        // Delete some realm (and some data in admin realm)
        KeycloakSession session = keycloakRule.startSession();
        try {
            RealmProvider realmProvider = session.realms();
            UserProvider userProvider = session.users();
            new RealmManager(session).removeRealm(realmProvider.getRealmByName("test"));
            new RealmManager(session).removeRealm(realmProvider.getRealmByName("test-realm"));
            Assert.assertEquals(1, realmProvider.getRealms().size());

            assertNotAuthenticated(userProvider, realmProvider, "test", "test-user@localhost", "password");
            assertNotAuthenticated(userProvider, realmProvider, "test", "user1", "password");
            assertNotAuthenticated(userProvider, realmProvider, "test", "user2", "password");
            assertNotAuthenticated(userProvider, realmProvider, "test", "user3", "password");
        } finally {
            keycloakRule.stopSession(session, true);
        }

        // Configure import
        ExportImportConfig.setAction(ExportImportConfig.ACTION_IMPORT);

        // Restart server, which triggers import
        keycloakRule.restartServer();

        // Ensure data are imported back
        session = keycloakRule.startSession();
        try {
            RealmProvider model = session.realms();
            UserProvider userProvider = session.users();
            Assert.assertEquals(3, model.getRealms().size());

            assertAuthenticated(userProvider, model, "test", "test-user@localhost", "password");
            assertAuthenticated(userProvider, model, "test", "user1", "password");
            assertAuthenticated(userProvider, model, "test", "user2", "password");
            assertAuthenticated(userProvider, model, "test", "user3", "password");

            RealmModel testRealmRealm = model.getRealmByName("test-realm");
            ImportTest.assertDataImportedInRealm(session, testRealmRealm);
        } finally {
            keycloakRule.stopSession(session, true);
        }
    }

    private void testRealmExportImport() {
        ExportImportConfig.setAction(ExportImportConfig.ACTION_EXPORT);
        ExportImportConfig.setRealmName("test");

        // Restart server, which triggers export
        keycloakRule.restartServer();

        // Delete some realm (and some data in admin realm)
        KeycloakSession session = keycloakRule.startSession();
        try {
            RealmProvider realmProvider = session.realms();
            UserProvider userProvider = session.users();
            new RealmManager(session).removeRealm(realmProvider.getRealmByName("test"));
            Assert.assertEquals(2, realmProvider.getRealms().size());

            assertNotAuthenticated(userProvider, realmProvider, "test", "test-user@localhost", "password");
            assertNotAuthenticated(userProvider, realmProvider, "test", "user1", "password");
            assertNotAuthenticated(userProvider, realmProvider, "test", "user2", "password");
            assertNotAuthenticated(userProvider, realmProvider, "test", "user3", "password");
        } finally {
            keycloakRule.stopSession(session, true);
        }

        // Configure import
        ExportImportConfig.setAction(ExportImportConfig.ACTION_IMPORT);

        // Restart server, which triggers import
        keycloakRule.restartServer();

        // Ensure data are imported back, but just for "test" realm
        session = keycloakRule.startSession();
        try {
            RealmProvider realmProvider = session.realms();
            UserProvider userProvider = session.users();
            Assert.assertEquals(3, realmProvider.getRealms().size());

            assertAuthenticated(userProvider, realmProvider, "test", "test-user@localhost", "password");
            assertAuthenticated(userProvider, realmProvider, "test", "user1", "password");
            assertAuthenticated(userProvider, realmProvider, "test", "user2", "password");
            assertAuthenticated(userProvider, realmProvider, "test", "user3", "password");
        } finally {
            keycloakRule.stopSession(session, true);
        }
    }

    private void assertAuthenticated(UserProvider userProvider, RealmProvider realmProvider, String realmName, String username, String password) {
        RealmModel realm = realmProvider.getRealmByName(realmName);
        if (realm == null) {
            Assert.fail("realm " + realmName + " not found");
        }

        UserModel user = userProvider.getUserByUsername(username, realm);
        if (user == null) {
            Assert.fail("user " + username + " not found");
        }

        KeycloakSession session = keycloakRule.startSession();
        try {
            Assert.assertTrue(userProvider.validCredentials(session, realm, user, UserCredentialModel.password(password)));
        } finally {
            keycloakRule.stopSession(session, true);
        }
    }

    private void assertNotAuthenticated(UserProvider userProvider, RealmProvider realmProvider, String realmName, String username, String password) {
        RealmModel realm = realmProvider.getRealmByName(realmName);
        if (realm == null) {
            return;
        }

        UserModel user = userProvider.getUserByUsername(username, realm);
        if (user == null) {
            return;
        }

        KeycloakSession session = keycloakRule.startSession();
        try {
            Assert.assertFalse(userProvider.validCredentials(session, realm, user, UserCredentialModel.password(password)));
        } finally {
            keycloakRule.stopSession(session, true);
        }
    }

    private static void addUser(UserProvider userProvider, RealmModel appRealm, String username, String password) {
        UserModel user = userProvider.addUser(appRealm, username);
        user.setEmail(username + "@test.com");
        user.setEnabled(true);

        UserCredentialModel creds = new UserCredentialModel();
        creds.setType(CredentialRepresentation.PASSWORD);
        creds.setValue(password);
        user.updateCredential(creds);
    }

    private static String getExportImportTestDirectory() {
        String dirPath = null;
        String relativeDirExportImportPath = "testsuite" + File.separator + "integration" + File.separator + "target" + File.separator + "export-import";

        if (System.getProperties().containsKey("maven.home")) {
            dirPath = System.getProperty("user.dir").replaceFirst("testsuite.integration.*", Matcher.quoteReplacement(relativeDirExportImportPath));
        } else {
            for (String c : System.getProperty("java.class.path").split(File.pathSeparator)) {
                if (c.contains(File.separator + "testsuite" + File.separator + "integration")) {
                    dirPath = c.replaceFirst("testsuite.integration.*", Matcher.quoteReplacement(relativeDirExportImportPath));
                }
            }
        }

        String absolutePath = new File(dirPath).getAbsolutePath();
        return absolutePath;
    }

    private static class SystemPropertiesHelper {

        private Map<String,String> previousValues = new HashMap<String,String>();

        private void pushProperty(String name, String value) {
            String currentValue = System.getProperty(name);
            previousValues.put(name, currentValue);
            System.setProperty(name, value);
        }

        private void pullProperty(String name) {
            if (previousValues.containsKey(name)) {
                String prevValue = previousValues.get(name);

                if (prevValue == null) {
                    System.getProperties().remove(name);
                } else {
                    System.setProperty(name, prevValue);
                }
            }
        }

    }*/

}
