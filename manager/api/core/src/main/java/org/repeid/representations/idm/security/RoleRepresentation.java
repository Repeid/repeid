package org.repeid.representations.idm.security;

import java.util.Date;
import java.util.Set;

import io.apiman.manager.api.beans.idm.PermissionType;

public class RoleRepresentation {

    private String id;
    private String name;
    private String description;
    private String createdBy;
    private Date createdOn;
    private Boolean autoGrant;
    private Set<PermissionType> permissions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getAutoGrant() {
        return autoGrant;
    }

    public void setAutoGrant(Boolean autoGrant) {
        this.autoGrant = autoGrant;
    }

    public Set<PermissionType> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionType> permissions) {
        this.permissions = permissions;
    }

}
