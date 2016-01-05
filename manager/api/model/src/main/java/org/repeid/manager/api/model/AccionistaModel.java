package org.repeid.manager.api.model;

import java.math.BigDecimal;

public interface AccionistaModel extends Model {

    String getId();

    PersonaNaturalModel getPersonaNatural();

    PersonaJuridicaModel getPersonaJuridica();

    BigDecimal getPorcentajeParticipacion();

    void setPorcentajeParticipacion(BigDecimal porcentajeParticipacion);

}