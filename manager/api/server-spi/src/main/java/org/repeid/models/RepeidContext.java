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

package org.repeid.models;

import java.net.URI;
import java.util.Locale;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

import org.repeid.common.ClientConnection;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public interface RepeidContext {

    URI getAuthServerUrl();

    String getContextPath();

    UriInfo getUri();

    HttpHeaders getRequestHeaders();

    <T> T getContextObject(Class<T> clazz);

    OrganizationModel getOrganization();

    void setOrganization(OrganizationModel organization);

    //ClientModel getClient();

    //void setClient(ClientModel client);

    ClientConnection getConnection();

    void setConnection(ClientConnection connection);

    //RealmImporter getRealmManager();

    //Locale resolveLocale(UserModel user);

}