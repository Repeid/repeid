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

    protected PersonaJuridicaEntity personaJuridicaEntity;
    protected EntityManager em;

    public PersonaJuridicaAdapter(EntityManager em, PersonaJuridicaEntity personaJuridicaEntity) {
        this.em = em;
        this.personaJuridicaEntity = personaJuridicaEntity;
    }

    public PersonaJuridicaEntity getPersonaJuridicaEntity() {
        return personaJuridicaEntity;
    }

    @Override
    public String getId() {
        return personaJuridicaEntity.getId();
    }

    @Override
    public PersonaNaturalModel getRepresentanteLegal() {
        return new PersonaNaturalAdapter(em, personaJuridicaEntity.getRepresentanteLegal());
    }

    @Override
    public void setRepresentanteLegal(PersonaNaturalModel representanteLegal) {
        PersonaNaturalEntity personaNaturalEntity = PersonaNaturalAdapter
                .toPersonaNaturalEntity(representanteLegal, em);
        personaJuridicaEntity.setRepresentanteLegal(personaNaturalEntity);
    }

    @Override
    public List<AccionistaModel> getAccionistas() {
        Set<AccionistaEntity> list = personaJuridicaEntity.getAccionistas();
        List<AccionistaModel> result = new ArrayList<AccionistaModel>();
        for (AccionistaEntity entity : list) {
            result.add(new AccionistaAdapter(em, entity));
        }
        return result;
    }

    @Override
    public String getCodigoPais() {
        return personaJuridicaEntity.getCodigoPais();
    }

    @Override
    public void setCodigoPais(String codigoPais) {
        personaJuridicaEntity.setCodigoPais(codigoPais);
    }

    @Override
    public TipoDocumentoModel getTipoDocumento() {
        return new TipoDocumentoAdapter(em, personaJuridicaEntity.getTipoDocumento());
    }

    @Override
    public String getNumeroDocumento() {
        return personaJuridicaEntity.getNumeroDocumento();
    }

    @Override
    public String getRazonSocial() {
        return personaJuridicaEntity.getRazonSocial();
    }

    @Override
    public void setRazonSocial(String razonSocial) {
        personaJuridicaEntity.setRazonSocial(razonSocial);
    }

    @Override
    public String getNombreComercial() {
        return personaJuridicaEntity.getNombreComercial();
    }

    @Override
    public void setNombreComercial(String nombreComercial) {
        personaJuridicaEntity.setNombreComercial(nombreComercial);
    }

    @Override
    public Date getFechaConstitucion() {
        return personaJuridicaEntity.getFechaConstitucion();
    }

    @Override
    public void setFechaConstitucion(Date fechaConstitucion) {
        personaJuridicaEntity.setFechaConstitucion(fechaConstitucion);
    }

    @Override
    public String getActividadPrincipal() {
        return personaJuridicaEntity.getActividadPrincipal();
    }

    @Override
    public void setActividadPrincipal(String actividadPrincipal) {
        personaJuridicaEntity.setActividadPrincipal(actividadPrincipal);
    }

    @Override
    public TipoEmpresa getTipoEmpresa() {
        String tipoEmpresa = personaJuridicaEntity.getTipoEmpresa();
        return tipoEmpresa != null ? TipoEmpresa.valueOf(tipoEmpresa) : null;
    }

    @Override
    public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
        if (tipoEmpresa != null) {
            personaJuridicaEntity.setTipoEmpresa(tipoEmpresa.toString());
        } else {
            personaJuridicaEntity.setTipoEmpresa(null);
        }
    }

    @Override
    public boolean isFinLucro() {
        return personaJuridicaEntity.isFinLucro();
    }

    @Override
    public void setFinLucro(boolean finLucro) {
        personaJuridicaEntity.setFinLucro(finLucro);
    }

    @Override
    public String getUbigeo() {
        return personaJuridicaEntity.getUbigeo();
    }

    @Override
    public void setUbigeo(String ubigeo) {
        personaJuridicaEntity.setUbigeo(ubigeo);
    }

    @Override
    public String getDireccion() {
        return personaJuridicaEntity.getDireccion();
    }

    @Override
    public void setDireccion(String direccion) {
        personaJuridicaEntity.setDireccion(direccion);
    }

    @Override
    public String getReferencia() {
        return personaJuridicaEntity.getReferencia();
    }

    @Override
    public void setReferencia(String referencia) {
        personaJuridicaEntity.setReferencia(referencia);
    }

    @Override
    public String getTelefono() {
        return personaJuridicaEntity.getTelefono();
    }

    @Override
    public void setTelefono(String telefono) {
        personaJuridicaEntity.setTelefono(telefono);
    }

    @Override
    public String getCelular() {
        return personaJuridicaEntity.getCelular();
    }

    @Override
    public void setCelular(String celular) {
        personaJuridicaEntity.setCelular(celular);
    }

    @Override
    public String getEmail() {
        return personaJuridicaEntity.getEmail();
    }

    @Override
    public void setEmail(String email) {
        personaJuridicaEntity.setEmail(email);
    }

    public static PersonaJuridicaEntity toPersonaJuridicaEntity(PersonaJuridicaModel model,
            EntityManager em) {
        if (model instanceof PersonaJuridicaAdapter) {
            return ((PersonaJuridicaAdapter) model).getPersonaJuridicaEntity();
        }
        return em.getReference(PersonaJuridicaEntity.class, model.getId());
    }

    @Override
    public void commit() {
        em.merge(personaJuridicaEntity);
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
