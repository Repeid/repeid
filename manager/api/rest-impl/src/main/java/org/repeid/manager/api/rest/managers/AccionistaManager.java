package org.repeid.manager.api.rest.managers;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.repeid.manager.api.core.exceptions.StorageException;
import org.repeid.manager.api.core.representations.idm.AccionistaRepresentation;
import org.repeid.manager.api.model.AccionistaModel;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AccionistaManager {

    public void update(AccionistaModel model, AccionistaRepresentation rep) throws StorageException {
        model.setPorcentajeParticipacion(rep.getPorcentajeParticipacion());
        model.commit();
    }

}