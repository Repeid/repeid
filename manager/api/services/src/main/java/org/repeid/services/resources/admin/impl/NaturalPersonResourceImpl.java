package org.repeid.services.resources.admin.impl;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.repeid.manager.api.beans.representations.PersonaNaturalRepresentation;
import org.repeid.manager.api.beans.representations.StoredFileRepresentation;
import org.repeid.services.resources.admin.NaturalPersonResource;

public class NaturalPersonResourceImpl implements NaturalPersonResource {

    @Override
    public PersonaNaturalRepresentation toRepresentation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(PersonaNaturalRepresentation rep) {
        // TODO Auto-generated method stub

    }

    @Override
    public Response getFoto() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StoredFileRepresentation setFoto(MultipartFormDataInput input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response getFirma() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StoredFileRepresentation setFirma(MultipartFormDataInput input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void remove() {
        // TODO Auto-generated method stub

    }

}
