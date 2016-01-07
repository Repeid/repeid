package org.repeid.manager.api.model.utils;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.repeid.manager.api.core.exceptions.StorageException;
import org.repeid.manager.api.core.representations.idm.security.RoleRepresentation;
import org.repeid.manager.api.core.representations.idm.security.UserRepresentation;
import org.repeid.manager.api.model.security.RoleModel;
import org.repeid.manager.api.model.security.RoleProvider;
import org.repeid.manager.api.model.security.UserModel;
import org.repeid.manager.api.model.security.UserProvider;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SecurityRepresentationToModel {

	public RoleModel createRole(RoleRepresentation rep, RoleProvider provider, String createdBy) throws StorageException {
        return provider.create(rep.getName(), rep.getDescription(), rep.getAutoGrant(), rep.getPermissions(),
                createdBy);
	}

	public UserModel createUser(UserRepresentation rep, UserProvider provider) throws StorageException {
		return provider.create(rep.getUsername(), rep.getFullName(), rep.getEmail());
	}
}
