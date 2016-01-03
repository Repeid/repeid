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

package io.apiman.manager.api.rest.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.apiman.manager.api.beans.idm.UpdateUserBean;
import io.apiman.manager.api.beans.idm.UserBean;
import io.apiman.manager.api.beans.search.SearchCriteriaBean;
import io.apiman.manager.api.beans.search.SearchResultsBean;
import io.apiman.manager.api.core.IStorage;
import io.apiman.manager.api.core.IStorageQuery;
import io.apiman.manager.api.core.exceptions.StorageException;
import io.apiman.manager.api.rest.contract.IUserResource;
import io.apiman.manager.api.rest.contract.exceptions.InvalidSearchCriteriaException;
import io.apiman.manager.api.rest.contract.exceptions.NotAuthorizedException;
import io.apiman.manager.api.rest.contract.exceptions.SystemErrorException;
import io.apiman.manager.api.rest.contract.exceptions.UserNotFoundException;
import io.apiman.manager.api.rest.impl.util.ExceptionFactory;
import io.apiman.manager.api.security.ISecurityContext;

/**
 * Implementation of the User API.
 * 
 * @author eric.wittmann@redhat.com
 */
@ApplicationScoped
public class UserResourceImpl implements IUserResource {

    @Inject
    private IStorage storage;
    @Inject
    ISecurityContext securityContext;
    @Inject
    IStorageQuery query;

    /**
     * Constructor.
     */
    public UserResourceImpl() {
    }

    /**
     * @see io.apiman.manager.api.rest.contract.IUserResource#get(java.lang.String)
     */
    @Override
    public UserBean get(String userId) throws UserNotFoundException {
        try {
            storage.beginTx();
            UserBean user = storage.getUser(userId);
            if (user == null) {
                throw ExceptionFactory.userNotFoundException(userId);
            }
            return user;
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        } finally {
            storage.rollbackTx();
        }
    }

    /**
     * @see io.apiman.manager.api.rest.contract.IUserResource#update(java.lang.String,
     *      io.apiman.manager.api.beans.idm.UpdateUserBean)
     */
    @Override
    public void update(String userId, UpdateUserBean user)
            throws UserNotFoundException, NotAuthorizedException {
        if (!securityContext.isAdmin() && !securityContext.getCurrentUser().equals(userId))
            throw ExceptionFactory.notAuthorizedException();
        try {
            storage.beginTx();
            UserBean updatedUser = storage.getUser(userId);
            if (updatedUser == null) {
                throw ExceptionFactory.userNotFoundException(userId);
            }
            if (user.getEmail() != null) {
                updatedUser.setEmail(user.getEmail());
            }
            if (user.getFullName() != null) {
                updatedUser.setFullName(user.getFullName());
            }
            storage.updateUser(updatedUser);
            storage.commitTx();
        } catch (StorageException e) {
            storage.rollbackTx();
            throw new SystemErrorException(e);
        }
    }

    /**
     * @see io.apiman.manager.api.rest.contract.IUserResource#search(io.apiman.manager.api.beans.search.SearchCriteriaBean)
     */
    @Override
    public SearchResultsBean<UserBean> search(SearchCriteriaBean criteria)
            throws InvalidSearchCriteriaException {
        try {
            return query.findUsers(criteria);
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    /**
     * @return the securityContext
     */
    public ISecurityContext getSecurityContext() {
        return securityContext;
    }

    /**
     * @param securityContext
     *            the securityContext to set
     */
    public void setSecurityContext(ISecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    /**
     * @return the query
     */
    public IStorageQuery getQuery() {
        return query;
    }

    /**
     * @param query
     *            the query to set
     */
    public void setQuery(IStorageQuery query) {
        this.query = query;
    }

    /**
     * @return the storage
     */
    public IStorage getStorage() {
        return storage;
    }

    /**
     * @param storage
     *            the storage to set
     */
    public void setStorage(IStorage storage) {
        this.storage = storage;
    }
}
