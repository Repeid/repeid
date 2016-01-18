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
package org.repeid.manager.api.mongo.models;

import javax.persistence.EntityManager;

import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.api.mongo.entities.MongoTipoDocumentoEntity;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */

public class TipoDocumentoAdapter implements TipoDocumentoModel {

	private MongoTipoDocumentoEntity tipoDocumentoEntity;
	private EntityManager em;

	public TipoDocumentoAdapter(EntityManager em, MongoTipoDocumentoEntity tipoDocumentoEntity) {
		this.em = em;
		this.tipoDocumentoEntity = tipoDocumentoEntity;
	}

	public static MongoTipoDocumentoEntity toTipoDocumentoEntity(TipoDocumentoModel model, EntityManager em) {
		if (model instanceof TipoDocumentoAdapter) {
			return ((TipoDocumentoAdapter) model).getTipoDocumentEntity();
		}
		return em.getReference(MongoTipoDocumentoEntity.class, model.getId());
	}

	public MongoTipoDocumentoEntity getTipoDocumentEntity() {
		return tipoDocumentoEntity;
	}

	@Override
	public String getId() {
		return tipoDocumentoEntity.getId();
	}

	@Override
	public String getAbreviatura() {
		return tipoDocumentoEntity.getAbreviatura();
	}

	@Override
	public void setAbreviatura(String abreviatura) {
		tipoDocumentoEntity.setAbreviatura(abreviatura);
	}

	@Override
	public String getDenominacion() {
		return tipoDocumentoEntity.getDenominacion();
	}

	@Override
	public void setDenominacion(String denominacion) {
		tipoDocumentoEntity.setDenominacion(denominacion);
	}

	@Override
	public int getCantidadCaracteres() {
		return tipoDocumentoEntity.getCantidadCaracteres();
	}

	@Override
	public void setCantidadCaracteres(int cantidadCaracteres) {
		tipoDocumentoEntity.setCantidadCaracteres(cantidadCaracteres);
	}

	@Override
	public TipoPersona getTipoPersona() {
		String tipoPersona = tipoDocumentoEntity.getTipoPersona();
		return tipoPersona != null ? TipoPersona.valueOf(tipoPersona) : null;
	}

	@Override
	public void setTipoPersona(TipoPersona tipoPersona) {
		if (tipoPersona != null) {
			tipoDocumentoEntity.setTipoPersona(tipoPersona.toString());
		} else {
			tipoDocumentoEntity.setTipoPersona(null);
		}
	}

	@Override
	public boolean getEstado() {
		return tipoDocumentoEntity.isEstado();
	}

	@Override
	public void setEstado(boolean estado) {
		tipoDocumentoEntity.setEstado(estado);
	}

	@Override
	public void commit() {
		em.merge(tipoDocumentoEntity);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getAbreviatura() == null) ? 0 : getAbreviatura().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TipoDocumentoModel))
			return false;
		TipoDocumentoModel other = (TipoDocumentoModel) obj;
		if (getAbreviatura() == null) {
			if (other.getAbreviatura() != null)
				return false;
		} else if (!getAbreviatura().equals(other.getAbreviatura()))
			return false;
		return true;
	}

}
