package org.repeid.representations.idm.security;

import java.util.HashSet;
import java.util.Set;

public class GrantRolesRepresentation {

    private String userId;
    private Set<String> roleIds = new HashSet<>();

    /**
     * @param roleId
     *            the role
     */
    public void addRoleId(String roleId) {
        roleIds.add(roleId);
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the roleIds
     */
    public Set<String> getRoleIds() {
        return roleIds;
    }

    /**
     * @param roleIds
     *            the roleIds to set
     */
    public void setRoleIds(Set<String> roleIds) {
        this.roleIds = roleIds;
    }

}
