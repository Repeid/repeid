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

package org.repeid.manager.api.rest.contract.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.beans.representations.search.SearchCriteriaRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchResultsRepresentation;
import org.repeid.manager.api.beans.representations.security.UserRepresentation;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;
import org.repeid.manager.api.model.security.UserModel;
import org.repeid.manager.api.model.security.UserProvider;
import org.repeid.manager.api.model.utils.SecurityModelToRepresentation;
import org.repeid.manager.api.rest.contract.IUserResource;
import org.repeid.manager.api.rest.contract.exceptions.InvalidSearchCriteriaException;
import org.repeid.manager.api.rest.contract.exceptions.NotAuthorizedException;
import org.repeid.manager.api.rest.contract.exceptions.SystemErrorException;
import org.repeid.manager.api.rest.contract.exceptions.UserNotFoundException;
import org.repeid.manager.api.rest.impl.util.ExceptionFactory;
import org.repeid.manager.api.rest.impl.util.SearchCriteriaUtil;
import org.repeid.manager.api.rest.managers.SecurityManager;
import org.repeid.manager.api.security.ISecurityContext;

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
    private SecurityManager securityManager;

    @Inject
    private ISecurityContext securityContext;

    private UserModel getUserModel(String userId) throws StorageException {
        return userProvider.findById(userId);
    }

    @Override
    public UserRepresentation get(String userId) throws UserNotFoundException {
        try {
            UserModel user = getUserModel(userId);
            if (user == null) {
                throw ExceptionFactory.userNotFoundException(userId);
            }
            return SecurityModelToRepresentation.toRepresentation(user);
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public void update(String userId, UserRepresentation rep)
            throws UserNotFoundException, NotAuthorizedException {
        if (!securityContext.isAdmin() && !securityContext.getCurrentUser().equals(userId))
            throw ExceptionFactory.notAuthorizedException();

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

    @Override
    public SearchResultsRepresentation<UserRepresentation> search(SearchCriteriaRepresentation criteria)
            throws InvalidSearchCriteriaException {
        try {
            // Validate criteria
            SearchCriteriaUtil.validateSearchCriteria(criteria);
            SearchCriteriaModel criteriaModel = SearchCriteriaUtil.getSearchCriteriaModel(criteria);

            // extract filterText
            String filterText = criteria.getFilterText();

            // search
            SearchResultsModel<UserModel> results = null;
            if (filterText == null || filterText.trim().isEmpty()) {
                results = userProvider.search(criteriaModel);
            } else {
                results = userProvider.search(criteriaModel, filterText);
            }

            SearchResultsRepresentation<UserRepresentation> rep = new SearchResultsRepresentation<>();
            List<UserRepresentation> items = new ArrayList<>();
            for (UserModel model : results.getModels()) {
                items.add(SecurityModelToRepresentation.toRepresentation(model));
            }
            rep.setItems(items);
            rep.setTotalSize(results.getTotalSize());
            return rep;
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

}
