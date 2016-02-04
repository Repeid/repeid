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
package org.repeid.manager.api.jpa.models;

import java.util.Date;

import javax.persistence.EntityManager;

import org.repeid.manager.api.jpa.entities.PersonaNaturalEntity;
import org.repeid.manager.api.jpa.entities.StoredFileEntity;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.StoredFileModel;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.enums.EstadoCivil;
import org.repeid.manager.api.model.enums.Sexo;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public class PersonaNaturalAdapter implements PersonaNaturalModel {

	private PersonaNaturalEntity personaNatural;

	private EntityManager em;

	public PersonaNaturalAdapter(EntityManager em, PersonaNaturalEntity personaNaturalEntity) {
		this.em = em;
		this.personaNatural = personaNaturalEntity;
	}

	public PersonaNaturalEntity getPersonaNaturalEntity() {
		return this.personaNatural;
	}

	public static PersonaNaturalEntity toPersonaNaturalEntity(PersonaNaturalModel model, EntityManager em) {
		if (model instanceof PersonaNaturalAdapter) {
			return ((PersonaNaturalAdapter) model).getPersonaNaturalEntity();
		}
		return em.getReference(PersonaNaturalEntity.class, model.getId());
	}

	@Override
	public void commit() {
		em.merge(personaNatural);
	}

	@Override
	public String getId() {
		return personaNatural.getId();
	}

	@Override
	public String getCodigoPais() {
		return personaNatural.getCodigoPais();
	}

	@Override
	public void setCodigoPais(String codigoPais) {
		personaNatural.setCodigoPais(codigoPais);
	}

	@Override
	public TipoDocumentoModel getTipoDocumento() {
		return new TipoDocumentoAdapter(em, personaNatural.getTipoDocumento());
	}

	@Override
	public String getNumeroDocumento() {
		return personaNatural.getNumeroDocumento();
	}

	@Override
	public String getApellidoPaterno() {
		return personaNatural.getApellidoPaterno();
	}

	@Override
	public void setApellidoPaterno(String apellidoPaterno) {
		personaNatural.setApellidoPaterno(apellidoPaterno);
	}

	@Override
	public String getApellidoMaterno() {
		return personaNatural.getApellidoMaterno();
	}

	@Override
	public void setApellidoMaterno(String apellidoMaterno) {
		personaNatural.setApellidoMaterno(apellidoMaterno);
	}

	@Override
	public String getNombres() {
		return personaNatural.getNombres();
	}

	@Override
	public void setNombres(String nombres) {
		personaNatural.setNombres(nombres);
	}

	@Override
	public Date getFechaNacimiento() {
		return personaNatural.getFechaNacimiento();
	}

	@Override
	public void setFechaNacimiento(Date fechaNacimiento) {
		personaNatural.setFechaNacimiento(fechaNacimiento);
	}

	@Override
	public Sexo getSexo() {
		String sexo = personaNatural.getSexo();
		return sexo != null ? Sexo.valueOf(sexo) : null;
	}

	@Override
	public void setSexo(Sexo sexo) {
		if (sexo != null) {
			personaNatural.setSexo(sexo.toString());
		} else {
			personaNatural.setSexo(null);
		}
	}

	@Override
	public EstadoCivil getEstadoCivil() {
		String estadoCivil = personaNatural.getEstadoCivil();
		return estadoCivil != null ? EstadoCivil.valueOf(estadoCivil) : null;
	}

	@Override
	public void setEstadoCivil(EstadoCivil estadoCivil) {
		if (estadoCivil != null) {
			personaNatural.setEstadoCivil(estadoCivil.toString());
		} else {
			personaNatural.setEstadoCivil(null);
		}
	}

	@Override
	public String getOcupacion() {
		return personaNatural.getOcupacion();
	}

	@Override
	public void setOcupacion(String ocupacion) {
		personaNatural.setOcupacion(ocupacion);
	}

	@Override
	public String getUbigeo() {
		return personaNatural.getUbigeo();
	}

	@Override
	public void setUbigeo(String ubigeo) {
		personaNatural.setUbigeo(ubigeo);
	}

	@Override
	public String getDireccion() {
		return personaNatural.getDireccion();
	}

	@Override
	public void setDireccion(String direccion) {
		personaNatural.setDireccion(direccion);
	}

	@Override
	public String getReferencia() {
		return personaNatural.getReferencia();
	}

	@Override
	public void setReferencia(String referencia) {
		personaNatural.setReferencia(referencia);
	}

	@Override
	public String getTelefono() {
		return personaNatural.getTelefono();
	}

	@Override
	public void setTelefono(String telefono) {
		personaNatural.setTelefono(telefono);
	}

	@Override
	public String getCelular() {
		return personaNatural.getCelular();
	}

	@Override
	public void setCelular(String celular) {
		personaNatural.setCelular(celular);
	}

	@Override
	public String getEmail() {
		return personaNatural.getEmail();
	}

	@Override
	public void setEmail(String email) {
		personaNatural.setEmail(email);
	}

	@Override
	public StoredFileModel getFoto() {
		StoredFileEntity storedFileEntity = personaNatural.getFoto();
		if (storedFileEntity != null) {
			return new StoredFileAdapter(em, storedFileEntity);
		} else {
			return null;
		}
	}

	@Override
	public StoredFileModel getFirma() {
		StoredFileEntity storedFileEntity = personaNatural.getFirma();
		if (storedFileEntity != null) {
			return new StoredFileAdapter(em, storedFileEntity);
		} else {
			return null;
		}
	}

	@Override
	public void setFoto(StoredFileModel foto) {
		if (foto != null) {
			StoredFileEntity storedFileEntity = StoredFileAdapter.toStoredFileEntity(foto, em);
			personaNatural.setFoto(storedFileEntity);
		} else {
			personaNatural.setFoto(null);
		}
	}

	@Override
	public void setFirma(StoredFileModel firma) {
		if (firma != null) {
			StoredFileEntity storedFileEntity = StoredFileAdapter.toStoredFileEntity(firma, em);
			personaNatural.setFirma(storedFileEntity);
		} else {
			personaNatural.setFirma(null);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getNumeroDocumento() == null) ? 0 : getNumeroDocumento().hashCode());
		result = prime * result + ((getTipoDocumento() == null) ? 0 : getTipoDocumento().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PersonaNaturalModel))
			return false;
		PersonaNaturalModel other = (PersonaNaturalModel) obj;
		if (getNumeroDocumento() == null) {
			if (other.getNumeroDocumento() != null)
				return false;
		} else if (!getNumeroDocumento().equals(other.getNumeroDocumento()))
			return false;
		if (getTipoDocumento() == null) {
			if (other.getTipoDocumento() != null)
				return false;
		} else if (!getTipoDocumento().equals(other.getTipoDocumento()))
			return false;
		return true;
	}

}
