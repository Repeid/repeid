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

import org.repeid.manager.api.beans.representations.PersonaJuridicaRepresentation;
import org.repeid.manager.api.beans.representations.PersonaNaturalRepresentation;
import org.repeid.manager.api.model.PersonaJuridicaModel;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.enums.TipoEmpresa;
import org.repeid.manager.api.model.exceptions.ModelException;
import org.repeid.manager.api.model.exceptions.ModelReadOnlyException;
import org.repeid.manager.api.model.system.RepeidSession;
import org.repeid.manager.api.rest.contract.exceptions.SystemErrorException;
import org.repeid.manager.api.rest.util.ExceptionFactory;

public class PersonaJuridicaManager {

	RepeidSession session;

	public PersonaJuridicaManager(RepeidSession session) {
		this.session = session;
	}

	public boolean update(PersonaJuridicaModel model, PersonaJuridicaRepresentation rep) {
		model.setCodigoPais(rep.getCodigoPais());
		model.setRazonSocial(rep.getRazonSocial());
		model.setFechaConstitucion(rep.getFechaConstitucion());
		model.setActividadPrincipal(rep.getActividadPrincipal());
		model.setNombreComercial(rep.getNombreComercial());
		model.setFinLucro(rep.isFinLucro());
		model.setTipoEmpresa(TipoEmpresa.valueOf(rep.getTipoEmpresa()));

		model.setUbigeo(rep.getUbigeo());
		model.setDireccion(rep.getDireccion());
		model.setReferencia(rep.getReferencia());
		model.setTelefono(rep.getTelefono());
		model.setCelular(rep.getCelular());
		model.setEmail(rep.getEmail());

		PersonaNaturalRepresentation representanteRep = rep.getRepresentanteLegal();
		if (representanteRep != null) {
			PersonaNaturalRepresentation representanteRepresentation = rep.getRepresentanteLegal();
			TipoDocumentoModel tipoDocumentoRepresentanteModel = session.tipoDocumentos()
					.findByAbreviatura(representanteRepresentation.getTipoDocumento());
			PersonaNaturalModel representanteModel = session.personasNaturales().findByTipoNumeroDocumento(
					tipoDocumentoRepresentanteModel, representanteRepresentation.getNumeroDocumento());

			model.setRepresentanteLegal(representanteModel);
		}

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

}