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
package org.repeid.manager.api.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.repeid.manager.api.model.enums.Sexo;
import org.repeid.manager.api.model.provider.Provider;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

public interface PersonaNaturalProvider extends Provider {

	PersonaNaturalModel findById(String id);

	PersonaNaturalModel findByTipoNumeroDocumento(TipoDocumentoModel tipoDocumento, String numeroDocumento);

	PersonaNaturalModel create(String codigoPais, TipoDocumentoModel tipoDocumento, String numeroDocumento,
			String apellidoPaterno, String apellidoMaterno, String nombres, Date fechaNacimiento, Sexo sexo);

	boolean remove(PersonaNaturalModel personaNatural);

	List<PersonaNaturalModel> getAll();

	List<PersonaNaturalModel> getAll(int firstResult, int maxResults);

	List<PersonaNaturalModel> search(String filterText);

	List<PersonaNaturalModel> search(String filterText, int firstResult, int maxResults);

	List<PersonaNaturalModel> searchByAttributes(Map<String, String> attributes);

	List<PersonaNaturalModel> searchByAttributes(Map<String, String> attributes, int firstResult, int maxResults);

	SearchResultsModel<PersonaNaturalModel> search(SearchCriteriaModel criteria);

	SearchResultsModel<PersonaNaturalModel> search(SearchCriteriaModel criteria, String filterText);

}
