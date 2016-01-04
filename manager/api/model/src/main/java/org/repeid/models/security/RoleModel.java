package org.repeid.models.security;

import java.util.Date;

import org.repeid.models.Model;

import io.apiman.manager.api.beans.idm.PermissionType;

public interface RoleModel extends Model {

    String getId();

    String getName();

    String getDescription();

    void setDescription(String description);

    String getCreatedBy();

    Date getCreatedon();

    boolean isAutoGrant();

    PermissionType getPermissions();

    void setPermissions(PermissionType permissions);

}
