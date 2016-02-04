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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.repeid.manager.api.jpa.entities.AccionistaEntity;
import org.repeid.manager.api.jpa.entities.PersonaJuridicaEntity;
import org.repeid.manager.api.jpa.entities.PersonaNaturalEntity;
import org.repeid.manager.api.model.AccionistaModel;
import org.repeid.manager.api.model.PersonaJuridicaModel;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.enums.TipoEmpresa;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public class PersonaJuridicaAdapter implements PersonaJuridicaModel {

	private PersonaJuridicaEntity personaJuridica;

	private EntityManager em;

	public PersonaJuridicaAdapter(EntityManager em, PersonaJuridicaEntity personaJuridicaEntity) {
		this.em = em;
		this.personaJuridica = personaJuridicaEntity;
	}

	public PersonaJuridicaEntity getPersonaJuridicaEntity() {
		return personaJuridica;
	}

	@Override
	public String getId() {
		return personaJuridica.getId();
	}

	@Override
	public PersonaNaturalModel getRepresentanteLegal() {
		return new PersonaNaturalAdapter(em, personaJuridica.getRepresentanteLegal());
	}

	@Override
	public void setRepresentanteLegal(PersonaNaturalModel representanteLegal) {
		PersonaNaturalEntity personaNaturalEntity = PersonaNaturalAdapter.toPersonaNaturalEntity(representanteLegal,
				em);
		personaJuridica.setRepresentanteLegal(personaNaturalEntity);
	}

	@Override
	public List<AccionistaModel> getAccionistas() {
		Set<AccionistaEntity> list = personaJuridica.getAccionistas();
		List<AccionistaModel> result = new ArrayList<AccionistaModel>();
		for (AccionistaEntity entity : list) {
			result.add(new AccionistaAdapter(em, entity));
		}
		return result;
	}

	@Override
	public String getCodigoPais() {
		return personaJuridica.getCodigoPais();
	}

	@Override
	public void setCodigoPais(String codigoPais) {
		personaJuridica.setCodigoPais(codigoPais);
	}

	@Override
	public TipoDocumentoModel getTipoDocumento() {
		return new TipoDocumentoAdapter(em, personaJuridica.getTipoDocumento());
	}

	@Override
	public String getNumeroDocumento() {
		return personaJuridica.getNumeroDocumento();
	}

	@Override
	public String getRazonSocial() {
		return personaJuridica.getRazonSocial();
	}

	@Override
	public void setRazonSocial(String razonSocial) {
		personaJuridica.setRazonSocial(razonSocial);
	}

	@Override
	public String getNombreComercial() {
		return personaJuridica.getNombreComercial();
	}

	@Override
	public void setNombreComercial(String nombreComercial) {
		personaJuridica.setNombreComercial(nombreComercial);
	}

	@Override
	public Date getFechaConstitucion() {
		return personaJuridica.getFechaConstitucion();
	}

	@Override
	public void setFechaConstitucion(Date fechaConstitucion) {
		personaJuridica.setFechaConstitucion(fechaConstitucion);
	}

	@Override
	public String getActividadPrincipal() {
		return personaJuridica.getActividadPrincipal();
	}

	@Override
	public void setActividadPrincipal(String actividadPrincipal) {
		personaJuridica.setActividadPrincipal(actividadPrincipal);
	}

	@Override
	public TipoEmpresa getTipoEmpresa() {
		String tipoEmpresa = personaJuridica.getTipoEmpresa();
		return tipoEmpresa != null ? TipoEmpresa.valueOf(tipoEmpresa) : null;
	}

	@Override
	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		if (tipoEmpresa != null) {
			personaJuridica.setTipoEmpresa(tipoEmpresa.toString());
		} else {
			personaJuridica.setTipoEmpresa(null);
		}
	}

	@Override
	public boolean isFinLucro() {
		return personaJuridica.isFinLucro();
	}

	@Override
	public void setFinLucro(boolean finLucro) {
		personaJuridica.setFinLucro(finLucro);
	}

	@Override
	public String getUbigeo() {
		return personaJuridica.getUbigeo();
	}

	@Override
	public void setUbigeo(String ubigeo) {
		personaJuridica.setUbigeo(ubigeo);
	}

	@Override
	public String getDireccion() {
		return personaJuridica.getDireccion();
	}

	@Override
	public void setDireccion(String direccion) {
		personaJuridica.setDireccion(direccion);
	}

	@Override
	public String getReferencia() {
		return personaJuridica.getReferencia();
	}

	@Override
	public void setReferencia(String referencia) {
		personaJuridica.setReferencia(referencia);
	}

	@Override
	public String getTelefono() {
		return personaJuridica.getTelefono();
	}

	@Override
	public void setTelefono(String telefono) {
		personaJuridica.setTelefono(telefono);
	}

	@Override
	public String getCelular() {
		return personaJuridica.getCelular();
	}

	@Override
	public void setCelular(String celular) {
		personaJuridica.setCelular(celular);
	}

	@Override
	public String getEmail() {
		return personaJuridica.getEmail();
	}

	@Override
	public void setEmail(String email) {
		personaJuridica.setEmail(email);
	}

	public static PersonaJuridicaEntity toPersonaJuridicaEntity(PersonaJuridicaModel model, EntityManager em) {
		if (model instanceof PersonaJuridicaAdapter) {
			return ((PersonaJuridicaAdapter) model).getPersonaJuridicaEntity();
		}
		return em.getReference(PersonaJuridicaEntity.class, model.getId());
	}

	@Override
	public void commit() {
		em.merge(personaJuridica);
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
		if (!(obj instanceof PersonaJuridicaModel))
			return false;
		PersonaJuridicaModel other = (PersonaJuridicaModel) obj;
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
