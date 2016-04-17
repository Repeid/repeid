package org.repeid.services;

import java.net.URI;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.repeid.common.ClientConnection;
import org.repeid.models.OrganizationModel;
import org.repeid.models.RepeidContext;
import org.repeid.models.RepeidSession;
import org.repeid.services.resources.RepeidApplication;

public class DefaultRepeidContext implements RepeidContext {

    private OrganizationModel organization;

    //private ClientModel client;

    private ClientConnection connection;

    private RepeidSession session;

    public DefaultRepeidContext(RepeidSession session) {
        this.session = session;
    }

    @Override
    public URI getAuthServerUrl() {
        UriInfo uri = getUri();
        RepeidApplication keycloakApplication = getContextObject(RepeidApplication.class);
        return keycloakApplication.getBaseUri(uri);
    }

    @Override
    public String getContextPath() {
        RepeidApplication app = getContextObject(RepeidApplication.class);
        return app.getContextPath();
    }

    @Override
    public UriInfo getUri() {
        return getContextObject(UriInfo.class);
    }

    @Override
    public HttpHeaders getRequestHeaders() {
        return getContextObject(HttpHeaders.class);
    }

    @Override
    public <T> T getContextObject(Class<T> clazz) {
        return ResteasyProviderFactory.getContextData(clazz);
    }

    @Override
    public OrganizationModel getOrganization() {
        return organization;
    }

    @Override
    public void setOrganization(OrganizationModel organization) {
        this.organization = organization;
    }

    /*@Override
    public ClientModel getClient() {
        return client;
    }

    @Override
    public void setClient(ClientModel client) {
        this.client = client;
    }*/

    @Override
    public ClientConnection getConnection() {
        return connection;
    }

    @Override
    public void setConnection(ClientConnection connection) {
        this.connection = connection;
    }

    /*@Override
    public RealmImporter getRealmManager() {
        RealmManager manager = new RealmManager(session);
        manager.setContextPath(getContextPath());
        return manager;
    }

    @Override
    public Locale resolveLocale(UserModel user) {
        return LocaleHelper.getLocale(session, realm, user);
    }*/
}
