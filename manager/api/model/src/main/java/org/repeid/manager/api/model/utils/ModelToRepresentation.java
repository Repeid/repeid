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
package org.repeid.manager.api.model.utils;

import org.repeid.manager.api.beans.representations.PersonaJuridicaRepresentation;
import org.repeid.manager.api.beans.representations.PersonaNaturalRepresentation;
import org.repeid.manager.api.beans.representations.TipoDocumentoRepresentation;
import org.repeid.manager.api.model.PersonaJuridicaModel;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.TipoDocumentoModel;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public class ModelToRepresentation {

	public static TipoDocumentoRepresentation toRepresentation(TipoDocumentoModel model) {
		if (model == null)
			return null;
		TipoDocumentoRepresentation rep = new TipoDocumentoRepresentation();

		rep.setAbreviatura(model.getAbreviatura());
		rep.setDenominacion(model.getDenominacion());
		rep.setCantidadCaracteres(model.getCantidadCaracteres());
		rep.setTipoPersona(model.getTipoPersona().toString());
		rep.setEstado(model.getEstado());

		return rep;
	}

	public static PersonaNaturalRepresentation toRepresentation(PersonaNaturalModel model) {
		if (model == null)
			return null;

		PersonaNaturalRepresentation rep = new PersonaNaturalRepresentation();

		rep.setId(model.getId());

		rep.setCodigoPais(model.getCodigoPais());
		rep.setTipoDocumento(model.getTipoDocumento().getAbreviatura());
		rep.setNumeroDocumento(model.getNumeroDocumento());

		// datos personales
		rep.setApellidoPaterno(model.getApellidoPaterno());
		rep.setApellidoMaterno(model.getApellidoMaterno());
		rep.setNombres(model.getNombres());
		rep.setFechaNacimiento(model.getFechaNacimiento());
		rep.setSexo(model.getSexo() != null ? model.getSexo().toString() : null);

		rep.setEstadoCivil(model.getEstadoCivil() != null ? model.getEstadoCivil().toString() : null);
		rep.setOcupacion(model.getOcupacion());

		// direccion de residencia
		rep.setUbigeo(model.getUbigeo());
		rep.setDireccion(model.getDireccion());
		rep.setReferencia(model.getReferencia());
		rep.setTelefono(model.getTelefono());
		rep.setCelular(model.getCelular());
		rep.setEmail(model.getEmail());

		return rep;
	}

	public static PersonaJuridicaRepresentation toRepresentation(PersonaJuridicaModel model) {
		if (model == null)
			return null;

		PersonaJuridicaRepresentation rep = new PersonaJuridicaRepresentation();

		rep.setId(model.getId());

		rep.setCodigoPais(model.getCodigoPais());
		rep.setTipoDocumento(model.getTipoDocumento().getAbreviatura());
		rep.setNumeroDocumento(model.getNumeroDocumento());

		// datos personales
		rep.setRazonSocial(model.getRazonSocial());
		rep.setActividadPrincipal(model.getActividadPrincipal());
		rep.setFechaConstitucion(model.getFechaConstitucion());
		rep.setFinLucro(model.isFinLucro());
		rep.setNombreComercial(model.getNombreComercial());
		rep.setTipoEmpresa(model.getTipoEmpresa() != null ? model.getTipoEmpresa().toString() : null);

		// representante legal
		PersonaNaturalRepresentation representanteRep = new PersonaNaturalRepresentation();
		representanteRep.setId(model.getRepresentanteLegal().getId());
		representanteRep.setCodigoPais(model.getRepresentanteLegal().getCodigoPais());
		representanteRep.setTipoDocumento(model.getRepresentanteLegal().getTipoDocumento().getAbreviatura());
		representanteRep.setNumeroDocumento(model.getRepresentanteLegal().getNumeroDocumento());
		representanteRep.setApellidoPaterno(model.getRepresentanteLegal().getApellidoPaterno());
		representanteRep.setApellidoMaterno(model.getRepresentanteLegal().getApellidoMaterno());
		representanteRep.setNombres(model.getRepresentanteLegal().getNombres());
		representanteRep.setFechaNacimiento(model.getRepresentanteLegal().getFechaNacimiento());
		representanteRep.setSexo(model.getRepresentanteLegal().getSexo().toString());
		rep.setRepresentanteLegal(representanteRep);

		// direccion de residencia
		rep.setUbigeo(model.getUbigeo());
		rep.setDireccion(model.getDireccion());
		rep.setReferencia(model.getReferencia());
		rep.setTelefono(model.getTelefono());
		rep.setCelular(model.getCelular());
		rep.setEmail(model.getEmail());

		return rep;
	}

}
