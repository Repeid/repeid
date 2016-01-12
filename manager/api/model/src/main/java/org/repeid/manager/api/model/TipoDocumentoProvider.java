package org.repeid.manager.api.model;

import java.util.List;
import java.util.Map;

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.api.model.provider.Provider;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;

public interface TipoDocumentoProvider extends Provider {

	TipoDocumentoModel findById(String id) throws StorageException;

	TipoDocumentoModel findByAbreviatura(String abreviatura) throws StorageException;

	TipoDocumentoModel create(String abreviatura, String denominacion, int cantidadCaracteres, TipoPersona tipoPersona)
			throws StorageException;

	boolean remove(TipoDocumentoModel tipoDocumentoModel) throws StorageException;

	List<TipoDocumentoModel> getAll() throws StorageException;

	List<TipoDocumentoModel> getAll(int firstResult, int maxResults) throws StorageException;

	List<TipoDocumentoModel> search(String filterText) throws StorageException;

	List<TipoDocumentoModel> search(String filterText, int firstResult, int maxResults) throws StorageException;

	List<TipoDocumentoModel> searchByAttributes(Map<String, Object> attributes) throws StorageException;

	List<TipoDocumentoModel> searchByAttributes(Map<String, Object> attributes, int firstResult, int maxResults)
			throws StorageException;

	SearchResultsModel<TipoDocumentoModel> search(SearchCriteriaModel criteria) throws StorageException;

	SearchResultsModel<TipoDocumentoModel> search(SearchCriteriaModel criteria, String filterText)
			throws StorageException;

}
