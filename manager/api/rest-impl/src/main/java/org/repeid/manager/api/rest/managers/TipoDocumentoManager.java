package org.repeid.manager.api.rest.managers;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.repeid.models.TipoDocumentoModel;
import org.repeid.models.enums.TipoPersona;
import org.repeid.representations.idm.TipoDocumentoRepresentation;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TipoDocumentoManager {

    public void update(TipoDocumentoModel model, TipoDocumentoRepresentation rep) {
        model.setDenominacion(rep.getDenominacion());
        model.setCantidadCaracteres(rep.getCantidadCaracteres());
        model.setTipoPersona(TipoPersona.valueOf(rep.getTipoPersona()));
        model.commit();
    }

    public void enable(TipoDocumentoModel model) {
        model.setEstado(true);
        model.commit();
    }

    public void disable(TipoDocumentoModel model) {
        model.setEstado(false);
        model.commit();
    }

}