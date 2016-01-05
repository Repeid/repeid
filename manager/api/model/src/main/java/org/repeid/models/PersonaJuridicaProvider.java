package org.repeid.models;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.repeid.models.enums.TipoEmpresa;
import org.repeid.models.search.SearchCriteriaModel;
import org.repeid.models.search.SearchResultsModel;
import org.repeid.provider.Provider;

import io.apiman.manager.api.core.exceptions.StorageException;

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
