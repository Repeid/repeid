package org.repeid.manager.api.jpa.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Alternative;
import javax.inject.Named;
import javax.persistence.TypedQuery;

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.jpa.entities.PersonaJuridicaEntity;
import org.repeid.manager.api.jpa.entities.PersonaNaturalEntity;
import org.repeid.manager.api.jpa.entities.TipoDocumentoEntity;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.TipoDocumentoProvider;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;

import io.apiman.manager.api.jpa.AbstractStorage;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */

@Named
@Stateless
@Alternative
@Local(TipoDocumentoProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaTipoDocumentoProvider extends AbstractStorage implements TipoDocumentoProvider {

    private static final String ABREVIATURA = "abreviatura";
    private static final String DENOMINACION = "denominacion";
    private static final String TIPO_PERSONA = "tipoPersona";
    private static final String ESTADO = "estado";

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }

    @Override
    public TipoDocumentoModel create(String abreviatura, String denominacion, int cantidadCaracteres,
            TipoPersona tipoPersona) throws StorageException {
        if (findByAbreviatura(abreviatura) != null) {
            throw new ModelDuplicateException(
                    "TipoDocumentoEntity abreviatura debe ser unico, se encontro otra entidad con abreviatura:"
                            + abreviatura);
        }

        TipoDocumentoEntity tipoDocumentoEntity = new TipoDocumentoEntity();
        tipoDocumentoEntity.setAbreviatura(abreviatura);
        tipoDocumentoEntity.setDenominacion(denominacion);
        tipoDocumentoEntity.setCantidadCaracteres(cantidadCaracteres);
        tipoDocumentoEntity.setTipoPersona(tipoPersona.toString());
        tipoDocumentoEntity.setEstado(true);
        create(tipoDocumentoEntity);
        return new TipoDocumentoAdapter(getEntityManager(), tipoDocumentoEntity);
    }

    @Override
    public TipoDocumentoModel findByAbreviatura(String abreviatura) throws StorageException {
        TypedQuery<TipoDocumentoEntity> query = getEntityManager()
                .createNamedQuery("TipoDocumentoEntity.findByAbreviatura", TipoDocumentoEntity.class);
        query.setParameter("abreviatura", abreviatura);
        List<TipoDocumentoEntity> results = executeTypedQuery(query);
        if (results.isEmpty()) {
            return null;
        } else if (results.size() > 1) {
            throw new IllegalStateException(
                    "Mas de un TipoDocumentoEntity con abreviatura=" + abreviatura + ", results=" + results);
        } else {
            return new TipoDocumentoAdapter(getEntityManager(), results.get(0));
        }
    }

    @Override
    public TipoDocumentoModel findById(String id) throws StorageException {
        TipoDocumentoEntity entity = get(id, TipoDocumentoEntity.class);
        return entity != null ? new TipoDocumentoAdapter(getEntityManager(), entity) : null;
    }

    @Override
    public boolean remove(TipoDocumentoModel tipoDocumentoModel) throws StorageException {
        TypedQuery<PersonaNaturalEntity> query1 = getEntityManager()
                .createNamedQuery("PersonaNaturalEntity.findByTipoDocumento", PersonaNaturalEntity.class);
        query1.setParameter("tipoDocumento", tipoDocumentoModel.getAbreviatura());
        query1.setMaxResults(1);
        if (!executeTypedQuery(query1).isEmpty()) {
            return false;
        }

        TypedQuery<PersonaJuridicaEntity> query2 = getEntityManager()
                .createNamedQuery("PersonaJuridicaEntity.findByTipoDocumento", PersonaJuridicaEntity.class);
        query2.setParameter("tipoDocumento", tipoDocumentoModel.getAbreviatura());
        query2.setMaxResults(1);
        if (!executeTypedQuery(query2).isEmpty()) {
            return false;
        }

        TipoDocumentoEntity tipoDocumentoEntity = get(tipoDocumentoModel.getId(), TipoDocumentoEntity.class);
        if (tipoDocumentoEntity == null) {
            return false;
        }
        delete(tipoDocumentoEntity);
        return true;
    }

    @Override
    public List<TipoDocumentoModel> getAll() throws StorageException {
        return getAll(-1, -1);
    }

    @Override
    public List<TipoDocumentoModel> getAll(int firstResult, int maxResults) throws StorageException {
        TypedQuery<TipoDocumentoEntity> query = getEntityManager()
                .createNamedQuery("TipoDocumentoEntity.findAll", TipoDocumentoEntity.class);
        if (firstResult != -1) {
            query.setFirstResult(firstResult);
        }
        if (maxResults != -1) {
            query.setMaxResults(maxResults);
        }
        List<TipoDocumentoEntity> entities = query.getResultList();
        List<TipoDocumentoModel> models = new ArrayList<TipoDocumentoModel>();
        for (TipoDocumentoEntity tipoDocumentoEntity : entities) {
            models.add(new TipoDocumentoAdapter(getEntityManager(), tipoDocumentoEntity));
        }
        return models;
    }

    @Override
    public List<TipoDocumentoModel> search(String filterText) throws StorageException {
        return search(filterText, -1, -1);
    }

    @Override
    public List<TipoDocumentoModel> search(String filterText, int firstResult, int maxResults)
            throws StorageException {
        TypedQuery<TipoDocumentoEntity> query = getEntityManager()
                .createNamedQuery("TipoDocumentoEntity.findByFilterText", TipoDocumentoEntity.class);
        query.setParameter("filterText", "%" + filterText.toLowerCase() + "%");
        if (firstResult != -1) {
            query.setFirstResult(firstResult);
        }
        if (maxResults != -1) {
            query.setMaxResults(maxResults);
        }
        List<TipoDocumentoEntity> entities = executeTypedQuery(query);
        List<TipoDocumentoModel> models = new ArrayList<TipoDocumentoModel>();
        for (TipoDocumentoEntity tipoDocumentoEntity : entities) {
            models.add(new TipoDocumentoAdapter(getEntityManager(), tipoDocumentoEntity));
        }

        return models;
    }

    @Override
    public List<TipoDocumentoModel> searchByAttributes(Map<String, Object> attributes)
            throws StorageException {
        return searchByAttributes(attributes, -1, -1);
    }

    @Override
    public List<TipoDocumentoModel> searchByAttributes(Map<String, Object> attributes, int firstResult,
            int maxResults) throws StorageException {
        StringBuilder builder = new StringBuilder("SELECT t FROM TipoDocumentoEntity");
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            String attribute = null;
            String parameterName = null;
            if (entry.getKey().equals(TipoDocumentoModel.ABREVIATURA)) {
                attribute = "lower(t.abreviatura)";
                parameterName = JpaTipoDocumentoProvider.ABREVIATURA;
            } else if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.DENOMINACION)) {
                attribute = "lower(t.denominacion)";
                parameterName = JpaTipoDocumentoProvider.DENOMINACION;
            }

            if (attribute != null) {
                builder.append(" and ");
                builder.append(attribute).append(" like :").append(parameterName);
            } else {
                if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.TIPO_PERSONA)) {
                    attribute = "lower(t.tipoPersona)";
                    parameterName = JpaTipoDocumentoProvider.TIPO_PERSONA;
                } else if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.ESTADO)) {
                    attribute = "t.estado";
                    parameterName = JpaTipoDocumentoProvider.ESTADO;
                }

                if (attribute == null) {
                    continue;
                }
                builder.append(" and ");
                builder.append(attribute).append(" = :").append(parameterName);
            }
        }
        builder.append(" order by t.abreviatura");
        String q = builder.toString();
        TypedQuery<TipoDocumentoEntity> query = getEntityManager().createQuery(q, TipoDocumentoEntity.class);
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            String parameterName = null;
            if (entry.getKey().equals(TipoDocumentoModel.ABREVIATURA)) {
                parameterName = JpaTipoDocumentoProvider.ABREVIATURA;
            } else if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.DENOMINACION)) {
                parameterName = JpaTipoDocumentoProvider.DENOMINACION;
            }

            if (parameterName != null) {
                query.setParameter(parameterName, "%" + String.valueOf(entry.getValue()).toLowerCase() + "%");
            } else {
                if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.TIPO_PERSONA)) {
                    parameterName = JpaTipoDocumentoProvider.TIPO_PERSONA;
                } else if (entry.getKey().equalsIgnoreCase(TipoDocumentoModel.ESTADO)) {
                    parameterName = JpaTipoDocumentoProvider.ESTADO;
                }

                if (parameterName == null) {
                    continue;
                }
                query.setParameter(parameterName, entry.getValue());
            }
        }
        if (firstResult != -1) {
            query.setFirstResult(firstResult);
        }
        if (maxResults != -1) {
            query.setMaxResults(maxResults);
        }
        List<TipoDocumentoEntity> results = query.getResultList();
        List<TipoDocumentoModel> tipoDocumentos = new ArrayList<TipoDocumentoModel>();
        for (TipoDocumentoEntity entity : results)
            tipoDocumentos.add(new TipoDocumentoAdapter(getEntityManager(), entity));
        return tipoDocumentos;
    }

    @Override
    public SearchResultsModel<TipoDocumentoModel> search(SearchCriteriaModel criteria)
            throws StorageException {
        SearchResultsModel<TipoDocumentoEntity> entityResult = find(criteria, TipoDocumentoEntity.class);

        SearchResultsModel<TipoDocumentoModel> modelResult = new SearchResultsModel<>();
        List<TipoDocumentoModel> list = new ArrayList<>();
        for (TipoDocumentoEntity entity : entityResult.getModels()) {
            list.add(new TipoDocumentoAdapter(getEntityManager(), entity));
        }
        modelResult.setTotalSize(entityResult.getTotalSize());
        modelResult.setModels(list);
        return modelResult;
    }

    @Override
    public SearchResultsModel<TipoDocumentoModel> search(SearchCriteriaModel criteria, String filterText)
            throws StorageException {
        SearchResultsModel<TipoDocumentoEntity> entityResult = findFullText(criteria,
                TipoDocumentoEntity.class, filterText, "abreviatura", "denominacion");

        SearchResultsModel<TipoDocumentoModel> modelResult = new SearchResultsModel<>();
        List<TipoDocumentoModel> list = new ArrayList<>();
        for (TipoDocumentoEntity entity : entityResult.getModels()) {
            list.add(new TipoDocumentoAdapter(getEntityManager(), entity));
        }
        modelResult.setTotalSize(entityResult.getTotalSize());
        modelResult.setModels(list);
        return modelResult;
    }

}
