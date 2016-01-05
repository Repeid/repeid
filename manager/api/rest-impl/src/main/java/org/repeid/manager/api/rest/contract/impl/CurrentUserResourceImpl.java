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

import java.util.HashSet;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.repeid.manager.api.core.exceptions.StorageException;
import org.repeid.manager.api.core.representations.idm.security.UserRepresentation;
import org.repeid.manager.api.model.security.UserModel;
import org.repeid.manager.api.model.security.UserProvider;
import org.repeid.manager.api.model.utils.SecurityModelToRepresentation;
import org.repeid.manager.api.model.utils.SecurityRepresentationToModel;
import org.repeid.manager.api.rest.contract.ICurrentUserResource;
import org.repeid.manager.api.rest.contract.exceptions.SystemErrorException;
import org.repeid.manager.api.rest.impl.util.ExceptionFactory;
import org.repeid.manager.api.rest.managers.SecurityManager;
import org.repeid.manager.api.security.ISecurityContext;

/**
 * Implementation of the Current User API.
 *
 * @author eric.wittmann@redhat.com
 */
@Stateless
public class CurrentUserResourceImpl implements ICurrentUserResource {

    @Inject
    private UserProvider userProvider;

    @Inject
    private SecurityManager securityManager;

    @Inject
    private SecurityRepresentationToModel securityRepresentationToModel;

    @Inject
    private ISecurityContext securityContext;

    private UserModel getUserModel(String userId) throws StorageException {
        return userProvider.findById(userId);
    }

    @Override
    public UserRepresentation getInfo() {
        String userId = securityContext.getCurrentUser();
        try {
            UserModel user = getUserModel(userId);
            if (user == null) {
                UserRepresentation rep = new UserRepresentation();
                rep.setUsername(userId);
                rep.setFullName(securityContext.getFullName());
                rep.setEmail(securityContext.getEmail());
                rep.setAdmin(securityContext.isAdmin());
                rep.setPermissions(new HashSet<>());
                user = securityRepresentationToModel.createUser(rep, userProvider);
            }
            return SecurityModelToRepresentation.toRepresentation(user);
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public void updateInfo(UserRepresentation rep) {
        String userId = securityContext.getCurrentUser();
        try {
            UserModel user = getUserModel(userId);
            if (user == null) {
                throw ExceptionFactory.userNotFoundException(userId);
            }
            securityManager.update(user, rep);
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

}
