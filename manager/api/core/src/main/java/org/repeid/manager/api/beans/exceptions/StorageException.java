/*******************************************************************************
 * Repeid, Home of Professional Open Source
 * <p>
 * Copyright 2015 Sistcoop, Inc. and/or its affiliates.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.repeid.manager.api.beans.exceptions;

/**
 * Thrown if a storage problem occurs while storing a bean.
 *
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public class StorageException extends Exception {

    private static final long serialVersionUID = -2331516263436545223L;

    /**
     * Constructor.
     */
    public StorageException() {
    }

    /**
     * Constructor.
     *
     * @param message
     *            the exception message
     */
    public StorageException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param cause
     *            the exception cause the exception cause
     */
    public StorageException(Throwable cause) {
        super(cause);
    }

}
