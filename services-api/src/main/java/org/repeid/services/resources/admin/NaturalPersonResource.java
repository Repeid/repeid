package org.repeid.services.resources.admin;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.repeid.representations.idm.NaturalPersonRepresentation;

/**
 * PersonaNatural API. Usado para administrar personaNaturales. Nota: Las
 * personas naturales es la entidad mas usada, representa a una persona comun.
 *
 * @author carlosthe19916@gmail.com
 */
public interface NaturalPersonResource {

    /**
     * Use este endpoint para obtener informacion hacerca de una PersonaNatural
     * por medio de su ID.
     *
     * @return Una personaNatural.
     * @throws PersonaNaturalNotFoundException cuando el request envia una personaNatural que no existe.
     * @throws NotAuthorizedException          cuando no esta autorizado para invocar este metodo.
     * @summary Get a PersonaNatural by ID
     * @statuscode 200 Si la personaNatural fue retornada satisfactoriamente.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public NaturalPersonRepresentation toRepresentation();

    /**
     * Use este endpoint para actualizar la informacion relacionada a una
     * personaNatural. La personaNatural es identificada por medio de su ID.
     *
     * @param rep Informacion actualizada de la personaNatural.
     * @throws PersonaNaturalNotFoundException cuando el request envia una personaNatural que no existe.
     * @throws NotAuthorizedException          cuando no esta autorizado para invocar este metodo.
     * @summary Update a PersonaNatural by ID
     * @servicetag admin
     * @statuscode 204 Si la personaNatural fue actualizada satisfactoriamente.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(NaturalPersonRepresentation rep);

    /**
     * Use este endpoint para obtener la foto de una PersonaNatural por medio de
     * su ID.
     *
     * @return Una imagen.
     * @throws PersonaNaturalNotFoundException cuando el request envia una personaNatural que no existe.
     * @throws NotAuthorizedException          cuando no esta autorizado para invocar este metodo.
     * @summary Get a Foto by ID
     * @statuscode 200 Si la foto de la personaNatural fue retornada
     * satisfactoriamente.
     */
    @GET
    @Path("foto")
    @Produces("image/png")
    public Response getFoto();

    /**
     * Use este endpoint para obtener la firma en formato de imagen de una
     * PersonaNatural por medio de su ID.
     *
     * @return Una imagen.
     * @throws PersonaNaturalNotFoundException cuando el request envia una personaNatural que no existe.
     * @throws NotAuthorizedException          cuando no esta autorizado para invocar este metodo.
     * @summary Get a Firma by ID
     * @statuscode 200 Si la firma de la personaNatural fue retornada
     * satisfactoriamente.
     */
    @GET
    @Path("firma")
    @Produces("image/png")
    public Response getFirma();

    /**
     * Use este endpoint para eliminar una personaNatural por medio de su ID.
     *
     * @throws PersonaNaturalNotFoundException cuando el request envia un tipoDocumento que no existe.
     * @throws NotAuthorizedException          cuando no esta autorizado para invocar este metodo.
     * @summary Delete a PersonaNatural by ID
     * @servicetag admin
     * @statuscode 204 Si la personaNatural fue eliminada.
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public void remove();

}