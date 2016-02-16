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

import org.repeid.manager.api.beans.representations.PersonaNaturalRepresentation;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.StoreConfigurationModel;
import org.repeid.manager.api.model.StoredFileModel;
import org.repeid.manager.api.model.enums.EstadoCivil;
import org.repeid.manager.api.model.enums.Sexo;
import org.repeid.manager.api.model.exceptions.ModelException;
import org.repeid.manager.api.model.exceptions.ModelReadOnlyException;
import org.repeid.manager.api.model.system.RepeidSession;
import org.repeid.manager.api.rest.contract.exceptions.SystemErrorException;
import org.repeid.manager.api.rest.util.ExceptionFactory;

public class PersonaNaturalManager {

	private RepeidSession session;

	public PersonaNaturalManager(RepeidSession session) {
		this.session = session;
	}

	public boolean update(PersonaNaturalModel model, PersonaNaturalRepresentation representation) {
		model.setCodigoPais(representation.getCodigoPais());
		model.setApellidoPaterno(representation.getApellidoPaterno());
		model.setApellidoMaterno(representation.getApellidoMaterno());
		model.setNombres(representation.getNombres());
		model.setFechaNacimiento(representation.getFechaNacimiento());
		model.setSexo(Sexo.valueOf(representation.getSexo().toUpperCase()));
		model.setEstadoCivil(representation.getEstadoCivil() != null
				? EstadoCivil.valueOf(representation.getEstadoCivil().toUpperCase()) : null);

		model.setUbigeo(representation.getUbigeo());
		model.setDireccion(representation.getDireccion());
		model.setReferencia(representation.getReferencia());
		model.setOcupacion(representation.getOcupacion());
		model.setTelefono(representation.getTelefono());
		model.setCelular(representation.getCelular());
		model.setEmail(representation.getEmail());

		try {
			if (session.getTransaction().isActive()) {
				session.getTransaction().commit();
			}
		} catch (ModelReadOnlyException e) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().setRollbackOnly();
			}
			throw ExceptionFactory.tipoDocumentoAlreadyExistsException(model.getId());
		} catch (ModelException e) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().setRollbackOnly();
			}
			throw new SystemErrorException(e);
		}

		return true;
	}

	public StoredFileModel setFoto(PersonaNaturalModel personaNatural, StoreConfigurationModel config, byte[] bytes) {
		return null;
	}

	public StoredFileModel setFirma(PersonaNaturalModel personaNatural, StoreConfigurationModel config, byte[] bytes) {
		return null;
	}

}
