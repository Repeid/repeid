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
package org.repeid.manager.test.api.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.TipoDocumentoProvider;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.api.model.system.RepeidSession;
import org.repeid.manager.test.api.AbstractTest;

public class TipoDocumentoProviderTest extends AbstractTest {

	@Inject
	private RepeidSession session;

	/*@Test
	public void findByAbreviatura() throws StorageException {
		TipoDocumentoModel model1 = session.tipoDocumentos().create("DNI", "Documento nacional de identidad", 8,
				TipoPersona.NATURAL);

		String abreviatura = model1.getAbreviatura();
		TipoDocumentoModel model2 = session.tipoDocumentos().findByAbreviatura(abreviatura);

		assertThat("model1 debe ser igual a model2", model1, is(equalTo(model2)));
	}*/

	@Test
	public void create() throws StorageException {
		TipoDocumentoModel model = session.tipoDocumentos().create("DNI", "Documento nacional de identidad", 8,
				TipoPersona.NATURAL);
		
		if (session.getTransaction().isActive()) {
			session.getTransaction().commit();
		}

		assertThat("model no debe ser null", model, is(notNullValue()));
		assertThat("estado debe ser true", model.getEstado(), is(true));
	}

}
