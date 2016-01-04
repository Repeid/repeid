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

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.repeid.manager.api.rest.contract.IUserResource;
import org.repeid.manager.api.rest.contract.exceptions.InvalidSearchCriteriaException;
import org.repeid.manager.api.rest.contract.exceptions.NotAuthorizedException;
import org.repeid.manager.api.rest.contract.exceptions.UserNotFoundException;
import org.repeid.manager.api.rest.impl.util.ExceptionFactory;
import org.repeid.manager.api.rest.managers.SecurityManager;
import org.repeid.models.security.UserModel;
import org.repeid.models.security.UserProvider;
import org.repeid.models.utils.ModelToRepresentation;
import org.repeid.representations.idm.search.SearchCriteriaRepresentation;
import org.repeid.representations.idm.search.SearchResultsRepresentation;
import org.repeid.representations.idm.security.UserRepresentation;

import io.apiman.manager.api.security.ISecurityContext;

/**
 * Implementation of the User API.
 * 
 * @author eric.wittmann@redhat.com
 */
@Stateless
public class UserResourceImpl implements IUserResource {

    @Inject
    private UserProvider userProvider;

    @Inject
    private SecurityManager userManager;

    @Inject
    private ISecurityContext securityContext;

    private UserModel getUserModel(String userId) {
        return userProvider.findById(userId);
    }

    @Override
    public UserRepresentation get(String userId) throws UserNotFoundException {
        UserModel user = getUserModel(userId);
        if (user == null) {
            throw ExceptionFactory.userNotFoundException(userId);
        }
        return ModelToRepresentation.toRepresentation(user);
    }

    @Override
    public void update(String userId, UserRepresentation rep)
            throws UserNotFoundException, NotAuthorizedException {
        if (!securityContext.isAdmin() && !securityContext.getCurrentUser().equals(userId))
            throw ExceptionFactory.notAuthorizedException();
        UserModel user = getUserModel(userId);
        if (user == null) {
            throw ExceptionFactory.userNotFoundException(userId);
        }
        userManager.update(user, rep);
    }

    @Override
    public SearchResultsRepresentation<UserRepresentation> search(SearchCriteriaRepresentation criteria)
            throws InvalidSearchCriteriaException {
        return null;
    }

}
