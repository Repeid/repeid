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
package org.repeid.models.jpa.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
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
@Table(name = "NATURAL_PERSON")
@NamedQueries(value = {
		@NamedQuery(name = "NaturalPersonEntity.findAll", query = "SELECT p FROM NaturalPersonEntity p"),
		@NamedQuery(name = "NaturalPersonEntity.findByTipoDocumento", query = "SELECT p FROM NaturalPersonEntity p INNER JOIN p.tipoDocumento t WHERE t.abreviatura = :tipoDocumento"),
		@NamedQuery(name = "NaturalPersonEntity.findByTipoNumeroDocumento", query = "SELECT p FROM NaturalPersonEntity p INNER JOIN p.tipoDocumento t WHERE t.abreviatura = :tipoDocumento AND p.numeroDocumento = :numeroDocumento"),
		@NamedQuery(name = "NaturalPersonEntity.findByFilterText", query = "SELECT p FROM NaturalPersonEntity p WHERE LOWER(p.numeroDocumento) LIKE :filterText OR LOWER(p.apellidoPaterno) LIKE :filterText OR LOWER(p.apellidoMaterno) LIKE :filterText OR LOWER(p.nombres) LIKE :filterText") })
public class NaturalPersonEntity extends PersonEntity implements Serializable {

	@Id
	@Access(AccessType.PROPERTY) // we do this because relationships often fetch id, but not entity.  This avoids an extra SQL
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id")
	private String id;

	@NotNull
	@Size(min = 1, max = 70)
	@NotBlank
	@Column(name = "first_name")
	private String firstName;

	@NotNull
	@Size(min = 1, max = 70)
	@NotBlank
	@Column(name = "middle_name")
	private String middleName;

	@NotNull
	@Size(min = 1, max = 70)
	@NotBlank
	@Column(name = "last_name")
	private String lastName;

	@NotNull
	@Past
	@Column(name = "date_birth")
	private LocalDate date_bith;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "gender")
	private String gender;

	@Size(min = 1, max = 50)
	@Column(name = "marriage_status")
	private String marriageStatus;

	@Size(min = 0, max = 70)
	@Column(name = "job")
	private String job;

	public NaturalPersonEntity() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDate_bith() {
		return date_bith;
	}

	public void setDate_bith(LocalDate date_bith) {
		this.date_bith = date_bith;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMarriageStatus() {
		return marriageStatus;
	}

	public void setMarriageStatus(String marriageStatus) {
		this.marriageStatus = marriageStatus;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
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
		if (!(obj instanceof NaturalPersonEntity))
			return false;
		NaturalPersonEntity other = (NaturalPersonEntity) obj;
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
