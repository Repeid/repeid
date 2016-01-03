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
package org.repeid.manager.api.rest.impl.util;

import org.repeid.manager.api.rest.contract.exceptions.NotAuthorizedException;
import org.repeid.manager.api.rest.contract.exceptions.RoleAlreadyExistsException;
import org.repeid.manager.api.rest.contract.exceptions.RoleNotFoundException;
import org.repeid.manager.api.rest.contract.exceptions.UserNotFoundException;
import org.repeid.manager.api.rest.impl.i18n.Messages;

/**
 * Simple factory for creating REST exceptions.
 *
 * @author eric.wittmann@redhat.com
 */
public final class ExceptionFactory {

    /**
     * Creates an exception from a username.
     * 
     * @param username
     *            the username
     * @return the exception
     */
    public static final UserNotFoundException userNotFoundException(String username) {
        return new UserNotFoundException(Messages.i18n.format("UserNotFound", username)); //$NON-NLS-1$
    }

    /**
     * Creates a not authorized exception.
     * 
     * @return the exception
     */
    public static final NotAuthorizedException notAuthorizedException() {
        return new NotAuthorizedException(Messages.i18n.format("AccessDenied")); //$NON-NLS-1$
    }

    /**
     * Creates an exception from an role id.
     * 
     * @param roleId
     *            the role id
     * @return the exception
     */
    public static final RoleAlreadyExistsException roleAlreadyExistsException(String roleId) {
        return new RoleAlreadyExistsException(Messages.i18n.format("RoleAlreadyExists", roleId)); //$NON-NLS-1$
    }

    /**
     * Creates an exception from a username.
     * 
     * @param roleId
     *            the role id
     * @return the exception
     */
    public static final RoleNotFoundException roleNotFoundException(String roleId) {
        return new RoleNotFoundException(Messages.i18n.format("RoleNotFound", roleId)); //$NON-NLS-1$
    }

}
