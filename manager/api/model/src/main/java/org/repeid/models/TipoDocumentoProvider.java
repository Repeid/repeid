package org.repeid.models;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.repeid.models.enums.TipoPersona;
import org.repeid.models.search.SearchCriteriaModel;
import org.repeid.models.search.SearchResultsModel;
import org.repeid.provider.Provider;

import io.apiman.manager.api.core.exceptions.StorageException;

@Local
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
