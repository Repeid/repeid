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

package org.keycloak.testsuite.account;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.keycloak.OAuth2Constants;
import org.keycloak.models.AccountRoles;
import org.keycloak.models.ClientModel;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.services.managers.RealmManager;
import org.keycloak.services.resources.RealmsResource;
import org.keycloak.testsuite.Constants;
import org.keycloak.testsuite.OAuthClient;
import org.keycloak.testsuite.pages.AccountApplicationsPage;
import org.keycloak.testsuite.pages.AccountUpdateProfilePage;
import org.keycloak.testsuite.pages.LoginPage;
import org.keycloak.testsuite.pages.OAuthGrantPage;
import org.keycloak.testsuite.rule.KeycloakRule;
import org.keycloak.testsuite.rule.WebResource;
import org.keycloak.testsuite.rule.WebRule;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class ProfileTest {

    @ClassRule
    public static KeycloakRule keycloakRule = new KeycloakRule(new KeycloakRule.KeycloakSetup() {
        @Override
        public void config(RealmManager manager, RealmModel adminstrationRealm, RealmModel appRealm) {
            UserModel user = manager.getSession().users().getUserByUsername("test-user@localhost", appRealm);
            user.setFirstName("First");
            user.setLastName("Last");
            user.setSingleAttribute("key1", "value1");
            user.setSingleAttribute("key2", "value2");

            ClientModel accountApp = appRealm.getClientByClientId(org.keycloak.models.Constants.ACCOUNT_MANAGEMENT_CLIENT_ID);

            UserModel user2 = manager.getSession().users().addUser(appRealm, "test-user-no-access@localhost");
            user2.setEnabled(true);
            for (String r : accountApp.getDefaultRoles()) {
                user2.deleteRoleMapping(accountApp.getRole(r));
            }
            UserCredentialModel creds = new UserCredentialModel();
            creds.setType(CredentialRepresentation.PASSWORD);
            creds.setValue("password");
            user2.updateCredential(creds);

            ClientModel app = appRealm.getClientByClientId("test-app");
            app.addScopeMapping(accountApp.getRole(AccountRoles.VIEW_PROFILE));
            app.addRedirectUri("http://localhost:8081/app/*");
            app.addWebOrigin("http://localtest.me:8081");

            ClientModel thirdParty = appRealm.getClientByClientId("third-party");
            thirdParty.addScopeMapping(accountApp.getRole(AccountRoles.VIEW_PROFILE));
        }
    });

    @Rule
    public WebRule webRule = new WebRule(this);

    @WebResource
    protected WebDriver driver;

    @WebResource
    protected OAuthClient oauth;

    @WebResource
    protected AccountUpdateProfilePage profilePage;

    @WebResource
    protected AccountApplicationsPage accountApplicationsPage;

    @WebResource
    protected LoginPage loginPage;

    @WebResource
    protected OAuthGrantPage grantPage;

    @Test
    public void getProfile() throws Exception {
        oauth.doLogin("test-user@localhost", "password");

        String code = oauth.getCurrentQuery().get(OAuth2Constants.CODE);
        String token = oauth.doAccessTokenRequest(code, "password").getAccessToken();

        HttpResponse response = doGetProfile(token, null);
        assertEquals(200, response.getStatusLine().getStatusCode());
        JSONObject profile = new JSONObject(IOUtils.toString(response.getEntity().getContent()));

        assertEquals("test-user@localhost", profile.getString("username"));
        assertEquals("test-user@localhost", profile.getString("email"));
        assertEquals("First", profile.getString("firstName"));
        assertEquals("Last", profile.getString("lastName"));

        JSONObject attributes = profile.getJSONObject("attributes");
        JSONArray attrValue = attributes.getJSONArray("key1");
        assertEquals(1, attrValue.length());
        assertEquals("value1", attrValue.get(0));
        attrValue = attributes.getJSONArray("key2");
        assertEquals(1, attrValue.length());
        assertEquals("value2", attrValue.get(0));
    }

    @Test
    public void getProfileCors() throws Exception {
        oauth.doLogin("test-user@localhost", "password");

        String code = oauth.getCurrentQuery().get(OAuth2Constants.CODE);
        String token = oauth.doAccessTokenRequest(code, "password").getAccessToken();

        driver.navigate().to("http://localtest.me:8081/app");

        String[] response = doGetProfileJs(token);
        assertEquals("200", response[0]);
    }

    @Test
    public void getProfileCorsInvalidOrigin() throws Exception {
        oauth.doLogin("test-user@localhost", "password");

        String code = oauth.getCurrentQuery().get(OAuth2Constants.CODE);
        String token = oauth.doAccessTokenRequest(code, "password").getAccessToken();

        driver.navigate().to("http://invalid.localtest.me:8081");

        try {
            doGetProfileJs(token);
            fail("Expected failure");
        } catch (Throwable t) {
        }
    }

    @Test
    public void getProfileCookieAuth() throws Exception {
        profilePage.open();
        loginPage.login("test-user@localhost", "password");

        String[] response = doGetProfileJs(null);
        assertEquals("200", response[0]);

        JSONObject profile = new JSONObject(response[1]);
        assertEquals("test-user@localhost", profile.getString("username"));
    }

    @Test
    public void getProfileNoAuth() throws Exception {
        HttpResponse response = doGetProfile(null, null);
        assertEquals(403, response.getStatusLine().getStatusCode());
    }

    @Test
    public void getProfileNoAccess() throws Exception {
        oauth.doLogin("test-user-no-access@localhost", "password");

        String code = oauth.getCurrentQuery().get(OAuth2Constants.CODE);
        String token = oauth.doAccessTokenRequest(code, "password").getAccessToken();

        HttpResponse response = doGetProfile(token, null);
        assertEquals(403, response.getStatusLine().getStatusCode());
    }

    @Test
    public void getProfileOAuthClient() throws Exception {
        oauth.clientId("third-party");
        oauth.doLoginGrant("test-user@localhost", "password");

        grantPage.accept();

        String token = oauth.doAccessTokenRequest(oauth.getCurrentQuery().get(OAuth2Constants.CODE), "password").getAccessToken();
        HttpResponse response = doGetProfile(token, null);

        assertEquals(200, response.getStatusLine().getStatusCode());
        JSONObject profile = new JSONObject(IOUtils.toString(response.getEntity().getContent()));

        assertEquals("test-user@localhost", profile.getString("username"));

        accountApplicationsPage.open();
        accountApplicationsPage.revokeGrant("third-party");
    }

    @Test
    public void getProfileOAuthClientNoScope() throws Exception {
        oauth.clientId("third-party");
        oauth.doLoginGrant("test-user@localhost", "password");

        String token = oauth.doAccessTokenRequest(oauth.getCurrentQuery().get(OAuth2Constants.CODE), "password").getAccessToken();
        HttpResponse response = doGetProfile(token, null);

        assertEquals(403, response.getStatusLine().getStatusCode());
    }

    private URI getAccountURI() {
        return RealmsResource.accountUrl(UriBuilder.fromUri(Constants.AUTH_SERVER_ROOT)).build(oauth.getRealm());
    }

    private HttpResponse doGetProfile(String token, String origin) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(UriBuilder.fromUri(getAccountURI()).build());
        if (token != null) {
            get.setHeader(HttpHeaders.AUTHORIZATION, "bearer " + token);
        }
        if (origin != null) {
            get.setHeader("Origin", origin);
        }
        get.setHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
        return client.execute(get);
    }

    private String[] doGetProfileJs(String token) {
        StringBuilder sb = new StringBuilder();
        sb.append("var req = new XMLHttpRequest();\n");
        sb.append("req.open('GET', '" + getAccountURI().toString() + "', false);\n");
        if (token != null) {
            sb.append("req.setRequestHeader('Authorization', 'Bearer " + token + "');\n");
        }
        sb.append("req.setRequestHeader('Accept', 'application/json');\n");
        sb.append("req.send(null);\n");
        sb.append("return req.status + '///' + req.responseText;\n");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String response = (String) js.executeScript(sb.toString());
        return response.split("///");
    }
}
