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

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.model.enums.TipoEmpresa;
import org.repeid.manager.api.model.provider.Provider;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public interface PersonaJuridicaProvider extends Provider {

	PersonaJuridicaModel findById(String id) throws StorageException;

	PersonaJuridicaModel findByTipoNumeroDocumento(TipoDocumentoModel tipoDocumento, String numeroDocumento)
			throws StorageException;

	PersonaJuridicaModel create(PersonaNaturalModel representanteLegal, String codigoPais,
			TipoDocumentoModel tipoDocumentoModel, String numeroDocumento, String razonSocial, Date fechaConstitucion,
			TipoEmpresa tipoEmpresa, boolean finLucro) throws StorageException;

	boolean remove(PersonaJuridicaModel personaJuridicaModel) throws StorageException;

	List<PersonaJuridicaModel> getAll() throws StorageException;

	List<PersonaJuridicaModel> getAll(int firstResult, int maxResults) throws StorageException;

	List<PersonaJuridicaModel> search(String filterText) throws StorageException;

	List<PersonaJuridicaModel> search(String filterText, int firstResult, int maxResults) throws StorageException;

	List<PersonaJuridicaModel> searchByAttributes(Map<String, String> attributes) throws StorageException;

	List<PersonaJuridicaModel> searchByAttributes(Map<String, String> attributes, int firstResult, int maxResults)
			throws StorageException;

	SearchResultsModel<PersonaJuridicaModel> search(SearchCriteriaModel criteria) throws StorageException;

	SearchResultsModel<PersonaJuridicaModel> search(SearchCriteriaModel criteria, String filterText)
			throws StorageException;

}
