package org.repeid.manager.api.rest.bussiness;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.repeid.manager.api.rest.contract.exceptions.TipoDocumentoAlreadyExistsException;
import org.repeid.representations.idm.TipoDocumentoRepresentation;
import org.repeid.representations.idm.search.SearchCriteriaRepresentation;
import org.repeid.representations.idm.search.SearchResultsRepresentation;

/**
 * @author carlosthe19916@gmail.com
 */

@Path("tipoDocumentos")
@Consumes(MediaType.APPLICATION_JSON)
public interface TiposDocumentoResource {

	public static final String TIPO_DOCUMENTO_ID = "tipoDocumentoId";

	@Path(TIPO_DOCUMENTO_ID)
	public TipoDocumentoResource tipoDocumento(@PathParam(TIPO_DOCUMENTO_ID) String tipoDocumentoId);

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(TipoDocumentoRepresentation rep) throws TipoDocumentoAlreadyExistsException;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TipoDocumentoRepresentation> search(@QueryParam("denominacion") String denominacion,
			@QueryParam("abreviatura") String abreviatura, @QueryParam("tipoPersona") String tipoPersona,
			@QueryParam("estado") Boolean estado, @QueryParam("filterText") String filterText,
			@QueryParam("first") Integer firstResult, @QueryParam("max") Integer maxResults);

	/**
	 * Este endpoint provee una forma de buscar direccionesRegionales. Los
	 * criterios de busqueda estan definidos por los parametros enviados.
	 * 
	 * @summary Search for DireccionesRegionales
	 * @param criteria
	 *            Criterio de busqueda.
	 * @statuscode 200 Si la busqueda fue realizada satisfactoriamente.
	 * @return Los resultados de la busqueda (una pagina de
	 *         direccionesRegionales).
	 */
	@POST
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
	public SearchResultsRepresentation<TipoDocumentoRepresentation> search(SearchCriteriaRepresentation criteria);

}