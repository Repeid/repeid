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

import javax.persistence.EntityManager;

import org.repeid.manager.api.jpa.entities.TipoDocumentoEntity;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.enums.TipoPersona;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public class TipoDocumentoAdapter implements TipoDocumentoModel {

	private TipoDocumentoEntity tipoDocumento;

	private EntityManager em;

	public TipoDocumentoAdapter(EntityManager em, TipoDocumentoEntity tipoDocumentoEntity) {
		this.em = em;
		this.tipoDocumento = tipoDocumentoEntity;
	}

	public static TipoDocumentoEntity toTipoDocumentoEntity(TipoDocumentoModel model, EntityManager em) {
		if (model instanceof TipoDocumentoAdapter) {
			return ((TipoDocumentoAdapter) model).getTipoDocumentEntity();
		}
		return em.getReference(TipoDocumentoEntity.class, model.getId());
	}

	public TipoDocumentoEntity getTipoDocumentEntity() {
		return tipoDocumento;
	}

	@Override
	public void commit() {
		em.merge(tipoDocumento);
	}

	@Override
	public String getId() {
		return tipoDocumento.getId();
	}

	@Override
	public String getAbreviatura() {
		return tipoDocumento.getAbreviatura();
	}

	@Override
	public void setAbreviatura(String abreviatura) {
		tipoDocumento.setAbreviatura(abreviatura);
	}

	@Override
	public String getDenominacion() {
		return tipoDocumento.getDenominacion();
	}

	@Override
	public void setDenominacion(String denominacion) {
		tipoDocumento.setDenominacion(denominacion);
	}

	@Override
	public int getCantidadCaracteres() {
		return tipoDocumento.getCantidadCaracteres();
	}

	@Override
	public void setCantidadCaracteres(int cantidadCaracteres) {
		tipoDocumento.setCantidadCaracteres(cantidadCaracteres);
	}

	@Override
	public TipoPersona getTipoPersona() {
		String tipoPersona = tipoDocumento.getTipoPersona();
		return tipoPersona != null ? TipoPersona.valueOf(tipoPersona) : null;
	}

	@Override
	public void setTipoPersona(TipoPersona tipoPersona) {
		if (tipoPersona != null) {
			tipoDocumento.setTipoPersona(tipoPersona.toString());
		} else {
			tipoDocumento.setTipoPersona(null);
		}
	}

	@Override
	public boolean getEstado() {
		return tipoDocumento.isEstado();
	}

	@Override
	public void setEstado(boolean estado) {
		tipoDocumento.setEstado(estado);
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
