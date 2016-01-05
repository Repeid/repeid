package org.repeid.manager.api.rest.bussiness.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.repeid.manager.api.rest.bussiness.TipoDocumentoResource;
import org.repeid.manager.api.rest.bussiness.TiposDocumentoResource;
import org.repeid.manager.api.rest.contract.exceptions.SystemErrorException;
import org.repeid.manager.api.rest.impl.util.ExceptionFactory;
import org.repeid.manager.api.rest.managers.TipoDocumentoManager;
import org.repeid.models.TipoDocumentoModel;
import org.repeid.models.TipoDocumentoProvider;
import org.repeid.models.utils.ModelToRepresentation;
import org.repeid.representations.idm.TipoDocumentoRepresentation;
import org.repeid.representations.idm.security.PermissionType;

import io.apiman.manager.api.core.exceptions.StorageException;
import io.apiman.manager.api.security.ISecurityContext;

@Stateless
public class TipoDocumentoResourceImpl implements TipoDocumentoResource {

	@PathParam(TiposDocumentoResource.TIPO_DOCUMENTO_ID)
	private String tipoDocumentoId;

	@Inject
	private TipoDocumentoProvider tipoDocumentoProvider;

	@Inject
	private TipoDocumentoManager tipoDocumentoManager;

	@Inject
	private ISecurityContext iSecurityContext;

	private TipoDocumentoModel getTipoDocumentoModel() throws StorageException {
		return tipoDocumentoProvider.findById(tipoDocumentoId);
	}

	@Override
	public TipoDocumentoRepresentation toRepresentation() {
		if (!iSecurityContext.hasPermission(PermissionType.tipoDocumentoView))
			throw ExceptionFactory.notAuthorizedException();

		try {
			TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
			if (tipoDocumento == null) {
				throw ExceptionFactory.tipoDocumentoNotFoundException(tipoDocumentoId);
			}
			return ModelToRepresentation.toRepresentation(tipoDocumento);
		} catch (StorageException e) {
			throw new SystemErrorException(e);
		}
	}

	@Override
	public void update(TipoDocumentoRepresentation rep) {
		if (!iSecurityContext.hasPermission(PermissionType.tipoDocumentoEdit))
			throw ExceptionFactory.notAuthorizedException();

		try {
			TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
			if (tipoDocumento == null) {
				throw ExceptionFactory.tipoDocumentoNotFoundException(tipoDocumentoId);
			}
			tipoDocumentoManager.update(tipoDocumento, rep);
		} catch (StorageException e) {
			throw new SystemErrorException(e);
		}
	}

	@Override
	public void enable() {
		if (!iSecurityContext.hasPermission(PermissionType.tipoDocumentoEdit))
			throw ExceptionFactory.notAuthorizedException();

		try {
			TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
			if (tipoDocumento == null) {
				throw ExceptionFactory.tipoDocumentoNotFoundException(tipoDocumentoId);
			}
			tipoDocumentoManager.enable(tipoDocumento);
		} catch (StorageException e) {
			throw new SystemErrorException(e);
		}
	}

	@Override
	public void disable() {
		if (!iSecurityContext.hasPermission(PermissionType.tipoDocumentoEdit))
			throw ExceptionFactory.notAuthorizedException();

		try {
			TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
			if (tipoDocumento == null) {
				throw ExceptionFactory.tipoDocumentoNotFoundException(tipoDocumentoId);
			}
			tipoDocumentoManager.disable(tipoDocumento);
		} catch (StorageException e) {
			throw new SystemErrorException(e);
		}
	}

	@Override
	public Response remove() {
		if (!iSecurityContext.hasPermission(PermissionType.tipoDocumentoAdmin))
			throw ExceptionFactory.notAuthorizedException();

		try {
			TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
			if (tipoDocumento == null) {
				throw ExceptionFactory.tipoDocumentoNotFoundException(tipoDocumentoId);
			}

			boolean removed = tipoDocumentoProvider.remove(tipoDocumento);
			if (removed) {
				return Response.noContent().build();
			} else {
				throw ExceptionFactory.tipoDocumentoAlreadyExistsException(tipoDocumentoId);
			}
		} catch (StorageException e) {
			throw new SystemErrorException(e);
		}
	}

}
