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

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.repeid.manager.api.rest.contract.IRoleResource;
import org.repeid.manager.api.rest.contract.exceptions.InvalidSearchCriteriaException;
import org.repeid.manager.api.rest.contract.exceptions.NotAuthorizedException;
import org.repeid.manager.api.rest.contract.exceptions.RoleAlreadyExistsException;
import org.repeid.manager.api.rest.contract.exceptions.RoleNotFoundException;
import org.repeid.manager.api.rest.impl.util.ExceptionFactory;
import org.repeid.manager.api.rest.managers.SecurityManager;
import org.repeid.models.security.RoleModel;
import org.repeid.models.security.RoleProvider;
import org.repeid.models.utils.ModelToRepresentation;
import org.repeid.models.utils.RepresentationToModel;
import org.repeid.representations.idm.search.SearchCriteriaRepresentation;
import org.repeid.representations.idm.search.SearchResultsRepresentation;
import org.repeid.representations.idm.security.RoleRepresentation;

import io.apiman.manager.api.security.ISecurityContext;

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
    private RepresentationToModel representationToModel;

    @Inject
    private ISecurityContext securityContext;

    private RoleModel getRoleModel(String roleId) {
        return roleProvider.findById(roleId);
    }

    @Override
    public RoleRepresentation create(RoleRepresentation rep)
            throws RoleAlreadyExistsException, NotAuthorizedException {
        if (!securityContext.isAdmin())
            throw ExceptionFactory.notAuthorizedException();

        RoleModel role = representationToModel.createRole(rep, roleProvider);
        return ModelToRepresentation.toRepresentation(role);
    }

    @Override
    public RoleRepresentation get(String roleId) throws RoleNotFoundException, NotAuthorizedException {
        RoleModel role = getRoleModel(roleId);
        if (role == null) {
            throw ExceptionFactory.roleNotFoundException(roleId);
        }
        return ModelToRepresentation.toRepresentation(role);
    }

    @Override
    public void update(String roleId, RoleRepresentation rep)
            throws RoleNotFoundException, NotAuthorizedException {
        if (!securityContext.isAdmin())
            throw ExceptionFactory.notAuthorizedException();

        RoleModel role = getRoleModel(roleId);
        securityManager.update(role, rep);
    }

    @Override
    public void delete(String roleId) throws RoleNotFoundException, NotAuthorizedException {
        if (!securityContext.isAdmin())
            throw ExceptionFactory.notAuthorizedException();
        RoleModel role = getRoleModel(roleId);
        roleProvider.delete(role);
    }

    /**
     * @see org.repeid.manager.api.rest.contract.IRoleResource#list()
     */
    @Override
    public List<RoleRepresentation> list() throws NotAuthorizedException {
        List<RoleModel> roles = roleProvider.getAll();
        List<RoleRepresentation> result = new ArrayList<>();
        for (RoleModel role : roles) {
            result.add(ModelToRepresentation.toRepresentation(role));
        }
        return result;
    }

    @Override
    public SearchResultsRepresentation<RoleRepresentation> search(SearchCriteriaRepresentation criteria)
            throws InvalidSearchCriteriaException, NotAuthorizedException {
        // TODO Auto-generated method stub
        return null;
    }

}
