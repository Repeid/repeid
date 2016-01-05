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

import org.repeid.manager.api.rest.contract.exceptions.InvalidSearchCriteriaException;
import org.repeid.manager.api.rest.contract.exceptions.NotAuthorizedException;
import org.repeid.manager.api.rest.contract.exceptions.PersonaNaturalAlreadyExistsException;
import org.repeid.representations.idm.PersonaNaturalRepresentation;
import org.repeid.representations.idm.search.SearchCriteriaRepresentation;
import org.repeid.representations.idm.search.SearchResultsRepresentation;

/**
 * @author carlosthe19916@gmail.com
 */

@Path("/personas/naturales")
@Consumes(MediaType.APPLICATION_JSON)
public interface PersonasNaturalesResource {

	public static final String PERSONA_NATURAL_ID = "personaNaturalId";

	/**
	 * @param personaNaturalId
	 *            El ID de la PersonaNatural.
	 */
	@Path("{" + PERSONA_NATURAL_ID + "}")
	public PersonaNaturalResource personaNatural(@PathParam(PERSONA_NATURAL_ID) String personaNaturalId);

	/**
	 * Use este endpoint para crear una nueva personaNatural. Una PersonaNatural
	 * representa una persona sin razon social.
	 * 
	 * @summary Create PersonaNatural
	 * @servicetag admin
	 * @param rep
	 *            La nueva PersonaNatural.
	 * @statuscode 200 Si la personaNatural fue creada satisfactoriamente.
	 * @return La informacion completa de la personaNatural creada.
	 * @throws PersonaNaturalAlreadyExistsException
	 *             cuando la personaNatural ya existe.
	 * @throws NotAuthorizedException
	 *             cuando no esta autorizado para invocar este metodo.
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(PersonaNaturalRepresentation rep)
			throws PersonaNaturalAlreadyExistsException, NotAuthorizedException;

	/**
	 * Este endpoint provee una forma de buscar personaNaturales. Los criterios
	 * de busqueda estan definidos por los parametros enviados.
	 * 
	 * @summary Search for PersonaNatural
	 * 
	 * @param tipoDocumento
	 *            TipoDocumento de personaNatural.
	 * @param numeroDocumento
	 *            NumeroDocumento de personaNatural.
	 * @param apellidoPaterno
	 *            ApellidoPaterno de personaNatural.
	 * @param apellidoMaterno
	 *            ApellidoMaterno de personaNatural.
	 * @param nombres
	 *            Nombres de personaNatural.
	 * @param filterText
	 *            FilterText representa el patron de busqueda.
	 * @param first
	 *            First representa el primer elemento del resutado de busqueda.
	 * @param max
	 *            Max representa el numero maximo de elementos del resutado de
	 *            busqueda.
	 * 
	 * @statuscode 200 Si la busqueda fue realizada satisfactoriamente.
	 * @return Los resultados de la busqueda (una pagina de personaNaturales).
	 * @throws NotAuthorizedException
	 *             cuando no esta autorizado para invocar este metodo.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<PersonaNaturalRepresentation> search(@QueryParam("tipoDocumento") String tipoDocumento,
			@QueryParam("numeroDocumento") String numeroDocumento,
			@QueryParam("apellidoPaterno") String apellidoPaterno,
			@QueryParam("apellidoMaterno") String apellidoMaterno, @QueryParam("nombres") String nombres,
			@QueryParam("filterText") String filterText, @QueryParam("first") Integer firstResult,
			@QueryParam("max") Integer maxResults) throws NotAuthorizedException;

	/**
	 * Este endpoint provee una forma de buscar personaNaturales. Los criterios
	 * de busqueda estan definidos por los parametros enviados.
	 * 
	 * @summary Search for PersonaNatural
	 * @param criteria
	 *            Criterio de busqueda.
	 * @statuscode 200 Si la busqueda fue realizada satisfactoriamente.
	 * @return Los resultados de la busqueda (una pagina de personaNaturales).
	 * @throws InvalidSearchCriteriaException
	 *             cuando el criterio de busqueda no es valido.
	 * @throws NotAuthorizedException
	 *             cuando no esta autorizado para invocar este metodo.
	 */
	@POST
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
	public SearchResultsRepresentation<PersonaNaturalRepresentation> search(SearchCriteriaRepresentation criteria)
			throws InvalidSearchCriteriaException, NotAuthorizedException;

}