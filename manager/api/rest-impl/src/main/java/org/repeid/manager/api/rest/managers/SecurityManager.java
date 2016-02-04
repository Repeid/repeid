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
package org.repeid.manager.api.rest.managers;

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.beans.representations.security.RoleRepresentation;
import org.repeid.manager.api.beans.representations.security.UserRepresentation;
import org.repeid.manager.api.model.security.RoleModel;
import org.repeid.manager.api.model.security.UserModel;

public class SecurityManager {

	public void update(UserModel model, UserRepresentation rep) throws StorageException {
		/*
		 * model.setFullName(rep.getFullName()); model.setEmail(rep.getEmail());
		 * model.commit();
		 */
	}

	public void update(RoleModel model, RoleRepresentation rep) throws StorageException {
		/*
		 * model.setDescription(rep.getDescription());
		 * model.setAutoGrant(rep.getAutoGrant());
		 * model.setPermissions(rep.getPermissions()); model.commit();
		 */
	}

}