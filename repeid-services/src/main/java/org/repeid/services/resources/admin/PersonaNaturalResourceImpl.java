package org.repeid.services.resources.admin;

import java.io.ByteArrayInputStream;
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
import org.repeid.models.StoreConfigurationModel;
import org.repeid.models.StoreConfigurationProvider;
import org.repeid.models.StoredFileModel;
import org.repeid.models.StoredFileProvider;
import org.repeid.models.StoredFileProviderFactory;
import org.repeid.models.utils.ModelToRepresentation;
import org.repeid.representations.idm.PersonaNaturalRepresentation;
import org.repeid.representations.idm.StoredFileRepresentation;
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
    private StoreConfigurationProvider storageConfigurationProvider;

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
    public Response getFoto() {
        PersonaNaturalModel personaNatural = getPersonaNaturalModel();
        StoredFileModel storedFileModel = personaNatural.getFoto();
        StoreConfigurationModel config = storedFileModel.getStoreConfiguration();
        StoredFileProvider storedFileProvider = storedFileProviderFactory.get(config);
        byte[] file = storedFileProvider.download(storedFileModel.getId());

        personaNaturalManager.setFoto(personaNatural, storedFileProvider, file);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(file);
        return Response.ok(byteArrayInputStream).build();
    }

    @Override
    public Response getFirma() {
        PersonaNaturalModel personaNatural = getPersonaNaturalModel();
        StoredFileModel storedFileModel = personaNatural.getFirma();
        StoreConfigurationModel config = storedFileModel.getStoreConfiguration();
        StoredFileProvider storedFileProvider = storedFileProviderFactory.get(config);
        byte[] file = storedFileProvider.download(storedFileModel.getId());

        personaNaturalManager.setFoto(personaNatural, storedFileProvider, file);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(file);
        return Response.ok(byteArrayInputStream).build();
    }

    @Override
    public StoredFileRepresentation setFoto(MultipartFormDataInput input) {
        PersonaNaturalModel personaNatural = getPersonaNaturalModel();

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");
        for (InputPart inputPart : inputParts) {
            try {
                // convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);

                StoreConfigurationModel config = storageConfigurationProvider.getDefaultStoreConfiguration();
                StoredFileProvider fileProvider = storedFileProviderFactory.get(config);
                StoredFileModel storedFileModel = personaNaturalManager.setFoto(personaNatural, fileProvider,
                        bytes);
                return ModelToRepresentation.toRepresentation(storedFileModel);
            } catch (IOException e) {
                throw new InternalServerErrorException();
            }
        }
        return null;
    }

    @Override
    public StoredFileRepresentation setFirma(MultipartFormDataInput input) {
        PersonaNaturalModel personaNatural = getPersonaNaturalModel();

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("file");
        for (InputPart inputPart : inputParts) {
            try {
                // convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);

                StoreConfigurationModel config = storageConfigurationProvider.getDefaultStoreConfiguration();
                StoredFileProvider fileProvider = storedFileProviderFactory.get(config);
                StoredFileModel storedFileModel = personaNaturalManager.setFirma(personaNatural, fileProvider,
                        bytes);
                return ModelToRepresentation.toRepresentation(storedFileModel);
            } catch (IOException e) {
                throw new InternalServerErrorException();
            }
        }
        return null;
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
