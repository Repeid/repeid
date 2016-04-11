package org.repeid.services.resources.admin;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

@Path("/admin")
public interface AdminRoot {

	/**
	 * Base Path to tipoDocumento admin REST interface
	 *
	 * @param headers
	 * @return
	 */
	@Path("tiposDocumento")
	public TiposDocumentoResource getTiposDocumentoResource(@Context final HttpHeaders headers);

	/**
	 * Base Path to persona admin REST interface
	 *
	 * @param headers
	 * @return
	 */
	@Path("personas")
	public PersonasResource getPersonasResource(@Context final HttpHeaders headers);

	/**
	 * Base Path to utils admin REST interface
	 *
	 * @param headers
	 * @return
	 */
	@Path("utils")
	public MaestroResource getUtilsResource(@Context final HttpHeaders headers);

	/**
	 * General information about the server
	 *
	 * @param headers
	 * @return
	 */
	@Path("serverinfo")
	public ServerInfoAdminResource getServerInfo(@Context final HttpHeaders headers);

}
