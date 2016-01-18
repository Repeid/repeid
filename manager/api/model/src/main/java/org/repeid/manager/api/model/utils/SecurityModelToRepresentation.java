/*******************************************************************************
 * Repeid, Home of Professional Open Source
 *
 * Copyright 2015 Sistcoop, Inc. and/or its affiliates.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.repeid.manager.api.model.utils;

import org.repeid.manager.api.beans.representations.security.RoleRepresentation;
import org.repeid.manager.api.beans.representations.security.UserRepresentation;
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
