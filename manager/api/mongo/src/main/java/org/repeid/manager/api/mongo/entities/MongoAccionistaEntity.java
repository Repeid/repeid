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
package org.repeid.manager.api.mongo.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

@Entity
@Table(name = "ACCIONISTA")
@NamedQueries(value = {
        @NamedQuery(name = "MongoAccionistaEntity.findAll", query = "SELECT a FROM MongoAccionistaEntity a"),
        @NamedQuery(name = "MongoAccionistaEntity.findByIdPersonaNatural", query = "SELECT a FROM MongoAccionistaEntity a INNER JOIN a.personaNatural p WHERE p.id = :idPersonaNatural"),
        @NamedQuery(name = "MongoAccionistaEntity.findByIdPersonaJuridicaNatural", query = "SELECT a FROM MongoAccionistaEntity a INNER JOIN a.personaJuridica pj INNER JOIN a.personaNatural pn WHERE pj.id = :idPersonaJuridica AND pn.id = :idPersonaNatural") })
public class MongoAccionistaEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @NotNull
    @NaturalId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_natural_id", foreignKey = @ForeignKey )
    private MongoPersonaNaturalEntity personaNatural;

    @NotNull
    @NaturalId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_juridica_id", foreignKey = @ForeignKey )
    private MongoPersonaJuridicaEntity personaJuridica;

    @NotNull
    @Min(value = 1)
    @Max(value = 100)
    @Digits(integer = 3, fraction = 2)
    @Column(name = "porcentaje_participacion")
    private BigDecimal porcentajeParticipacion;

    @Version
    private Integer optlk;

    public MongoAccionistaEntity() {
        // TODO Auto-generated constructor stub
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MongoPersonaNaturalEntity getPersonaNatural() {
        return personaNatural;
    }

    public void setPersonaNatural(MongoPersonaNaturalEntity personaNatural) {
        this.personaNatural = personaNatural;
    }

    public MongoPersonaJuridicaEntity getPersonaJuridica() {
        return personaJuridica;
    }

    public void setPersonaJuridica(MongoPersonaJuridicaEntity personaJuridica) {
        this.personaJuridica = personaJuridica;
    }

    public BigDecimal getPorcentajeParticipacion() {
        return porcentajeParticipacion;
    }

    public void setPorcentajeParticipacion(BigDecimal porcentajeParticipacion) {
        this.porcentajeParticipacion = porcentajeParticipacion;
    }

    public Integer getOptlk() {
        return optlk;
    }

    public void setOptlk(Integer optlk) {
        this.optlk = optlk;
    }

    @Override
    public String toString() {
        return "(AccionistaEntity id=" + this.id + " personaJuridica=" + this.personaJuridica.getId()
                + " personaNatural" + this.personaNatural.getId() + ")";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MongoAccionistaEntity other = (MongoAccionistaEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
