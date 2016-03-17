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
package org.repeid.manager.api.models.jpa.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
@Entity
@Table(name = "PERSONA_NATURAL")
@NamedQueries(value = {
		@NamedQuery(name = "PersonaNaturalEntity.findAll", query = "SELECT p FROM PersonaNaturalEntity p"),
		@NamedQuery(name = "PersonaNaturalEntity.findByTipoDocumento", query = "SELECT p FROM PersonaNaturalEntity p INNER JOIN p.tipoDocumento t WHERE t.abreviatura = :tipoDocumento"),
		@NamedQuery(name = "PersonaNaturalEntity.findByTipoNumeroDocumento", query = "SELECT p FROM PersonaNaturalEntity p INNER JOIN p.tipoDocumento t WHERE t.abreviatura = :tipoDocumento AND p.numeroDocumento = :numeroDocumento"),
		@NamedQuery(name = "PersonaNaturalEntity.findByFilterText", query = "SELECT p FROM PersonaNaturalEntity p WHERE LOWER(p.numeroDocumento) LIKE :filterText OR LOWER(p.apellidoPaterno) LIKE :filterText OR LOWER(p.apellidoMaterno) LIKE :filterText OR LOWER(p.nombres) LIKE :filterText") })
public class PersonaNaturalEntity extends PersonaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id")
	private String id;

	@NotNull
	@Size(min = 1, max = 50)
	@NotBlank
	@Column(name = "apellido_paterno")
	private String apellidoPaterno;

	@NotNull
	@Size(min = 1, max = 50)
	@NotBlank
	@Column(name = "apellido_materno")
	private String apellidoMaterno;

	@NotNull
	@Size(min = 1, max = 70)
	@NotBlank
	@Column(name = "nombres")
	private String nombres;

	@NotNull
	@Past
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_nacimiento")
	private Date fechaNacimiento;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "sexo")
	private String sexo;

	@Size(min = 1, max = 50)
	@Column(name = "estado_civil")
	private String estadoCivil;

	@Size(min = 0, max = 70)
	@Column(name = "ocupacion")
	private String ocupacion;
	
	public PersonaNaturalEntity() {
		super();
	}

	public PersonaNaturalEntity(String id) {
		super();
		this.id = id;
	}

	public PersonaNaturalEntity(TipoDocumentoEntity tipoDocumento, String numeroDocumento) {
		super(tipoDocumento, numeroDocumento);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}	

	@Override
	public String toString() {
		return "(PersonaNaturalEntity id=" + this.id + " tipoDocumento=" + this.tipoDocumento.getAbreviatura()
				+ " numeroDocumento" + this.numeroDocumento + " apellidoPaterno=" + this.apellidoPaterno
				+ " apellidoMaterno=" + this.apellidoMaterno + " nombres=" + this.nombres + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numeroDocumento == null) ? 0 : numeroDocumento.hashCode());
		result = prime * result + ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PersonaNaturalEntity))
			return false;
		PersonaNaturalEntity other = (PersonaNaturalEntity) obj;
		if (numeroDocumento == null) {
			if (other.numeroDocumento != null)
				return false;
		} else if (!numeroDocumento.equals(other.numeroDocumento))
			return false;
		if (tipoDocumento == null) {
			if (other.tipoDocumento != null)
				return false;
		} else if (!tipoDocumento.equals(other.tipoDocumento))
			return false;
		return true;
	}

}
