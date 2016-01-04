package org.repeid.manager.api.rest.managers;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.repeid.manager.api.rest.impl.util.ExceptionFactory;
import org.repeid.models.TipoDocumentoModel;
import org.repeid.models.enums.TipoPersona;
import org.repeid.models.security.RoleModel;
import org.repeid.models.security.UserModel;
import org.repeid.representations.idm.TipoDocumentoRepresentation;
import org.repeid.representations.idm.security.RoleRepresentation;
import org.repeid.representations.idm.security.UserRepresentation;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SecurityManager {

    public void update(UserModel model, UserRepresentation rep) {
        model.setFullName(rep.getFullName());
        model.setEmail(rep.getEmail());
        model.commit();
    }

    public void update(RoleModel model, RoleRepresentation rep) {
        model.setDescription(rep.getDescription());
        model.commit();             
    }

}