package org.repeid.services.resources.admin;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.repeid.admin.client.resource.PersonaNaturalResource;
import org.repeid.models.PersonaNaturalModel;
import org.repeid.models.PersonaNaturalProvider;
import org.repeid.models.StorageConfigurationModel;
import org.repeid.models.StorageConfigurationProvider;
import org.repeid.models.StoredFileModel;
import org.repeid.models.StoredFileProvider;
import org.repeid.models.StoredFileProviderFactory;
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

    @Inject
    private StorageConfigurationProvider storageConfigurationProvider;

    @Inject
    private StoredFileProviderFactory storedFileProviderFactory;

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
        PersonaNaturalModel personaNatural = getPersonaNaturalModel();
        StoredFileModel storedFileModel = personaNatural.getFirma();
        storedFileModel.get
        StorageConfigurationModel config = storageConfigurationProvider.getDefaultStoreConfiguration();
        StoredFileProvider storedFileProvider = storedFileProviderFactory.get(config);
        personaNaturalManager.setFoto(personaNatural, storedFileProvider, bytes);
    }

    @Override
    public void setFoto(MultipartFormDataInput input) {
        PersonaNaturalModel personaNatural = getPersonaNaturalModel();

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");
        for (InputPart inputPart : inputParts) {
            try {
                // convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);

                StorageConfigurationModel config = storageConfigurationProvider
                        .getDefaultStoreConfiguration();
                StoredFileProvider storedFileProvider = storedFileProviderFactory.get(config);
                personaNaturalManager.setFoto(personaNatural, storedFileProvider, bytes);
            } catch (IOException e) {
                throw new InternalServerErrorException();
            }
        }
    }

    @Override
    public void setFirma(MultipartFormDataInput input) {
        PersonaNaturalModel personaNatural = getPersonaNaturalModel();

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");
        for (InputPart inputPart : inputParts) {
            try {
                // convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);

                StorageConfigurationModel config = storageConfigurationProvider
                        .getDefaultStoreConfiguration();
                StoredFileProvider storedFileProvider = storedFileProviderFactory.get(config);
                personaNaturalManager.setFirma(personaNatural, storedFileProvider, bytes);
            } catch (IOException e) {
                throw new InternalServerErrorException();
            }
        }
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
