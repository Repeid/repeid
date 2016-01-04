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

import org.repeid.manager.api.rest.contract.IPermissionsResource;
import org.repeid.manager.api.rest.contract.exceptions.NotAuthorizedException;
import org.repeid.manager.api.rest.contract.exceptions.UserNotFoundException;
import org.repeid.manager.api.rest.impl.util.ExceptionFactory;
import org.repeid.representations.idm.security.UserPermissionsRepresentation;

import io.apiman.manager.api.security.ISecurityContext;

/**
 * Implementation of the Permissions API.
 * 
 * @author eric.wittmann@redhat.com
 */
@Stateless
public class PermissionsResourceImpl implements IPermissionsResource {

    @Inject
    ISecurityContext securityContext;

    /**
     * Constructor.
     */
    public PermissionsResourceImpl() {
    }

    /**
     * @see org.repeid.manager.api.rest.contract.IPermissionsResource#getPermissionsForUser(java.lang.String)
     */
    @Override
    public UserPermissionsRepresentation getPermissionsForUser(String userId)
            throws UserNotFoundException, NotAuthorizedException {
        if (!securityContext.isAdmin())
            throw ExceptionFactory.notAuthorizedException();

        return null;
    }

    /**
     * @see org.repeid.manager.api.rest.contract.IPermissionsResource#getPermissionsForCurrentUser()
     */
    @Override
    public UserPermissionsRepresentation getPermissionsForCurrentUser() throws UserNotFoundException {
        return null;
    }

}
