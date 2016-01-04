package org.repeid.models.security;

import java.util.Date;
import java.util.Set;

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

    void setAutoGrant(boolean autoGrant);

    PermissionType getPermissions();

    void setPermissions(Set<PermissionType> permissions);

}
