package org.repeid.manager.api.rest.bussiness;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.repeid.manager.api.beans.representations.TipoDocumentoRepresentation;
import org.repeid.manager.api.rest.contract.exceptions.NotAuthorizedException;
import org.repeid.manager.api.rest.contract.exceptions.TipoDocumentoNotFoundException;

/**
 * TipoDocumento API. Usado para administrar tipoDocumentos. Nota: los tipos de
 * documento son usados por las personas naturales y juridicas para poder ser
 * identificadas.
 * 
 * @author carlosthe19916@gmail.com
 */
public interface TipoDocumentoResource {

	/**
	 * Use este endpoint para obtener informacion hacerca de un TipoDocumento
	 * por medio de su ID.
	 * 
	 * @summary Get a TipoDocumento by ID
	 * 
	 * @statuscode 200 Si el tipoDocumento fue retornado satisfactoriamente.
	 * @return Un tipoDocumento.
	 * @throws TipoDocumentoNotFoundException
	 *             cuando el request envia un tipoDocumento que no existe.
	 * @throws NotAuthorizedException
	 *             cuando no esta autorizado para invocar este metodo.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public TipoDocumentoRepresentation toRepresentation() throws TipoDocumentoNotFoundException, NotAuthorizedException;

	/**
	 * Use este endpoint para actualizar la informacion relacionada a un
	 * tipoDocumento. El tipoDocumento es identificado por medio de su ID.
	 * 
	 * @summary Update a TipoDocumento by ID
	 * @servicetag admin
	 * 
	 * @param rep
	 *            Informacion actualizada del tipoDocumento.
	 * @statuscode 204 Si el tipoDocumento fue actualizado satisfactoriamente.
	 * @throws TipoDocumentoNotFoundException
	 *             cuando el request envia un tipoDocumento que no existe.
	 * @throws NotAuthorizedException
	 *             cuando no esta autorizado para invocar este metodo.
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(TipoDocumentoRepresentation rep) throws TipoDocumentoNotFoundException, NotAuthorizedException;

	/**
	 * Use este endpoint para activar un tipoDocumento por medio de su ID.
	 * 
	 * @summary Enable a TipoDocumento by ID
	 * @servicetag admin
	 * 
	 * @statuscode 204 Si el tipoDocumento fue activado.
	 * @throws TipoDocumentoNotFoundException
	 *             cuando el request envia un tipoDocumento que no existe.
	 * @throws NotAuthorizedException
	 *             cuando no esta autorizado para invocar este metodo.
	 */
	@POST
	@Path("enable")
	public void enable() throws TipoDocumentoNotFoundException, NotAuthorizedException;

	/**
	 * Use este endpoint para desactivar un tipoDocumento por medio de su ID.
	 * 
	 * @summary Disable a TipoDocumento by ID
	 * @servicetag admin
	 * 
	 * @statuscode 204 Si el tipoDocumento fue desactivado.
	 * @throws TipoDocumentoNotFoundException
	 *             cuando el request envia un tipoDocumento que no existe.
	 * @throws NotAuthorizedException
	 *             cuando no esta autorizado para invocar este metodo.
	 */
	@POST
	@Path("disable")
	public void disable() throws TipoDocumentoNotFoundException, NotAuthorizedException;

	/**
	 * Use este endpoint para eliminar un tipoDocumento por medio de su ID.
	 * 
	 * @summary Delete a TipoDocumento by ID
	 * @servicetag admin
	 * 
	 * @statuscode 204 Si el tipoDocumento fue eliminado.
	 * @throws TipoDocumentoNotFoundException
	 *             cuando el request envia un tipoDocumento que no existe.
	 * @throws NotAuthorizedException
	 *             cuando no esta autorizado para invocar este metodo.
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response remove() throws TipoDocumentoNotFoundException, NotAuthorizedException;

}