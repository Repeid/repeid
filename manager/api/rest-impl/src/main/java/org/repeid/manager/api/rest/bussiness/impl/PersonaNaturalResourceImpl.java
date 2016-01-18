/*******************************************************************************
 * Repeid, Home of Professional Open Source
 *
 * Copyright 2015 Sistcoop, Inc. and/or its affiliates.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.repeid.manager.api.rest.bussiness.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.beans.representations.PersonaNaturalRepresentation;
import org.repeid.manager.api.beans.representations.StoredFileRepresentation;
import org.repeid.manager.api.beans.representations.security.PermissionType;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.PersonaNaturalProvider;
import org.repeid.manager.api.model.utils.ModelToRepresentation;
import org.repeid.manager.api.rest.bussiness.PersonaNaturalResource;
import org.repeid.manager.api.rest.bussiness.PersonasNaturalesResource;
import org.repeid.manager.api.rest.contract.exceptions.NotAuthorizedException;
import org.repeid.manager.api.rest.contract.exceptions.PersonaNaturalNotFoundException;
import org.repeid.manager.api.rest.contract.exceptions.SystemErrorException;
import org.repeid.manager.api.rest.impl.util.ExceptionFactory;
import org.repeid.manager.api.rest.managers.PersonaNaturalManager;
import org.repeid.manager.api.security.ISecurityContext;

@Stateless
public class PersonaNaturalResourceImpl implements PersonaNaturalResource {

    @PathParam(PersonasNaturalesResource.PERSONA_NATURAL_ID)
    private String personaNaturalId;

    @Inject
    private PersonaNaturalProvider personaNaturalProvider;

    @Inject
    private PersonaNaturalManager personaNaturalManager;

    // @Inject
    // private StoreConfigurationProvider storageConfigurationProvider;

    // @Inject
    // private StoredFileProvider storedFileProvider;

    @Inject
    private ISecurityContext iSecurityContext;

    private PersonaNaturalModel getPersonaNaturalModel() throws StorageException {
        return personaNaturalProvider.findById(personaNaturalId);
    }

    @Override
    public PersonaNaturalRepresentation toRepresentation()
            throws PersonaNaturalNotFoundException, NotAuthorizedException {

        if (!iSecurityContext.hasPermission(PermissionType.personaView))
            throw ExceptionFactory.notAuthorizedException();

        try {
            PersonaNaturalModel personaNatural = getPersonaNaturalModel();
            if (personaNatural == null) {
                throw ExceptionFactory.personaNaturalNotFoundException(personaNaturalId);
            }
            return ModelToRepresentation.toRepresentation(personaNatural);
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public void update(PersonaNaturalRepresentation rep)
            throws PersonaNaturalNotFoundException, NotAuthorizedException {

        if (!iSecurityContext.hasPermission(PermissionType.documentoEdit))
            throw ExceptionFactory.notAuthorizedException();

        try {
            PersonaNaturalModel personaNatural = getPersonaNaturalModel();
            if (personaNatural == null) {
                throw ExceptionFactory.personaNaturalNotFoundException(personaNaturalId);
            }
            personaNaturalManager.update(personaNatural, rep);
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public Response getFoto() throws PersonaNaturalNotFoundException, NotAuthorizedException {
        /*
         * PersonaNaturalModel personaNatural = getPersonaNaturalModel();
         * StoredFileModel storedFileModel = personaNatural.getFoto();
         * StoreConfigurationModel config =
         * storedFileModel.getStoreConfiguration(); StoredFileProvider
         * storedFileProvider = storedFileProviderFactory.get(config); byte[]
         * file = storedFileProvider.download(storedFileModel.getId());
         * 
         * personaNaturalManager.setFoto(personaNatural, storedFileProvider,
         * file); ByteArrayInputStream byteArrayInputStream = new
         * ByteArrayInputStream(file); return
         * Response.ok(byteArrayInputStream).build();
         */
        return null;
    }

    @Override
    public Response getFirma() throws PersonaNaturalNotFoundException, NotAuthorizedException {
        /*
         * PersonaNaturalModel personaNatural = getPersonaNaturalModel();
         * StoredFileModel storedFileModel = personaNatural.getFirma();
         * StoreConfigurationModel config =
         * storedFileModel.getStoreConfiguration(); StoredFileProvider
         * storedFileProvider = storedFileProviderFactory.get(config); byte[]
         * file = storedFileProvider.download(storedFileModel.getId());
         * 
         * personaNaturalManager.setFoto(personaNatural, storedFileProvider,
         * file); ByteArrayInputStream byteArrayInputStream = new
         * ByteArrayInputStream(file); return
         * Response.ok(byteArrayInputStream).build();
         */
        return null;
    }

    @Override
    public StoredFileRepresentation setFoto(MultipartFormDataInput input)
            throws PersonaNaturalNotFoundException, NotAuthorizedException {
        /*
         * PersonaNaturalModel personaNatural = getPersonaNaturalModel();
         * 
         * Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
         * List<InputPart> inputParts = uploadForm.get("file"); for (InputPart
         * inputPart : inputParts) { try { // convert the uploaded file to
         * inputstream InputStream inputStream =
         * inputPart.getBody(InputStream.class, null); byte[] bytes =
         * IOUtils.toByteArray(inputStream);
         * 
         * StoreConfigurationModel config =
         * storageConfigurationProvider.getDefaultStoreConfiguration();
         * StoredFileModel storedFileModel =
         * personaNaturalManager.setFoto(personaNatural, config, bytes,
         * storedFileProvider); return
         * ModelToRepresentation.toRepresentation(storedFileModel); } catch
         * (IOException e) { throw new InternalServerErrorException(); } }
         * return null;
         */
        return null;
    }

    @Override
    public StoredFileRepresentation setFirma(MultipartFormDataInput input)
            throws PersonaNaturalNotFoundException, NotAuthorizedException {
        /*
         * PersonaNaturalModel personaNatural = getPersonaNaturalModel();
         * 
         * Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
         * List<InputPart> inputParts = uploadForm.get("file"); for (InputPart
         * inputPart : inputParts) { try { // convert the uploaded file to
         * inputstream InputStream inputStream =
         * inputPart.getBody(InputStream.class, null); byte[] bytes =
         * IOUtils.toByteArray(inputStream);
         * 
         * StoreConfigurationModel config =
         * storageConfigurationProvider.getDefaultStoreConfiguration();
         * StoredFileModel storedFileModel =
         * personaNaturalManager.setFirma(personaNatural, config, bytes,
         * storedFileProvider); return
         * ModelToRepresentation.toRepresentation(storedFileModel); } catch
         * (IOException e) { throw new InternalServerErrorException(); } }
         * return null;
         */
        return null;
    }

    @Override
    public Response remove() throws PersonaNaturalNotFoundException, NotAuthorizedException {

        if (!iSecurityContext.hasPermission(PermissionType.documentoAdmin))
            throw ExceptionFactory.notAuthorizedException();

        try {
            PersonaNaturalModel personaNatural = getPersonaNaturalModel();
            if (personaNatural == null) {
                throw ExceptionFactory.personaNaturalNotFoundException(personaNaturalId);
            }

            boolean removed = personaNaturalProvider.remove(personaNatural);
            if (removed) {
                return Response.noContent().build();
            } else {
                throw ExceptionFactory.personaNaturalLockedException(personaNaturalId);
            }
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

}
