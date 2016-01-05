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

package org.repeid.manager.api.rest.contract.exceptions;

/**
 * Thrown when trying to create a Role that already exists.
 *
 * @author eric.wittmann@redhat.com
 */
public class TipoDocumentoLockedException extends AbstractLockedException {

	private static final long serialVersionUID = -688580326437962778L;

	/**
	 * Constructor.
	 */
	public TipoDocumentoLockedException() {
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            the exception message
	 */
	public TipoDocumentoLockedException(String message) {
		super(message);
	}

	/**
	 * @see org.repeid.manager.api.rest.contract.exceptions.AbstractRestException#getErrorCode()
	 */
	@Override
	public int getErrorCode() {
		return ErrorCodes.TIPO_DOCUMENTO_LOCKED;
	}

	/**
	 * @see org.repeid.manager.api.rest.contract.exceptions.AbstractRestException#getMoreInfoUrl()
	 */
	@Override
	public String getMoreInfoUrl() {
		return ErrorCodes.TIPO_DOCUMENTO_LOCKED_INFO;
	}

}
