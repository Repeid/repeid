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

import org.repeid.manager.api.rest.contract.ICurrentUserResource;
import org.repeid.manager.api.rest.managers.SecurityManager;
import org.repeid.models.security.UserModel;
import org.repeid.models.security.UserProvider;
import org.repeid.models.utils.ModelToRepresentation;
import org.repeid.models.utils.RepresentationToModel;
import org.repeid.representations.idm.security.UserRepresentation;

import io.apiman.manager.api.security.ISecurityContext;

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
    private RepresentationToModel representationToModel;

    @Inject
    private ISecurityContext securityContext;

    private UserModel getUserModel(String userId) {
        return userProvider.findById(userId);
    }

    @Override
    public UserRepresentation getInfo() {
        String userId = securityContext.getCurrentUser();

        UserModel user = getUserModel(userId);
        if (user == null) {
            UserRepresentation rep = new UserRepresentation();
            rep.setUsername(userId);
            rep.setFullName(securityContext.getFullName());
            rep.setEmail(securityContext.getEmail());
            rep.setAdmin(securityContext.isAdmin());
            rep.setPermissions(new HashSet<>());
            user = representationToModel.createUser(rep, userProvider);
        }
        return ModelToRepresentation.toRepresentation(user);
    }

    @Override
    public void updateInfo(UserRepresentation rep) {
        String userId = securityContext.getCurrentUser();

        UserModel user = getUserModel(userId);
        securityManager.update(user, rep);
    }

}
