package org.repeid.manager.api.model.security;

import java.util.Date;
import java.util.Set;

import org.repeid.manager.api.core.representations.idm.security.PermissionType;
import org.repeid.manager.api.model.Model;

public interface RoleModel extends Model {

	String getId();

	String getName();

	String getDescription();

	void setDescription(String description);

	String getCreatedBy();

	Date getCreatedon();

	boolean isAutoGrant();

	void setAutoGrant(boolean autoGrant);

	Set<PermissionType> getPermissions();

	void setPermissions(Set<PermissionType> permissions);

}
