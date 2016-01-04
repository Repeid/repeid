package org.repeid.manager.api.rest.managers;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.repeid.models.security.RoleModel;
import org.repeid.models.security.UserModel;
import org.repeid.representations.idm.security.RoleRepresentation;
import org.repeid.representations.idm.security.UserRepresentation;

import io.apiman.manager.api.core.exceptions.StorageException;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SecurityManager {

    public void update(UserModel model, UserRepresentation rep) throws StorageException {
        model.setFullName(rep.getFullName());
        model.setEmail(rep.getEmail());
        model.commit();
    }

    public void update(RoleModel model, RoleRepresentation rep) throws StorageException {
        model.setDescription(rep.getDescription());
        model.commit();
    }

}