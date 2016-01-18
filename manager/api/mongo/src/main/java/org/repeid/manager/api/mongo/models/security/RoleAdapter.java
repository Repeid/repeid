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
package org.repeid.manager.api.mongo.models.security;

import java.util.Date;
import java.util.Set;

import javax.persistence.EntityManager;

import org.repeid.manager.api.beans.representations.security.PermissionType;
import org.repeid.manager.api.model.security.RoleModel;
import org.repeid.manager.api.mongo.entities.security.MongoRoleEntity;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */

public class RoleAdapter implements RoleModel {

	private MongoRoleEntity roleEntity;
	private EntityManager em;

	public RoleAdapter(EntityManager em, MongoRoleEntity roleEntity) {
		this.em = em;
		this.roleEntity = roleEntity;
	}

	public static MongoRoleEntity toRoleEntity(RoleModel model, EntityManager em) {
		if (model instanceof RoleAdapter) {
			return ((RoleAdapter) model).getRoleEntity();
		}
		return em.getReference(MongoRoleEntity.class, model.getId());
	}

	public MongoRoleEntity getRoleEntity() {
		return roleEntity;
	}

	@Override
	public void commit() {
		em.merge(roleEntity);
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
		return roleEntity.getPermissions();
	}

	@Override
	public void setPermissions(Set<PermissionType> permissions) {
		roleEntity.setPermissions(permissions);
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
