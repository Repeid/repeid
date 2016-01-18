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
package org.repeid.manager.api.rest.impl.util;

import org.repeid.manager.api.rest.contract.exceptions.NotAuthorizedException;
import org.repeid.manager.api.rest.contract.exceptions.PersonaJuridicaAlreadyExistsException;
import org.repeid.manager.api.rest.contract.exceptions.PersonaJuridicaLockedException;
import org.repeid.manager.api.rest.contract.exceptions.PersonaJuridicaNotFoundException;
import org.repeid.manager.api.rest.contract.exceptions.PersonaNaturalAlreadyExistsException;
import org.repeid.manager.api.rest.contract.exceptions.PersonaNaturalLockedException;
import org.repeid.manager.api.rest.contract.exceptions.PersonaNaturalNotFoundException;
import org.repeid.manager.api.rest.contract.exceptions.RoleAlreadyExistsException;
import org.repeid.manager.api.rest.contract.exceptions.RoleNotFoundException;
import org.repeid.manager.api.rest.contract.exceptions.TipoDocumentoAlreadyExistsException;
import org.repeid.manager.api.rest.contract.exceptions.TipoDocumentoLockedException;
import org.repeid.manager.api.rest.contract.exceptions.TipoDocumentoNotFoundException;
import org.repeid.manager.api.rest.contract.exceptions.UserNotFoundException;
import org.repeid.manager.api.rest.impl.i18n.Messages;

/**
 * Simple factory for creating REST exceptions.
 *
 * @author eric.wittmann@redhat.com
 */
public final class ExceptionFactory {

    /**
     * Creates an exception from an tipoDocumento id.
     * 
     * @param tipoDocumentoId
     *            the tipoDocumento id
     * @return the exception
     */
    public static final TipoDocumentoAlreadyExistsException tipoDocumentoAlreadyExistsException(
            String tipoDocumentoId) {
        return new TipoDocumentoAlreadyExistsException(
                Messages.i18n.format("TipoDocumentoAlreadyExists", tipoDocumentoId)); //$NON-NLS-1$
    }

    /**
     * Creates an exception from a username.
     * 
     * @param username
     *            the username
     * @return the exception
     */
    public static final TipoDocumentoNotFoundException tipoDocumentoNotFoundException(
            String tipoDocumentoId) {
        return new TipoDocumentoNotFoundException(
                Messages.i18n.format("TipoDocumentoNotFound", tipoDocumentoId)); //$NON-NLS-1$
    }

    /**
     * Creates an exception from a username.
     * 
     * @param username
     *            the username
     * @return the exception
     */
    public static final TipoDocumentoLockedException tipoDocumentoLockedException(String tipoDocumentoId) {
        return new TipoDocumentoLockedException(Messages.i18n.format("TipoDocumentoLocked", tipoDocumentoId)); //$NON-NLS-1$
    }

    /**
     * Creates an exception from an personaNatural id.
     * 
     * @param personaNaturalId
     *            the personaNatural id
     * @return the exception
     */
    public static final PersonaNaturalAlreadyExistsException personaNaturalAlreadyExistsException(
            String personaNaturalId) {
        return new PersonaNaturalAlreadyExistsException(
                Messages.i18n.format("PersonaNaturalAlreadyExists", personaNaturalId)); //$NON-NLS-1$
    }

    /**
     * Creates an exception from a username.
     * 
     * @param username
     *            the username
     * @return the exception
     */
    public static final PersonaNaturalNotFoundException personaNaturalNotFoundException(
            String personaNaturalId) {
        return new PersonaNaturalNotFoundException(
                Messages.i18n.format("PersonaNaturalNotFound", personaNaturalId)); //$NON-NLS-1$
    }

    /**
     * Creates an exception from a username.
     * 
     * @param username
     *            the username
     * @return the exception
     */
    public static final PersonaNaturalLockedException personaNaturalLockedException(String personaNaturalId) {
        return new PersonaNaturalLockedException(
                Messages.i18n.format("PersonaNaturalLocked", personaNaturalId)); //$NON-NLS-1$
    }

    /**
     * Creates an exception from an personaJuridica id.
     * 
     * @param personaJuridicaId
     *            the personaJuridica id
     * @return the exception
     */
    public static final PersonaJuridicaAlreadyExistsException personaJuridicaAlreadyExistsException(
            String personaJuridicaId) {
        return new PersonaJuridicaAlreadyExistsException(
                Messages.i18n.format("PersonaJuridicaAlreadyExists", personaJuridicaId)); //$NON-NLS-1$
    }

    /**
     * Creates an exception from a username.
     * 
     * @param username
     *            the username
     * @return the exception
     */
    public static final PersonaJuridicaNotFoundException personaJuridicaNotFoundException(
            String personaJuridicaId) {
        return new PersonaJuridicaNotFoundException(
                Messages.i18n.format("PersonaJuridicaNotFound", personaJuridicaId)); //$NON-NLS-1$
    }

    /**
     * Creates an exception from a username.
     * 
     * @param username
     *            the username
     * @return the exception
     */
    public static final PersonaJuridicaLockedException personaJuridicaLockedException(
            String personaJuridicaId) {
        return new PersonaJuridicaLockedException(
                Messages.i18n.format("PersonaJuridicaLocked", personaJuridicaId)); //$NON-NLS-1$
    }

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
