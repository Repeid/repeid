package org.repeid.manager.api.rest.managers;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.repeid.models.TipoDocumentoModel;
import org.repeid.models.enums.TipoPersona;
import org.repeid.models.security.UserModel;
import org.repeid.representations.idm.TipoDocumentoRepresentation;
import org.repeid.representations.idm.security.UserRepresentation;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserManager {

    public void update(UserModel model, UserRepresentation rep) {
        model.setFullName(rep.getFullName());
        model.setEmail(rep.getEmail());
        model.commit();
    }

}