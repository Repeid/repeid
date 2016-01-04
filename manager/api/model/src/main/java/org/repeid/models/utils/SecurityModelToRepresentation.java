package org.repeid.models.utils;

import org.repeid.models.AccionistaModel;
import org.repeid.models.PersonaJuridicaModel;
import org.repeid.models.PersonaNaturalModel;
import org.repeid.models.StoredFileModel;
import org.repeid.models.TipoDocumentoModel;
import org.repeid.models.security.RoleModel;
import org.repeid.models.security.UserModel;
import org.repeid.representations.idm.AccionistaRepresentation;
import org.repeid.representations.idm.PersonaJuridicaRepresentation;
import org.repeid.representations.idm.PersonaNaturalRepresentation;
import org.repeid.representations.idm.StoredFileRepresentation;
import org.repeid.representations.idm.TipoDocumentoRepresentation;
import org.repeid.representations.idm.security.RoleRepresentation;
import org.repeid.representations.idm.security.UserRepresentation;

public class SecurityModelToRepresentation {

    public static UserRepresentation toRepresentation(UserModel model) {
        if (model == null)
            return null;
        UserRepresentation rep = new UserRepresentation();
        rep.setUsername(model.getUsername());
        rep.setFullName(model.getFullName());
        rep.setEmail(model.getEmail());
        rep.setJoinedOn(model.getJoinedOn());
        rep.setAdmin(model.isAdmin());
        rep.setPermissions(permissions);
        return rep;
    }

    public static RoleRepresentation toRepresentation(RoleModel role) {
        // TODO Auto-generated method stub
        return null;
    }

}
