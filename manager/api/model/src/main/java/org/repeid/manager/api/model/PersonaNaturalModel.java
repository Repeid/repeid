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

import org.repeid.manager.api.model.enums.EstadoCivil;
import org.repeid.manager.api.model.enums.Sexo;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public interface PersonaNaturalModel extends Model {

	String APELLIDO_PATERNO = "apellidoPaterno";
	String APELLIDO_MATERNO = "apellidoMaterno";
	String NOMBRES = "nombres";
	String NUMERO_DOCUMENTO = "numeroDocumento";

	String getId();

	String getCodigoPais();

	void setCodigoPais(String codigoPais);

	TipoDocumentoModel getTipoDocumento();

	String getNumeroDocumento();

	String getApellidoPaterno();

	void setApellidoPaterno(String apellidoPaterno);

	String getApellidoMaterno();

	void setApellidoMaterno(String apellidoMaterno);

	String getNombres();

	void setNombres(String nombres);

	Date getFechaNacimiento();

	void setFechaNacimiento(Date fechaNacimiento);

	Sexo getSexo();

	void setSexo(Sexo sexo);

	EstadoCivil getEstadoCivil();

	void setEstadoCivil(EstadoCivil estadoCivil);

	String getOcupacion();

	void setOcupacion(String ocupacion);

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

	StoredFileModel getFoto();

	void setFoto(StoredFileModel foto);

	StoredFileModel getFirma();

	void setFirma(StoredFileModel firma);

}