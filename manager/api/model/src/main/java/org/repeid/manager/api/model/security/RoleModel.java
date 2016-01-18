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
package org.repeid.manager.api.model.security;

import java.util.Date;
import java.util.Set;

import org.repeid.manager.api.beans.representations.security.PermissionType;
import org.repeid.manager.api.model.Model;

public interface RoleModel extends Model {

	String getId();

	String getName();

	String getDescription();

	void setDescription(String description);

	String getCreatedBy();

	Date getCreatedOn();

	boolean isAutoGrant();

	void setAutoGrant(boolean autoGrant);

	Set<PermissionType> getPermissions();

	void setPermissions(Set<PermissionType> permissions);

}
