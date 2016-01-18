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
package org.repeid.manager.api.rest.managers;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.beans.representations.TipoDocumentoRepresentation;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.enums.TipoPersona;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TipoDocumentoManager {

	public void update(TipoDocumentoModel model, TipoDocumentoRepresentation rep) throws StorageException {
		model.setDenominacion(rep.getDenominacion());
		model.setCantidadCaracteres(rep.getCantidadCaracteres());
		model.setTipoPersona(TipoPersona.valueOf(rep.getTipoPersona()));
		model.commit();
	}

	public void enable(TipoDocumentoModel model) throws StorageException {
		model.setEstado(true);
		model.commit();
	}

	public void disable(TipoDocumentoModel model) throws StorageException {
		model.setEstado(false);
		model.commit();
	}

}