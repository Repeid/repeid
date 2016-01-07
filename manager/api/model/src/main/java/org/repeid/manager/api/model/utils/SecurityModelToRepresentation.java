package org.repeid.manager.api.model.utils;

import org.repeid.manager.api.core.representations.idm.security.RoleRepresentation;
import org.repeid.manager.api.core.representations.idm.security.UserRepresentation;
import org.repeid.manager.api.model.security.RoleModel;
import org.repeid.manager.api.model.security.UserModel;

public class SecurityModelToRepresentation {

	public static UserRepresentation toRepresentation(UserModel model) {
		if (model == null)
			return null;

		UserRepresentation rep = new UserRepresentation();
		rep.setUsername(model.getUsername());
		rep.setFullName(model.getFullName());
		rep.setEmail(model.getEmail());
		rep.setJoinedOn(model.getJoinedOn());
		rep.setAdmin(model.isAdmin());
		// rep.setPermissions(permissions);
		return rep;
	}

	public static RoleRepresentation toRepresentation(RoleModel model) {
		if (model == null)
			return null;

		RoleRepresentation rep = new RoleRepresentation();
		rep.setId(model.getId());
		rep.setName(model.getName());
		rep.setAutoGrant(model.isAutoGrant());
		rep.setCreatedBy(model.getCreatedBy());
		rep.setCreatedOn(model.getCreatedOn());
		rep.setDescription(model.getDescription());
		rep.setPermissions(model.getPermissions());

		return rep;
	}

}
