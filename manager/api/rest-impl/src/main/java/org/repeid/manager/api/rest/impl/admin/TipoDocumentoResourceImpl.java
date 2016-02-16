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

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.PathParam;

import org.repeid.manager.api.beans.representations.TipoDocumentoRepresentation;
import org.repeid.manager.api.beans.representations.security.PermissionType;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.exceptions.ModelException;
import org.repeid.manager.api.model.exceptions.ModelReadOnlyException;
import org.repeid.manager.api.model.system.RepeidSession;
import org.repeid.manager.api.model.utils.ModelToRepresentation;
import org.repeid.manager.api.rest.admin.TipoDocumentoResource;
import org.repeid.manager.api.rest.admin.TiposDocumentoResource;
import org.repeid.manager.api.rest.contract.exceptions.SystemErrorException;
import org.repeid.manager.api.rest.managers.TipoDocumentoManager;
import org.repeid.manager.api.rest.util.ExceptionFactory;
import org.repeid.manager.api.security.ISecurityContext;

@RequestScoped
public class TipoDocumentoResourceImpl implements TipoDocumentoResource {

	@PathParam(TiposDocumentoResource.TIPO_DOCUMENTO_ID)
	private String tipoDocumentoId;

	@Inject
	private RepeidSession session;

	@Inject
	private ISecurityContext auth;

	private TipoDocumentoModel getTipoDocumentoModel() {
		return session.tipoDocumentos().findById(tipoDocumentoId);
	}

	@Override
	public TipoDocumentoRepresentation toRepresentation() {
		if (!auth.hasPermission(PermissionType.documentoView))
			throw ExceptionFactory.notAuthorizedException();

		TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
		if (tipoDocumento == null) {
			throw ExceptionFactory.tipoDocumentoNotFoundException(tipoDocumentoId);
		}
		return ModelToRepresentation.toRepresentation(tipoDocumento);
	}

	@Override
	public void update(TipoDocumentoRepresentation rep) {
		if (!auth.hasPermission(PermissionType.documentoEdit))
			throw ExceptionFactory.notAuthorizedException();

		TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
		if (tipoDocumento == null) {
			throw ExceptionFactory.tipoDocumentoNotFoundException(tipoDocumentoId);
		}

		boolean result = new TipoDocumentoManager(session).update(tipoDocumento, rep);
		if (!result) {
			throw ExceptionFactory.tipoDocumentoLockedException(tipoDocumentoId);
		}
	}

	@Override
	public void enable() {
		if (!auth.hasPermission(PermissionType.documentoEdit))
			throw ExceptionFactory.notAuthorizedException();

		TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
		if (tipoDocumento == null) {
			throw ExceptionFactory.tipoDocumentoNotFoundException(tipoDocumentoId);
		}

		boolean result = new TipoDocumentoManager(session).enable(tipoDocumento);
		if (!result) {
			throw ExceptionFactory.tipoDocumentoLockedException(tipoDocumentoId);
		}
	}

	@Override
	public void disable() {
		if (!auth.hasPermission(PermissionType.documentoEdit))
			throw ExceptionFactory.notAuthorizedException();

		TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
		if (tipoDocumento == null) {
			throw ExceptionFactory.tipoDocumentoNotFoundException(tipoDocumentoId);
		}

		boolean result = new TipoDocumentoManager(session).disable(tipoDocumento);
		if (!result) {
			throw ExceptionFactory.tipoDocumentoLockedException(tipoDocumentoId);
		}
	}

	@Override
	public void remove() {
		if (!auth.hasPermission(PermissionType.documentoAdmin))
			throw ExceptionFactory.notAuthorizedException();

		TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
		if (tipoDocumento == null) {
			throw ExceptionFactory.tipoDocumentoNotFoundException(tipoDocumentoId);
		}

		try {
			session.tipoDocumentos().remove(tipoDocumento);

			if (session.getTransaction().isActive()) {
				session.getTransaction().commit();
			}
		} catch (ModelReadOnlyException e) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().setRollbackOnly();
			}
			throw new SystemErrorException(e);
		} catch (ModelException e) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().setRollbackOnly();
			}
			throw new SystemErrorException(e);
		}
	}

}
