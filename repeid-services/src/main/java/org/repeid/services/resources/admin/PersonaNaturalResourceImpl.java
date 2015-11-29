package org.repeid.services.resources.admin;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.repeid.admin.client.resource.PersonaNaturalResource;
import org.repeid.models.PersonaNaturalModel;
import org.repeid.models.PersonaNaturalProvider;
import org.repeid.models.utils.ModelToRepresentation;
import org.repeid.representations.idm.PersonaNaturalRepresentation;
import org.repeid.services.ErrorResponse;
import org.repeid.services.managers.PersonaNaturalManager;

@Stateless
public class PersonaNaturalResourceImpl implements PersonaNaturalResource {

    @PathParam("idPersonaNatural")
    private String idPersonaNatural;

    @Inject
    private PersonaNaturalProvider personaNaturalProvider;

    @Inject
    private PersonaNaturalManager personaNaturalManager;

    private PersonaNaturalModel getPersonaNaturalModel() {
        return personaNaturalProvider.findById(idPersonaNatural);
    }

    @Override
    public PersonaNaturalRepresentation toRepresentation() {
        PersonaNaturalRepresentation rep = ModelToRepresentation.toRepresentation(getPersonaNaturalModel());
        if (rep != null) {
            return rep;
        } else {
            throw new NotFoundException("Persona Natural no encontrada");
        }
    }

    @Override
    public void update(PersonaNaturalRepresentation rep) {
        personaNaturalManager.update(getPersonaNaturalModel(), rep);
    }

    @Override
    public void getFoto() {
        // TODO Auto-generated method stub

    }

    @Override
    public void getFirma() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setFoto(MultipartFormDataInput input) {

    }

    @Override
    public void setFirma(MultipartFormDataInput input) {

    }

    @Override
    public Response remove() {
        PersonaNaturalModel personaNatural = getPersonaNaturalModel();
        if (personaNatural == null) {
            throw new NotFoundException("Persona Natural no encontrada");
        }
        boolean removed = personaNaturalProvider.remove(personaNatural);
        if (removed) {
            return Response.noContent().build();
        } else {
            return ErrorResponse.error("Persona Natural no pudo ser eliminada", Response.Status.BAD_REQUEST);
        }
    }

}
