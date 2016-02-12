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
package org.repeid.manager.api.jpa.models.security;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.repeid.manager.api.beans.representations.security.PermissionType;
import org.repeid.manager.api.jpa.entities.security.RoleEntity;
import org.repeid.manager.api.model.security.RoleModel;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public class RoleAdapter implements RoleModel {

	private RoleEntity roleEntity;

	@SuppressWarnings("unused")
	private EntityManager em;

	public RoleAdapter(EntityManager em, RoleEntity roleEntity) {
		this.em = em;
		this.roleEntity = roleEntity;
	}

	public static RoleEntity toRoleEntity(RoleModel model, EntityManager em) {
		if (model instanceof RoleAdapter) {
			return ((RoleAdapter) model).getRoleEntity();
		}
		return em.getReference(RoleEntity.class, model.getId());
	}

	public RoleEntity getRoleEntity() {
		return roleEntity;
	}

	@Override
	public String getId() {
		return roleEntity.getId();
	}

	@Override
	public String getName() {
		return roleEntity.getName();
	}

	@Override
	public String getDescription() {
		return roleEntity.getDescription();
	}

	@Override
	public void setDescription(String description) {
		roleEntity.setDescription(description);
	}

	@Override
	public String getCreatedBy() {
		return roleEntity.getCreatedBy();
	}

	@Override
	public Date getCreatedOn() {
		return roleEntity.getCreatedOn();
	}

	@Override
	public boolean isAutoGrant() {
		return roleEntity.getAutoGrant();
	}

	@Override
	public void setAutoGrant(boolean autoGrant) {
		roleEntity.setAutoGrant(autoGrant);
	}

	@Override
	public Set<PermissionType> getPermissions() {
		Set<PermissionType> permissions = new HashSet<>();
		for (String str : roleEntity.getPermissions()) {
			permissions.add(PermissionType.valueOf(str));
		}
		return permissions;
	}

	@Override
	public void setPermissions(Set<PermissionType> permissions) {
		Set<String> str = new HashSet<>();
		for (PermissionType permissionType : permissions) {
			str.add(permissionType.toString());
		}
		roleEntity.setPermissions(str);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RoleModel))
			return false;
		RoleModel other = (RoleModel) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
