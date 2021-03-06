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

package org.repeid.testsuite.model;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class UserModelTest extends AbstractModelTest {

    @Test
    public void persistUser() {
        /*RealmModel realm = organizationManager.createRealm("original");
        KeycloakSession session = organizationManager.getSession();
        UserModel user = session.users().addUser(realm, "user");
        user.setFirstName("first-name");
        user.setLastName("last-name");
        user.setEmail("email");
        assertNotNull(user.getCreatedTimestamp());
        // test that timestamp is current with 10s tollerance
        Assert.assertTrue((System.currentTimeMillis() - user.getCreatedTimestamp()) < 10000);

        user.addRequiredAction(RequiredAction.CONFIGURE_TOTP);
        user.addRequiredAction(RequiredAction.UPDATE_PASSWORD);

        RealmModel searchRealm = organizationManager.getRealm(realm.getId());
        UserModel persisted = session.users().getUserByUsername("user", searchRealm);

        assertEquals(user, persisted);

        searchRealm = organizationManager.getRealm(realm.getId());
        UserModel persisted2 =  session.users().getUserById(user.getId(), searchRealm);
        assertEquals(user, persisted2);

        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put(UserModel.LAST_NAME, "last-name");
        List<UserModel> search = session.users().searchForUserByAttributes(attributes, realm);
        Assert.assertEquals(search.size(), 1);
        Assert.assertEquals(search.get(0).getUsername(), "user");

        attributes.clear();
        attributes.put(UserModel.EMAIL, "email");
        search = session.users().searchForUserByAttributes(attributes, realm);
        Assert.assertEquals(search.size(), 1);
        Assert.assertEquals(search.get(0).getUsername(), "user");

        attributes.clear();
        attributes.put(UserModel.LAST_NAME, "last-name");
        attributes.put(UserModel.EMAIL, "email");
        search = session.users().searchForUserByAttributes(attributes, realm);
        Assert.assertEquals(search.size(), 1);
        Assert.assertEquals(search.get(0).getUsername(), "user");*/
    }
    
    /*@Test
    public void webOriginSetTest() {
        RealmModel realm = organizationManager.createRealm("original");
        ClientModel client = realm.addClient("user");

        Assert.assertTrue(client.getWebOrigins().isEmpty());

        client.addWebOrigin("origin-1");
        Assert.assertEquals(1, client.getWebOrigins().size());

        client.addWebOrigin("origin-2");
        Assert.assertEquals(2, client.getWebOrigins().size());

        client.removeWebOrigin("origin-2");
        Assert.assertEquals(1, client.getWebOrigins().size());

        client.removeWebOrigin("origin-1");
        Assert.assertTrue(client.getWebOrigins().isEmpty());

        client = realm.addClient("oauthclient2");

        Assert.assertTrue(client.getWebOrigins().isEmpty());

        client.addWebOrigin("origin-1");
        Assert.assertEquals(1, client.getWebOrigins().size());

        client.addWebOrigin("origin-2");
        Assert.assertEquals(2, client.getWebOrigins().size());

        client.removeWebOrigin("origin-2");
        Assert.assertEquals(1, client.getWebOrigins().size());

        client.removeWebOrigin("origin-1");
        Assert.assertTrue(client.getWebOrigins().isEmpty());

    }

    @Test
    public void testUserRequiredActions() throws Exception {
        RealmModel realm = organizationManager.createRealm("original");
        UserModel user = session.users().addUser(realm, "user");

        Assert.assertTrue(user.getRequiredActions().isEmpty());

        user.addRequiredAction(RequiredAction.CONFIGURE_TOTP);
        String id = realm.getId();
        commit();
        realm = organizationManager.getRealm(id);
        user = session.users().getUserByUsername("user", realm);

        Assert.assertEquals(1, user.getRequiredActions().size());
        Assert.assertTrue(user.getRequiredActions().contains(RequiredAction.CONFIGURE_TOTP.name()));

        user.addRequiredAction(RequiredAction.CONFIGURE_TOTP);
        user = session.users().getUserByUsername("user", realm);

        Assert.assertEquals(1, user.getRequiredActions().size());
        Assert.assertTrue(user.getRequiredActions().contains(RequiredAction.CONFIGURE_TOTP.name()));

        user.addRequiredAction(RequiredAction.VERIFY_EMAIL.name());
        user = session.users().getUserByUsername("user", realm);

        Assert.assertEquals(2, user.getRequiredActions().size());
        Assert.assertTrue(user.getRequiredActions().contains(RequiredAction.CONFIGURE_TOTP.name()));
        Assert.assertTrue(user.getRequiredActions().contains(RequiredAction.VERIFY_EMAIL.name()));

        user.removeRequiredAction(RequiredAction.CONFIGURE_TOTP.name());
        user = session.users().getUserByUsername("user", realm);

        Assert.assertEquals(1, user.getRequiredActions().size());
        Assert.assertTrue(user.getRequiredActions().contains(RequiredAction.VERIFY_EMAIL.name()));

        user.removeRequiredAction(RequiredAction.VERIFY_EMAIL.name());
        user = session.users().getUserByUsername("user", realm);

        Assert.assertTrue(user.getRequiredActions().isEmpty());
    }

    @Test
    public void testUserMultipleAttributes() throws Exception {
        RealmModel realm = organizationManager.createRealm("original");
        UserModel user = session.users().addUser(realm, "user");
        UserModel userNoAttrs = session.users().addUser(realm, "user-noattrs");

        user.setSingleAttribute("key1", "value1");
        List<String> attrVals = new ArrayList<>(Arrays.asList( "val21", "val22" ));
        user.setAttribute("key2", attrVals);

        commit();

        // Test read attributes
        realm = organizationManager.getRealmByName("original");
        user = session.users().getUserByUsername("user", realm);

        attrVals = user.getAttribute("key1");
        Assert.assertEquals(1, attrVals.size());
        Assert.assertEquals("value1", attrVals.get(0));
        Assert.assertEquals("value1", user.getFirstAttribute("key1"));

        attrVals = user.getAttribute("key2");
        Assert.assertEquals(2, attrVals.size());
        Assert.assertTrue(attrVals.contains("val21"));
        Assert.assertTrue(attrVals.contains("val22"));

        attrVals = user.getAttribute("key3");
        Assert.assertTrue(attrVals.isEmpty());
        Assert.assertNull(user.getFirstAttribute("key3"));

        Map<String, List<String>> allAttrVals = user.getAttributes();
        Assert.assertEquals(2, allAttrVals.size());
        Assert.assertEquals(allAttrVals.get("key1"), user.getAttribute("key1"));
        Assert.assertEquals(allAttrVals.get("key2"), user.getAttribute("key2"));

        // Test remove and rewrite attribute
        user.removeAttribute("key1");
        user.setSingleAttribute("key2", "val23");

        commit();

        realm = organizationManager.getRealmByName("original");
        user = session.users().getUserByUsername("user", realm);
        Assert.assertNull(user.getFirstAttribute("key1"));
        attrVals = user.getAttribute("key2");
        Assert.assertEquals(1, attrVals.size());
        Assert.assertEquals("val23", attrVals.get(0));
    }

    @Test
    public void testSearchByString() {
        RealmModel realm = organizationManager.createRealm("original");
        UserModel user1 = session.users().addUser(realm, "user1");

        commit();

        List<UserModel> users = session.users().searchForUser("user", realm, 0, 7);
        Assert.assertTrue(users.contains(user1));
    }

    @Test
    public void testSearchByUserAttribute() throws Exception {
        RealmModel realm = organizationManager.createRealm("original");
        UserModel user1 = session.users().addUser(realm, "user1");
        UserModel user2 = session.users().addUser(realm, "user2");
        UserModel user3 = session.users().addUser(realm, "user3");

        user1.setSingleAttribute("key1", "value1");
        user1.setSingleAttribute("key2", "value21");

        user2.setSingleAttribute("key1", "value1");
        user2.setSingleAttribute("key2", "value22");

        user3.setSingleAttribute("key2", "value21");

        commit();

        List<UserModel> users = session.users().searchForUserByUserAttribute("key1", "value1", realm);
        Assert.assertEquals(2, users.size());
        Assert.assertTrue(users.contains(user1));
        Assert.assertTrue(users.contains(user2));

        users = session.users().searchForUserByUserAttribute("key2", "value21", realm);
        Assert.assertEquals(2, users.size());
        Assert.assertTrue(users.contains(user1));
        Assert.assertTrue(users.contains(user3));

        users = session.users().searchForUserByUserAttribute("key2", "value22", realm);
        Assert.assertEquals(1, users.size());
        Assert.assertTrue(users.contains(user2));

        users = session.users().searchForUserByUserAttribute("key3", "value3", realm);
        Assert.assertEquals(0, users.size());
    }

    @Test
    public void testServiceAccountLink() throws Exception {
        RealmModel realm = organizationManager.createRealm("original");
        ClientModel client = realm.addClient("foo");

        UserModel user1 = session.users().addUser(realm, "user1");
        user1.setFirstName("John");
        user1.setLastName("Doe");

        UserModel user2 = session.users().addUser(realm, "user2");
        user2.setFirstName("John");
        user2.setLastName("Doe");

        // Search
        Assert.assertNull(session.users().getUserByServiceAccountClient(client));
        List<UserModel> users = session.users().searchForUser("John Doe", realm);
        Assert.assertEquals(2, users.size());
        Assert.assertTrue(users.contains(user1));
        Assert.assertTrue(users.contains(user2));

        // Link service account
        user1.setServiceAccountClientLink(client.getId());

        commit();

        // Search and assert service account user not found
        realm = organizationManager.getRealmByName("original");
        UserModel searched = session.users().getUserByServiceAccountClient(client);
        Assert.assertEquals(searched, user1);
        users = session.users().searchForUser("John Doe", realm);
        Assert.assertEquals(1, users.size());
        Assert.assertFalse(users.contains(user1));
        Assert.assertTrue(users.contains(user2));

        users = session.users().getUsers(realm, false);
        Assert.assertEquals(1, users.size());
        Assert.assertFalse(users.contains(user1));
        Assert.assertTrue(users.contains(user2));

        users = session.users().getUsers(realm, true);
        Assert.assertEquals(2, users.size());
        Assert.assertTrue(users.contains(user1));
        Assert.assertTrue(users.contains(user2));

        Assert.assertEquals(2, session.users().getUsersCount(realm));

        // Remove client
        new ClientManager(organizationManager).removeClient(realm, client);
        commit();

        // Assert service account removed as well
        realm = organizationManager.getRealmByName("original");
        Assert.assertNull(session.users().getUserByUsername("user1", realm));
    }

    @Test
    public void testGrantToAll() {
        RealmModel realm1 = organizationManager.createRealm("realm1");
        RoleModel role1 = realm1.addRole("role1");
        UserModel user1 = organizationManager.getSession().users().addUser(realm1, "user1");
        UserModel user2 = organizationManager.getSession().users().addUser(realm1, "user2");

        RealmModel realm2 = organizationManager.createRealm("realm2");
        UserModel realm2User1 = organizationManager.getSession().users().addUser(realm2, "user1");

        commit();

        realm1 = organizationManager.getRealmByName("realm1");
        role1 = realm1.getRole("role1");
        organizationManager.getSession().users().grantToAllUsers(realm1, role1);

        commit();

        realm1 = organizationManager.getRealmByName("realm1");
        role1 = realm1.getRole("role1");
        user1 = organizationManager.getSession().users().getUserByUsername("user1", realm1);
        user2 = organizationManager.getSession().users().getUserByUsername("user2", realm1);
        Assert.assertTrue(user1.hasRole(role1));
        Assert.assertTrue(user2.hasRole(role1));

        realm2 = organizationManager.getRealmByName("realm2");
        realm2User1 = organizationManager.getSession().users().getUserByUsername("user1", realm2);
        Assert.assertFalse(realm2User1.hasRole(role1));
    }

    public static void assertEquals(UserModel expected, UserModel actual) {
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getCreatedTimestamp(), actual.getCreatedTimestamp());
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals(expected.getLastName(), actual.getLastName());

        String[] expectedRequiredActions = expected.getRequiredActions().toArray(new String[expected.getRequiredActions().size()]);
        Arrays.sort(expectedRequiredActions);
        String[] actualRequiredActions = actual.getRequiredActions().toArray(new String[actual.getRequiredActions().size()]);
        Arrays.sort(actualRequiredActions);

        Assert.assertArrayEquals(expectedRequiredActions, actualRequiredActions);
    }*/

}

