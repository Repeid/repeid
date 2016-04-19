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

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
@Entity
@Table(name = "DOCUMENT")
@NamedQueries(value = {
		@NamedQuery(name = "DocumentEntity.findAll", query = "SELECT document FROM DocumentEntity document"),
		@NamedQuery(name = "DocumentEntity.findByAbbreviation", query = "SELECT document FROM DocumentEntity document WHERE document.abbreviation = :abbreviation"),
		@NamedQuery(name = "DocumentEntity.findByFilterText", query = "SELECT document FROM DocumentEntity document WHERE LOWER(document.abbreviation) LIKE :filterText OR LOWER(document.name) LIKE :filterText") })
public class DocumentEntity implements Serializable {

	@Id
	@Access(AccessType.PROPERTY) // we do this because relationships often fetch id, but not entity.  This avoids an extra SQL
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id")
	private String id;

	@NaturalId(mutable = true)
	@Size(min = 1, max = 20)
	@Column(name = "abbreviation")
	private String abbreviation;

	@NotNull
	@Size(min = 1, max = 100)
	@NotBlank
	@Column(name = "name")
	private String name;

	@NotNull
	@Min(value = 1)
	@Max(value = 20)
	@Column(name = "size")
	private int size;

	@NotNull
	@Size(min = 1, max = 60)
	@Column(name = "person_type")
	private String personType;

	@NotNull
	@Type(type = "org.hibernate.type.TrueFalseType")
	@Column(name = "enabled")
	private boolean enabled;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abbreviation == null) ? 0 : abbreviation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DocumentEntity))
			return false;
		DocumentEntity other = (DocumentEntity) obj;
		if (abbreviation == null) {
			if (other.abbreviation != null)
				return false;
		} else if (!abbreviation.equals(other.abbreviation))
			return false;
		return true;
	}

}
