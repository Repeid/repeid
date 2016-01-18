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
package org.repeid.manager.api.security.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.repeid.manager.api.beans.representations.security.PermissionType;

/**
 * A class that optimizes the user permissions for querying.
 *
 * @author eric.wittmann@redhat.com
 */
public class IndexedPermissions implements Serializable {

	private static final long serialVersionUID = -474966481686691421L;

	private Set<PermissionType> qualifiedPermissions = new HashSet<>();

	/**
	 * Constructor.
	 * 
	 * @param permissions
	 *            the permissions
	 */
	public IndexedPermissions(Set<PermissionType> permissions) {
		index(permissions);
	}

	/**
	 * Returns true if the qualified permission exists.
	 * 
	 * @param permissionName
	 *            the permission name
	 * @param orgQualifier
	 *            the org qualifier
	 * @return true if has qualified permission
	 */
	public boolean hasQualifiedPermission(PermissionType permissionNames) {
		return qualifiedPermissions.contains(permissionNames);
	}

	/**
	 * Index the permissions.
	 * 
	 * @param bean
	 */
	private void index(Set<PermissionType> permissions) {
		qualifiedPermissions = permissions;
	}

}
