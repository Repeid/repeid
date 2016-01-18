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

package org.repeid.manager.api.rest.contract.exceptions;

/**
 * Base class for "locked entity" exceptions.
 *
 * @author eric.wittmann@redhat.com
 */
public abstract class AbstractLockedException extends AbstractUserException {

    private static final long serialVersionUID = 1345772129352225376L;

    /**
     * Constructor.
     */
    public AbstractLockedException() {
    }

    /**
     * Constructor.
     * 
     * @param message
     *            the message
     */
    public AbstractLockedException(String message) {
        super(message);
    }

    /**
     * @see org.repeid.manager.api.rest.contract.exceptions.AbstractRestException#getHttpCode()
     */
    @Override
    public final int getHttpCode() {
        return ErrorCodes.HTTP_STATUS_CODE_LOCKED;
    }

}
