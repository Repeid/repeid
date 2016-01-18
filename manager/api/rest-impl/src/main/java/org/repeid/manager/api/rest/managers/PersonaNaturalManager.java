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
import org.repeid.manager.api.beans.representations.PersonaNaturalRepresentation;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.StoreConfigurationModel;
import org.repeid.manager.api.model.StoredFileModel;
import org.repeid.manager.api.model.StoredFileProvider;
import org.repeid.manager.api.model.enums.EstadoCivil;
import org.repeid.manager.api.model.enums.Sexo;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PersonaNaturalManager {

	public void update(PersonaNaturalModel model, PersonaNaturalRepresentation representation) throws StorageException {
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

		model.commit();
	}

	public StoredFileModel setFoto(PersonaNaturalModel personaNatural, StoreConfigurationModel config, byte[] bytes,
			StoredFileProvider storedFileProvider) throws StorageException {
		StoredFileModel storedFileModel = storedFileProvider.create(bytes, config);
		personaNatural.setFoto(storedFileModel);
		personaNatural.commit();
		return storedFileModel;
	}

	public StoredFileModel setFirma(PersonaNaturalModel personaNatural, StoreConfigurationModel config, byte[] bytes,
 StoredFileProvider storedFileProvider) throws StorageException {
		StoredFileModel storedFileModel = storedFileProvider.create(bytes, config);
		personaNatural.setFirma(storedFileModel);
		personaNatural.commit();
		return storedFileModel;
	}

}