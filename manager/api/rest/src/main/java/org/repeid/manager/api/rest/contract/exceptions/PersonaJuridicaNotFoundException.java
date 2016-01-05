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
 * Thrown when a request is sent for a role that does not exist.
 *
 * @author eric.wittmann@redhat.com
 */
public class PersonaJuridicaNotFoundException extends AbstractNotFoundException {

	private static final long serialVersionUID = -5416700206708610404L;

	/**
	 * Constructor.
	 */
	public PersonaJuridicaNotFoundException() {
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            the exception message
	 */
	public PersonaJuridicaNotFoundException(String message) {
		super(message);
	}

	/**
	 * @see org.repeid.manager.api.rest.contract.exceptions.AbstractRestException#getErrorCode()
	 */
	@Override
	public int getErrorCode() {
		return ErrorCodes.PERSONA_JURIDICA_NOT_FOUND;
	}

	/**
	 * @see org.repeid.manager.api.rest.contract.exceptions.AbstractRestException#getMoreInfoUrl()
	 */
	@Override
	public String getMoreInfoUrl() {
		return ErrorCodes.PERSONA_JURIDICA_NOT_FOUND_INFO;
	}

}
