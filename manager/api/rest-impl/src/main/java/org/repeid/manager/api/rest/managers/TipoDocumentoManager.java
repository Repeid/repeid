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