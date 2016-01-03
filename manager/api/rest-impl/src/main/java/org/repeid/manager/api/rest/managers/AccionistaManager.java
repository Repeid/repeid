package org.repeid.manager.api.rest.managers;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.repeid.models.AccionistaModel;
import org.repeid.representations.idm.AccionistaRepresentation;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AccionistaManager {

    public void update(AccionistaModel model, AccionistaRepresentation rep) {
        model.setPorcentajeParticipacion(rep.getPorcentajeParticipacion());
        model.commit();
    }

}