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
package org.repeid.manager.api.model;

import java.util.Date;

import org.repeid.manager.api.model.enums.TipoEmpresa;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

public interface PersonaJuridicaModel extends Model {

	String RAZON_SOCIAL = "razonSocial";
	String NOMBRE_COMERCIAL = "nombreComercial";
	String NUMERO_DOCUMENTO = "numeroDocumento";

	String getId();

	PersonaNaturalModel getRepresentanteLegal();

	void setRepresentanteLegal(PersonaNaturalModel representanteLegal);

	String getCodigoPais();

	void setCodigoPais(String codigoPais);

	TipoDocumentoModel getTipoDocumento();

	String getNumeroDocumento();

	String getRazonSocial();

	void setRazonSocial(String razonSocial);

	String getNombreComercial();

	void setNombreComercial(String nombreComercial);

	Date getFechaConstitucion();

	void setFechaConstitucion(Date fechaConstitucion);

	String getActividadPrincipal();

	void setActividadPrincipal(String actividadPrincipal);

	TipoEmpresa getTipoEmpresa();

	void setTipoEmpresa(TipoEmpresa tipoEmpresa);

	boolean isFinLucro();

	void setFinLucro(boolean finLucro);

	String getUbigeo();

	void setUbigeo(String ubigeo);

	String getDireccion();

	void setDireccion(String direccion);

	String getReferencia();

	void setReferencia(String referencia);

	String getTelefono();

	void setTelefono(String telefono);

	String getCelular();

	void setCelular(String celular);

	String getEmail();

	void setEmail(String email);

}