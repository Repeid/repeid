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
package org.repeid.manager.api.rest.impl.admin;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.repeid.manager.api.beans.representations.TipoDocumentoRepresentation;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;
import org.repeid.manager.api.model.exceptions.ModelReadOnlyException;
import org.repeid.manager.api.model.provider.KeycloakSession;
import org.repeid.manager.api.model.utils.ModelToRepresentation;
import org.repeid.manager.api.rest.ErrorResponse;
import org.repeid.manager.api.rest.admin.TipoDocumentoResource;
import org.repeid.manager.api.rest.managers.TipoDocumentoManager;

public class TipoDocumentoResourceImpl implements TipoDocumentoResource {

	private String tipoDocumentoId;

	@Context
	private KeycloakSession session;

	public TipoDocumentoResourceImpl(String tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}

	private TipoDocumentoModel getTipoDocumentoModel() {
		return session.tipoDocumentos().findById(tipoDocumentoId);
	}

	@Override
	public TipoDocumentoRepresentation toRepresentation() {
		TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
		if (tipoDocumento == null) {
			throw new NotFoundException("TipoDocumento no encontrado");
		}
		return ModelToRepresentation.toRepresentation(tipoDocumento);
	}

	@Override
	public Response update(TipoDocumentoRepresentation rep) {
		try {
			TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
			if (tipoDocumento == null) {
				throw new NotFoundException("TipoDocumento no encontrado");
			}

			tipoDocumento.setDenominacion(rep.getDenominacion());
			tipoDocumento.setCantidadCaracteres(rep.getCantidadCaracteres());
			tipoDocumento.setTipoPersona(TipoPersona.valueOf(rep.getTipoPersona()));

			if (session.getTransaction().isActive()) {
				session.getTransaction().commit();
			}
			return Response.noContent().build();
		} catch (ModelDuplicateException e) {
			return ErrorResponse.exists("TipoDocumento ya existe");
		} catch (ModelReadOnlyException re) {
			return ErrorResponse.exists("TipoDocumento es de solo lectura!");
		}
	}

	@Override
	public Response enable() {
		try {
			TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
			if (tipoDocumento == null) {
				throw new NotFoundException("TipoDocumento no encontrado");
			}

			tipoDocumento.setEstado(true);

			if (session.getTransaction().isActive()) {
				session.getTransaction().commit();
			}
			return Response.noContent().build();
		} catch (ModelDuplicateException e) {
			return ErrorResponse.exists("TipoDocumento ya existe");
		} catch (ModelReadOnlyException re) {
			return ErrorResponse.exists("TipoDocumento es de solo lectura!");
		}
	}

	@Override
	public Response disable() {
		try {
			TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
			if (tipoDocumento == null) {
				throw new NotFoundException("TipoDocumento no encontrado");
			}

			tipoDocumento.setEstado(false);

			if (session.getTransaction().isActive()) {
				session.getTransaction().commit();
			}
			return Response.noContent().build();
		} catch (ModelDuplicateException e) {
			return ErrorResponse.exists("TipoDocumento ya existe");
		} catch (ModelReadOnlyException re) {
			return ErrorResponse.exists("TipoDocumento es de solo lectura!");
		}
	}

	@Override
	public Response remove() {
		TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
		if (tipoDocumento == null) {
			throw new NotFoundException("TipoDocumento no encontrado");
		}

		boolean removed = new TipoDocumentoManager(session).removeTipoDocumento(tipoDocumento);
		if (removed) {
			return Response.noContent().build();
		} else {
			return ErrorResponse.error("TipoDocumento no pudo ser eliminado", Response.Status.BAD_REQUEST);
		}
	}

}
