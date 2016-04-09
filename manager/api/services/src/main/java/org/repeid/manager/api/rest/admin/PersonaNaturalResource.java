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
package org.repeid.manager.api.rest.admin;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.repeid.manager.api.beans.representations.PersonaNaturalRepresentation;
import org.repeid.manager.api.beans.representations.StoredFileRepresentation;

/**
 * PersonaNatural API. Usado para administrar personaNaturales. Nota: Las
 * personas naturales es la entidad mas usada, representa a una persona comun.
 * 
 * @author carlosthe19916@gmail.com
 */
public interface PersonaNaturalResource {

    /**
     * Use este endpoint para obtener informacion hacerca de una PersonaNatural
     * por medio de su ID.
     * 
     * @summary Get a PersonaNatural by ID
     * 
     * @statuscode 200 Si la personaNatural fue retornada satisfactoriamente.
     * @return Una personaNatural.
     * @throws PersonaNaturalNotFoundException
     *             cuando el request envia una personaNatural que no existe.
     * @throws NotAuthorizedException
     *             cuando no esta autorizado para invocar este metodo.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PersonaNaturalRepresentation toRepresentation();

    /**
     * Use este endpoint para actualizar la informacion relacionada a una
     * personaNatural. La personaNatural es identificada por medio de su ID.
     * 
     * @summary Update a PersonaNatural by ID
     * @servicetag admin
     * 
     * @param rep
     *            Informacion actualizada de la personaNatural.
     * @statuscode 204 Si la personaNatural fue actualizada satisfactoriamente.
     * @throws PersonaNaturalNotFoundException
     *             cuando el request envia una personaNatural que no existe.
     * @throws NotAuthorizedException
     *             cuando no esta autorizado para invocar este metodo.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(PersonaNaturalRepresentation rep);

    /**
     * Use este endpoint para obtener la foto de una PersonaNatural por medio de
     * su ID.
     * 
     * @summary Get a Foto by ID
     * 
     * @statuscode 200 Si la foto de la personaNatural fue retornada
     *             satisfactoriamente.
     * @return Una imagen.
     * @throws PersonaNaturalNotFoundException
     *             cuando el request envia una personaNatural que no existe.
     * @throws NotAuthorizedException
     *             cuando no esta autorizado para invocar este metodo.
     */
    @GET
    @Path("foto")
    @Produces("image/png")
    public Response getFoto();

    /**
     * Use este endpoint para actualizar la foto relacionada a una
     * personaNatural. La personaNatural es identificada por medio de su ID.
     * 
     * @summary Update a Foto by ID
     * @servicetag admin
     * 
     * @param input
     *            MultipartFormDataInput input.
     * @statuscode 200 Si la personaNatural fue actualizada satisfactoriamente.
     * @throws PersonaNaturalNotFoundException
     *             cuando el request envia una personaNatural que no existe.
     * @throws NotAuthorizedException
     *             cuando no esta autorizado para invocar este metodo.
     */
    @POST
    @Path("foto")
    @Consumes("multipart/form-data")
    public StoredFileRepresentation setFoto(MultipartFormDataInput input);

    /**
     * Use este endpoint para obtener la firma en formato de imagen de una
     * PersonaNatural por medio de su ID.
     * 
     * @summary Get a Firma by ID
     * 
     * @statuscode 200 Si la firma de la personaNatural fue retornada
     *             satisfactoriamente.
     * @return Una imagen.
     * @throws PersonaNaturalNotFoundException
     *             cuando el request envia una personaNatural que no existe.
     * @throws NotAuthorizedException
     *             cuando no esta autorizado para invocar este metodo.
     */
    @GET
    @Path("firma")
    @Produces("image/png")
    public Response getFirma();

    /**
     * Use este endpoint para actualizar la firma relacionada a una
     * personaNatural. La personaNatural es identificada por medio de su ID.
     * 
     * @summary Update a Firma by ID
     * @servicetag admin
     * 
     * @param input
     *            MultipartFormDataInput input.
     * @statuscode 200 Si la personaNatural fue actualizada satisfactoriamente.
     * @throws PersonaNaturalNotFoundException
     *             cuando el request envia una personaNatural que no existe.
     * @throws NotAuthorizedException
     *             cuando no esta autorizado para invocar este metodo.
     */
    @POST
    @Path("firma")
    @Consumes("multipart/form-data")
    public StoredFileRepresentation setFirma(MultipartFormDataInput input);

    /**
     * Use este endpoint para eliminar una personaNatural por medio de su ID.
     * 
     * @summary Delete a PersonaNatural by ID
     * @servicetag admin
     * 
     * @statuscode 204 Si la personaNatural fue eliminada.
     * @throws PersonaNaturalNotFoundException
     *             cuando el request envia un tipoDocumento que no existe.
     * @throws NotAuthorizedException
     *             cuando no esta autorizado para invocar este metodo.
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public void remove();

}