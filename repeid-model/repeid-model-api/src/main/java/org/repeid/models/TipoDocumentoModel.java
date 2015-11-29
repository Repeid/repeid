package org.repeid.models;

import org.repeid.models.enums.TipoPersona;

public interface TipoDocumentoModel extends Model {

	String ABREVIATURA = "abreviatura";
	String DENOMINACION = "denominacion";
	String TIPO_PERSONA = "tipoPersona";
	String ESTADO = "estado";

	String getId();

	String getAbreviatura();

	void setAbreviatura(String abreviatura);

	String getDenominacion();

	void setDenominacion(String denominacion);

	int getCantidadCaracteres();

	void setCantidadCaracteres(int cantidadCaracteres);

	TipoPersona getTipoPersona();

	void setTipoPersona(TipoPersona tipoPersona);

	boolean getEstado();

	void setEstado(boolean estado);

}