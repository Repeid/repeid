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
import org.repeid.manager.api.beans.representations.security.RoleRepresentation;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;
import org.repeid.manager.api.model.security.RoleModel;
import org.repeid.manager.api.model.security.RoleProvider;
import org.repeid.manager.api.model.utils.SecurityModelToRepresentation;
import org.repeid.manager.api.model.utils.SecurityRepresentationToModel;
import org.repeid.manager.api.rest.contract.IRoleResource;
import org.repeid.manager.api.rest.contract.exceptions.InvalidSearchCriteriaException;
import org.repeid.manager.api.rest.contract.exceptions.NotAuthorizedException;
import org.repeid.manager.api.rest.contract.exceptions.RoleAlreadyExistsException;
import org.repeid.manager.api.rest.contract.exceptions.RoleNotFoundException;
import org.repeid.manager.api.rest.contract.exceptions.SystemErrorException;
import org.repeid.manager.api.rest.impl.util.ExceptionFactory;
import org.repeid.manager.api.rest.impl.util.SearchCriteriaUtil;
import org.repeid.manager.api.rest.managers.SecurityManager;
import org.repeid.manager.api.security.ISecurityContext;

/**
 * Implementation of the Role API.
 * 
 * @author eric.wittmann@redhat.com
 */
@Stateless
public class RoleResourceImpl implements IRoleResource {

    @Inject
    private RoleProvider roleProvider;

    @Inject
    private SecurityManager securityManager;

    @Inject
    private SecurityRepresentationToModel securityRepresentationToModel;

    @Inject
    private ISecurityContext securityContext;

    private RoleModel getRoleModel(String roleId) throws StorageException {
        return roleProvider.findById(roleId);
    }

    @Override
    public RoleRepresentation create(RoleRepresentation rep)
            throws RoleAlreadyExistsException, NotAuthorizedException {
        if (!securityContext.isAdmin())
            throw ExceptionFactory.notAuthorizedException();

        try {
            RoleModel role = securityRepresentationToModel.createRole(rep, roleProvider,
                    securityContext.getCurrentUser());
            return SecurityModelToRepresentation.toRepresentation(role);
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public RoleRepresentation get(String roleId) throws RoleNotFoundException, NotAuthorizedException {
        try {
            RoleModel role = getRoleModel(roleId);
            if (role == null) {
                throw ExceptionFactory.roleNotFoundException(roleId);
            }
            return SecurityModelToRepresentation.toRepresentation(role);
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public void update(String roleId, RoleRepresentation rep)
            throws RoleNotFoundException, NotAuthorizedException {
        if (!securityContext.isAdmin())
            throw ExceptionFactory.notAuthorizedException();

        try {
            RoleModel role = getRoleModel(roleId);
            securityManager.update(role, rep);
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public void delete(String roleId) throws RoleNotFoundException, NotAuthorizedException {
        if (!securityContext.isAdmin())
            throw ExceptionFactory.notAuthorizedException();

        try {
            RoleModel role = getRoleModel(roleId);
            if (role == null) {
                throw ExceptionFactory.roleNotFoundException(roleId);
            }
            @SuppressWarnings("unused")
            boolean result = roleProvider.remove(role);
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public List<RoleRepresentation> list() throws NotAuthorizedException {
        try {
            List<RoleModel> roles = roleProvider.getAll();
            List<RoleRepresentation> result = new ArrayList<>();
            for (RoleModel role : roles) {
                result.add(SecurityModelToRepresentation.toRepresentation(role));
            }
            return result;
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public SearchResultsRepresentation<RoleRepresentation> search(SearchCriteriaRepresentation criteria)
            throws InvalidSearchCriteriaException, NotAuthorizedException {
        try {
            // Validate criteria
            SearchCriteriaUtil.validateSearchCriteria(criteria);
            SearchCriteriaModel criteriaModel = SearchCriteriaUtil.getSearchCriteriaModel(criteria);

            // extract filterText
            String filterText = criteria.getFilterText();

            // search
            SearchResultsModel<RoleModel> results = null;
            if (filterText == null || filterText.trim().isEmpty()) {
                results = roleProvider.search(criteriaModel);
            } else {
                results = roleProvider.search(criteriaModel, filterText);
            }

            SearchResultsRepresentation<RoleRepresentation> rep = new SearchResultsRepresentation<>();
            List<RoleRepresentation> items = new ArrayList<>();
            for (RoleModel model : results.getModels()) {
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
