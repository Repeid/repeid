package org.repeid.manager.api.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.model.enums.Sexo;
import org.repeid.manager.api.model.provider.Provider;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;

public interface PersonaNaturalProvider extends Provider {

	PersonaNaturalModel findById(String id) throws StorageException;

	PersonaNaturalModel findByTipoNumeroDocumento(TipoDocumentoModel tipoDocumento, String numeroDocumento)
			throws StorageException;

	PersonaNaturalModel create(String codigoPais, TipoDocumentoModel tipoDocumentoModel, String numeroDocumento,
			String apellidoPaterno, String apellidoMaterno, String nombres, Date fechaNacimiento, Sexo sexo)
					throws StorageException;

	boolean remove(PersonaNaturalModel personaNatural) throws StorageException;

	List<PersonaNaturalModel> getAll() throws StorageException;

	List<PersonaNaturalModel> getAll(int firstResult, int maxResults) throws StorageException;

	List<PersonaNaturalModel> search(String filterText) throws StorageException;

	List<PersonaNaturalModel> search(String filterText, int firstResult, int maxResults) throws StorageException;

	List<PersonaNaturalModel> searchByAttributes(Map<String, String> attributes) throws StorageException;

	List<PersonaNaturalModel> searchByAttributes(Map<String, String> attributes, int firstResult, int maxResults)
			throws StorageException;

	SearchResultsModel<PersonaNaturalModel> search(SearchCriteriaModel criteria) throws StorageException;

	SearchResultsModel<PersonaNaturalModel> search(SearchCriteriaModel criteria, String filterText)
			throws StorageException;

}
