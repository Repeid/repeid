package org.repeid.manager.api.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.model.enums.TipoEmpresa;
import org.repeid.manager.api.model.provider.Provider;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;

@Local
public interface PersonaJuridicaProvider extends Provider {

    PersonaJuridicaModel findById(String id) throws StorageException;

    PersonaJuridicaModel findByTipoNumeroDocumento(TipoDocumentoModel tipoDocumento, String numeroDocumento)
            throws StorageException;

    PersonaJuridicaModel create(PersonaNaturalModel representanteLegal, String codigoPais,
            TipoDocumentoModel tipoDocumentoModel, String numeroDocumento, String razonSocial,
            Date fechaConstitucion, TipoEmpresa tipoEmpresa, boolean finLucro) throws StorageException;

    boolean remove(PersonaJuridicaModel personaJuridicaModel) throws StorageException;

    List<PersonaJuridicaModel> getAll() throws StorageException;

    List<PersonaJuridicaModel> getAll(int firstResult, int maxResults) throws StorageException;

    List<PersonaJuridicaModel> search(String filterText) throws StorageException;

    List<PersonaJuridicaModel> search(String filterText, int firstResult, int maxResults)
            throws StorageException;

    List<PersonaJuridicaModel> searchByAttributes(Map<String, String> attributes) throws StorageException;

    List<PersonaJuridicaModel> searchByAttributes(Map<String, String> attributes, int firstResult,
            int maxResults) throws StorageException;

    SearchResultsModel<PersonaJuridicaModel> search(SearchCriteriaModel criteria) throws StorageException;

    SearchResultsModel<PersonaJuridicaModel> search(SearchCriteriaModel criteria, String filterText)
            throws StorageException;

}
