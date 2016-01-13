package org.repeid.manager.api.mongo.models.security;

import java.util.Date;

import javax.persistence.EntityManager;

import org.repeid.manager.api.model.security.UserModel;
import org.repeid.manager.api.mongo.entities.security.UserEntity;

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
