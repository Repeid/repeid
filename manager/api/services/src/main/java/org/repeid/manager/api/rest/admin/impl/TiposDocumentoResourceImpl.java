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
package org.repeid.manager.api.rest.admin.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.repeid.manager.api.beans.representations.TipoDocumentoRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchCriteriaRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchResultsRepresentation;
import org.repeid.manager.api.rest.admin.TipoDocumentoResource;
import org.repeid.manager.api.rest.admin.TiposDocumentoResource;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

public class TiposDocumentoResourceImpl implements TiposDocumentoResource {

    @Override
    public TipoDocumentoResource tipoDocumento(String tipoDocumentoId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Response create(TipoDocumentoRepresentation rep) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TipoDocumentoRepresentation> search(String abreviatura, String denominacion,
            String tipoPersona, Boolean estado, String filterText, Integer firstResult, Integer maxResults) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SearchResultsRepresentation<TipoDocumentoRepresentation> search(
            SearchCriteriaRepresentation criteria) {
        // TODO Auto-generated method stub
        return null;
    }

	

}
