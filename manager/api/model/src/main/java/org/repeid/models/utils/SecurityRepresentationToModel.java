package org.repeid.models.utils;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.repeid.models.security.RoleModel;
import org.repeid.models.security.RoleProvider;
import org.repeid.models.security.UserModel;
import org.repeid.models.security.UserProvider;
import org.repeid.representations.idm.security.RoleRepresentation;
import org.repeid.representations.idm.security.UserRepresentation;

import io.apiman.manager.api.core.exceptions.StorageException;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SecurityRepresentationToModel {

    public RoleModel createRole(RoleRepresentation rep, RoleProvider provider) throws StorageException {
        return provider.create(rep.getName(), rep.getDescription(), rep.getAutoGrant(), rep.getPermissions());
    }

    public UserModel createUser(UserRepresentation rep, UserProvider userProvider) {
        // TODO Auto-generated method stub
        return null;
    }
}
