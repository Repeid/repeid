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
package org.repeid.manager.api.rest.bussiness.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.repeid.manager.api.beans.representations.AccionistaRepresentation;
import org.repeid.manager.api.model.AccionistaModel;
import org.repeid.manager.api.model.AccionistaProvider;
import org.repeid.manager.api.model.utils.ModelToRepresentation;
import org.repeid.manager.api.rest.bussiness.AccionistaResource;

@Stateless
public class AccionistaResourceImpl implements AccionistaResource {

    @PathParam("idAccionista")
    private String idAccionista;

    @Inject
    private AccionistaProvider accionistaProvider;

    // @Inject
    // private AccionistaManager accionistaManager;

    private AccionistaModel getAccionistaModel() {
        return accionistaProvider.findById(idAccionista);
    }

    @Override
    public AccionistaRepresentation toRepresentation() {
        AccionistaRepresentation rep = ModelToRepresentation.toRepresentation(getAccionistaModel());
        if (rep != null) {
            return rep;
        } else {
            throw new NotFoundException("Accionista no encontrado");
        }
    }

    @Override
    public void update(AccionistaRepresentation rep) {
        // accionistaManager.update(getAccionistaModel(), rep);
    }

    @Override
    public Response remove() {
        /*
         * AccionistaModel accionistaModel = getAccionistaModel(); if
         * (accionistaModel == null) { throw new NotFoundException(
         * "Accionista no encontrado"); } boolean removed =
         * accionistaProvider.remove(accionistaModel); if (removed) { return
         * Response.noContent().build(); } else { return ErrorResponse.error(
         * "Accionista no pudo ser eliminado", Response.Status.BAD_REQUEST); }
         */
        return null;
    }

}
