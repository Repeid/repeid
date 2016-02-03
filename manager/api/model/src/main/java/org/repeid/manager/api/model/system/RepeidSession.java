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

package org.repeid.manager.api.model.system;

import org.repeid.manager.api.model.PersonaJuridicaProvider;
import org.repeid.manager.api.model.PersonaNaturalProvider;
import org.repeid.manager.api.model.TipoDocumentoProvider;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

public interface RepeidSession {

	RepeidTransactionManager getTransaction();

	/**
	 * Returns a managed provider instance. Will start a provider transaction.
	 * This transaction is managed by the RepeidSession transaction.
	 *
	 * @return
	 * @throws IllegalStateException
	 *             if transaction is not active
	 */
	TipoDocumentoProvider tipoDocumentos();

	/**
	 * Returns a managed provider instance. Will start a provider transaction.
	 * This transaction is managed by the RepeidSession transaction.
	 *
	 * @return
	 * @throws IllegalStateException
	 *             if transaction is not active
	 */
	PersonaNaturalProvider personasNaturales();

	/**
	 * Returns a managed provider instance. Will start a provider transaction.
	 * This transaction is managed by the RepeidSession transaction.
	 *
	 * @return
	 * @throws IllegalStateException
	 *             if transaction is not active
	 */
	PersonaJuridicaProvider personasJuridicas();

	void close();

}
