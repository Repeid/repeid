/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.repeid.manager.api.rest.contract.impl;

import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.repeid.manager.api.rest.contract.IPermissionsResource;
import org.repeid.manager.api.rest.contract.exceptions.NotAuthorizedException;
import org.repeid.manager.api.rest.contract.exceptions.SystemErrorException;
import org.repeid.manager.api.rest.contract.exceptions.UserNotFoundException;
import org.repeid.manager.api.rest.impl.util.ExceptionFactory;
import org.repeid.models.security.UserModel;
import org.repeid.models.security.UserProvider;
import org.repeid.representations.idm.security.PermissionType;
import org.repeid.representations.idm.security.UserPermissionsRepresentation;

import io.apiman.manager.api.core.exceptions.StorageException;
import io.apiman.manager.api.security.ISecurityContext;

/**
 * Implementation of the Permissions API.
 * 
 * @author eric.wittmann@redhat.com
 */
@Stateless
public class PermissionsResourceImpl implements IPermissionsResource {

    @Inject
    private UserProvider userProvider;

    @Inject
    private ISecurityContext securityContext;

    private UserModel getUserModel(String userId) throws StorageException {
        return userProvider.findById(userId);
    }

    @Override
    public UserPermissionsRepresentation getPermissionsForUser(String userId)
            throws UserNotFoundException, NotAuthorizedException {
        if (!securityContext.isAdmin())
            throw ExceptionFactory.notAuthorizedException();

        try {
            UserModel user = getUserModel(userId);
            if (user == null) {
                throw ExceptionFactory.userNotFoundException(userId);
            }
            Set<PermissionType> permissions = userProvider.getPermissions(userId);

            UserPermissionsRepresentation rep = new UserPermissionsRepresentation();
            rep.setUserId(userId);
            rep.setPermissions(permissions);
            return rep;
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public UserPermissionsRepresentation getPermissionsForCurrentUser() throws UserNotFoundException {
        try {
            String userId = securityContext.getCurrentUser();
            UserModel user = getUserModel(userId);
            if (user == null) {
                throw ExceptionFactory.userNotFoundException(userId);
            }
            Set<PermissionType> permissions = userProvider.getPermissions(userId);

            UserPermissionsRepresentation rep = new UserPermissionsRepresentation();
            rep.setUserId(userId);
            rep.setPermissions(permissions);
            return rep;
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

}
