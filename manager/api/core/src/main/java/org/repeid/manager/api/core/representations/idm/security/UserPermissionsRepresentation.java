package org.repeid.manager.api.core.representations.idm.security;

import java.util.Set;

public class UserPermissionsRepresentation {

    private String userId;
    private Set<PermissionType> permissions;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<PermissionType> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionType> permissions) {
        this.permissions = permissions;
    }

}
