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

import javax.persistence.EntityManager;

import org.repeid.manager.api.jpa.entities.security.UserEntity;
import org.repeid.manager.api.model.security.UserModel;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */

public class UserAdapter implements UserModel {

	private UserEntity userEntity;
	private EntityManager em;

	public UserAdapter(EntityManager em, UserEntity userEntity) {
		this.em = em;
		this.userEntity = userEntity;
	}

	public static UserEntity toUserEntity(UserModel model, EntityManager em) {
		if (model instanceof UserAdapter) {
			return ((UserAdapter) model).getUserEntity();
		}
		return em.getReference(UserEntity.class, model.getId());
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	@Override
	public void commit() {
		em.merge(userEntity);
	}

	@Override
	public String getId() {
		return userEntity.getId();
	}

	@Override
	public String getUsername() {
		return userEntity.getUsername();
	}

	@Override
	public String getFullName() {
		return userEntity.getFullName();
	}

	@Override
	public void setFullName(String fullName) {
		userEntity.setFullName(fullName);
	}

	@Override
	public String getEmail() {
		return userEntity.getEmail();
	}

	@Override
	public void setEmail(String email) {
		userEntity.setEmail(email);
	}

	@Override
	public Date getJoinedOn() {
		return userEntity.getJoinedOn();
	}

	@Override
	public boolean isAdmin() {
		return userEntity.isAdmin();
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
		if (!(obj instanceof UserModel))
			return false;
		UserModel other = (UserModel) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
