package org.repeid.models;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.repeid.models.enums.TipoPersona;
import org.repeid.models.search.SearchCriteriaModel;
import org.repeid.models.search.SearchResultsModel;
import org.repeid.provider.Provider;

@Local
public interface TipoDocumentoProvider extends Provider {

    TipoDocumentoModel findById(String id);

    TipoDocumentoModel findByAbreviatura(String abreviatura);

    TipoDocumentoModel create(String abreviatura, String denominacion, int cantidadCaracteres,
            TipoPersona tipoPersona);

    boolean remove(TipoDocumentoModel tipoDocumentoModel);

    List<TipoDocumentoModel> getAll();

    List<TipoDocumentoModel> getAll(int firstResult, int maxResults);

    List<TipoDocumentoModel> search(String filterText);

    List<TipoDocumentoModel> search(String filterText, int firstResult, int maxResults);

    List<TipoDocumentoModel> searchByAttributes(Map<String, Object> attributes);

    List<TipoDocumentoModel> searchByAttributes(Map<String, Object> attributes, int firstResult,
            int maxResults);

    SearchResultsModel<TipoDocumentoModel> search(SearchCriteriaModel criteria);

    SearchResultsModel<TipoDocumentoModel> search(SearchCriteriaModel criteria, String filterText);

}
