package org.repeid.manager.api.core.representations.idm.security;

import java.util.Date;
import java.util.Set;

public class UserRepresentation {

    private String username;
    private String fullName;
    private String email;
    private Date joinedOn;

    // Used only when returning information about the current user
    private boolean admin;
    private Set<PermissionType> permissions;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(Date joinedOn) {
        this.joinedOn = joinedOn;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Set<PermissionType> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionType> permissions) {
        this.permissions = permissions;
    }

}
