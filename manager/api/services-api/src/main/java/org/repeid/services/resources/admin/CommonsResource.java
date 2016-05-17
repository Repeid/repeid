package org.repeid.services.resources.admin;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/commons")
@Produces(MediaType.APPLICATION_JSON)
public interface CommonsResource {

    /**
     * Este endpoint retorna todos los tipos de persona soportados por repeid.
     *
     * @return Los tipos de persona soportados por repeid.
     * @summary Get all TipoPersona
     * @statuscode 200 Si la busqueda fue realizada satisfactoriamente.
     */
    @GET
    @Path("personsType")
    public List<String> getPersonsType();

    /**
     * Este endpoint retorna todos los estados civiles soportados por repeid.
     *
     * @return Los estado civiles soportados por repeid.
     * @summary Get all EstadoCivil
     * @statuscode 200 Si la busqueda fue realizada satisfactoriamente.
     */
    @GET
    @Path("maritalStatus")
    public List<String> getMaritalStatus();

    /**
     * Este endpoint retorna todos los sexos soportados por repeid.
     *
     * @return Los sexos soportados por repeid.
     * @summary Get all Sexo
     * @statuscode 200 Si la busqueda fue realizada satisfactoriamente.
     */
    @GET
    @Path("genders")
    public List<String> getGenders();

    /**
     * Este endpoint retorna todos los tipos de empresa soportados por repeid.
     *
     * @return Los tipos de empresa soportados por repeid.
     * @summary Get all TipoEmpresa
     * @statuscode 200 Si la busqueda fue realizada satisfactoriamente.
     */
    @GET
    @Path("bussinessType")
    public List<String> getBussinessType();

}
