package org.repeid.services.managers;

import org.repeid.models.OrganizationModel;
import org.repeid.models.OrganizationProvider;
import org.repeid.models.RepeidSession;
import org.repeid.representations.idm.OrganizationRepresentation;

public class OrganizationManager {

    protected RepeidSession session;
    protected OrganizationProvider model;
    protected String contextPath = "";
    
    public OrganizationManager(RepeidSession session) {
        this.session = session;
        this.model = session.organizations();
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public RepeidSession getSession() {
        return session;
    }

    public OrganizationModel getOrganization(String id) {
        return model.getOrganization(id);
    }

    public OrganizationModel getOrganizationByName(String name) {
        return model.getOrganizationByName(name);
    }

    public OrganizationModel createOrganization(String name) {
        return createOrganization(null, name);
    }

    public OrganizationModel createOrganization(String id, String name) {
        OrganizationModel realm = model.createOrganization(name);
        return realm;
    }

    public OrganizationModel importOrganization(OrganizationRepresentation rep) {
        OrganizationModel organization = model.createOrganization(null, rep.getName());
        return organization;
    }

    public OrganizationModel getRepeidAdminstrationRealm() {
        // TODO Auto-generated method stub
        return null;
    }

}
