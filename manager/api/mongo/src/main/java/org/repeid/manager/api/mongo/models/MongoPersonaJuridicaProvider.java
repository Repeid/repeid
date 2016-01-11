package org.repeid.manager.api.mongo.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.repeid.manager.api.model.PersonaJuridicaModel;
import org.repeid.manager.api.model.PersonaJuridicaProvider;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.enums.TipoEmpresa;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;
import org.repeid.manager.api.mongo.entities.PersonaJuridicaEntity;
import org.repeid.manager.api.mongo.entities.PersonaNaturalEntity;
import org.repeid.manager.api.mongo.entities.TipoDocumentoEntity;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

@Named
@Stateless
@Local(PersonaJuridicaProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MongoPersonaJuridicaProvider extends AbstractHibernateStorage implements PersonaJuridicaProvider {

    private final static String RAZON_SOCIAL = "razonSocial";
    private final static String NOMBRE_COMERCIAL = "nombreComercial";
    private final static String NUMERO_DOCUMENTO = "numeroDocumento";

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }

    @Override
    public PersonaJuridicaModel create(PersonaNaturalModel representanteLegal, String codigoPais,
            TipoDocumentoModel tipoDocumentoModel, String numeroDocumento, String razonSocial,
            Date fechaConstitucion, TipoEmpresa tipoEmpresa, boolean finLucro) {
        if (findByTipoNumeroDocumento(tipoDocumentoModel, numeroDocumento) != null) {
            throw new ModelDuplicateException(
                    "PersonaJuridicaEntity tipoDocumento y numeroDocumento debe ser unico, se encontro otra entidad con tipoDocumento="
                            + tipoDocumentoModel + "y numeroDocumento=" + numeroDocumento);
        }

        TipoDocumentoEntity tipoDocumentoEntity = em.find(TipoDocumentoEntity.class,
                tipoDocumentoModel.getId());
        PersonaNaturalEntity personaNaturalEntity = em.find(PersonaNaturalEntity.class,
                representanteLegal.getId());

        PersonaJuridicaEntity personaJuridicaEntity = new PersonaJuridicaEntity();
        personaJuridicaEntity.setRepresentanteLegal(personaNaturalEntity);
        personaJuridicaEntity.setCodigoPais(codigoPais);
        personaJuridicaEntity.setTipoDocumento(tipoDocumentoEntity);
        personaJuridicaEntity.setNumeroDocumento(numeroDocumento);
        personaJuridicaEntity.setRazonSocial(razonSocial);
        personaJuridicaEntity.setFechaConstitucion(fechaConstitucion);
        personaJuridicaEntity.setTipoEmpresa(tipoEmpresa.toString());
        personaJuridicaEntity.setFinLucro(finLucro);

        em.persist(personaJuridicaEntity);
        return new PersonaJuridicaAdapter(em, personaJuridicaEntity);
    }

    @Override
    public boolean remove(PersonaJuridicaModel personaJuridicaModel) {
        PersonaJuridicaEntity personaJuridicaEntity = em.find(PersonaJuridicaEntity.class,
                personaJuridicaModel.getId());
        if (personaJuridicaEntity == null) {
            return false;
        }
        em.remove(personaJuridicaEntity);
        return true;
    }

    @Override
    public PersonaJuridicaModel findById(String id) {
        PersonaJuridicaEntity personaJuridicaEntity = em.find(PersonaJuridicaEntity.class, id);
        return personaJuridicaEntity != null ? new PersonaJuridicaAdapter(em, personaJuridicaEntity) : null;
    }

    @Override
    public PersonaJuridicaModel findByTipoNumeroDocumento(TipoDocumentoModel tipoDocumento,
            String numeroDocumento) {
        TypedQuery<PersonaJuridicaEntity> query = em.createNamedQuery(
                "PersonaJuridicaEntity.findByTipoNumeroDocumento", PersonaJuridicaEntity.class);
        query.setParameter("tipoDocumento", tipoDocumento.getAbreviatura());
        query.setParameter("numeroDocumento", numeroDocumento);
        List<PersonaJuridicaEntity> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        } else if (results.size() > 1) {
            throw new IllegalStateException("Mas de una PersonaJuridicaEntity con tipoDocumento="
                    + tipoDocumento + " y numeroDocumento=" + numeroDocumento + ", results=" + results);
        } else {
            return new PersonaJuridicaAdapter(em, results.get(0));
        }
    }

    @Override
    public List<PersonaJuridicaModel> getAll() {
        return getAll(-1, -1);
    }

    @Override
    public List<PersonaJuridicaModel> getAll(int firstResult, int maxResults) {
        TypedQuery<PersonaJuridicaEntity> query = em.createNamedQuery("PersonaJuridicaEntity.findAll",
                PersonaJuridicaEntity.class);
        if (firstResult != -1) {
            query.setFirstResult(firstResult);
        }
        if (maxResults != -1) {
            query.setMaxResults(maxResults);
        }
        List<PersonaJuridicaEntity> entities = query.getResultList();
        List<PersonaJuridicaModel> models = new ArrayList<PersonaJuridicaModel>();
        for (PersonaJuridicaEntity personaJuridicaEntity : entities) {
            models.add(new PersonaJuridicaAdapter(em, personaJuridicaEntity));
        }
        return models;
    }

    @Override
    public List<PersonaJuridicaModel> search(String filterText) {
        return search(filterText, -1, -1);
    }

    @Override
    public List<PersonaJuridicaModel> search(String filterText, int firstResult, int maxResults) {
        TypedQuery<PersonaJuridicaEntity> query = em
                .createNamedQuery("PersonaJuridicaEntity.findByFilterText", PersonaJuridicaEntity.class);
        query.setParameter("filterText", "%" + filterText.toLowerCase() + "%");
        if (firstResult != -1) {
            query.setFirstResult(firstResult);
        }
        if (maxResults != -1) {
            query.setMaxResults(maxResults);
        }
        List<PersonaJuridicaEntity> entities = query.getResultList();
        List<PersonaJuridicaModel> models = new ArrayList<PersonaJuridicaModel>();
        for (PersonaJuridicaEntity personaJuridicaEntity : entities) {
            models.add(new PersonaJuridicaAdapter(em, personaJuridicaEntity));
        }
        return models;
    }

    @Override
    public List<PersonaJuridicaModel> searchByAttributes(Map<String, String> attributes) {
        return searchByAttributes(attributes, -1, -1);
    }

    @Override
    public List<PersonaJuridicaModel> searchByAttributes(Map<String, String> attributes, int firstResult,
            int maxResults) {
        StringBuilder builder = new StringBuilder("SELECT p FROM PersonaJuridicaEntity");
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String attribute = null;
            String parameterName = null;
            if (entry.getKey().equals(PersonaJuridicaModel.RAZON_SOCIAL)) {
                attribute = "lower(p.razonSocial)";
                parameterName = MongoPersonaJuridicaProvider.RAZON_SOCIAL;
            } else if (entry.getKey().equalsIgnoreCase(PersonaJuridicaModel.NOMBRE_COMERCIAL)) {
                attribute = "lower(p.nombreComercial)";
                parameterName = MongoPersonaJuridicaProvider.NOMBRE_COMERCIAL;
            } else if (entry.getKey().equalsIgnoreCase(PersonaJuridicaModel.NUMERO_DOCUMENTO)) {
                attribute = "lower(p.numeroDocumento)";
                parameterName = MongoPersonaJuridicaProvider.NUMERO_DOCUMENTO;
            }

            if (attribute == null) {
                continue;
            }
            builder.append(attribute).append(" like :").append(parameterName);
        }
        builder.append(" order by p.razonSocial");
        String q = builder.toString();
        TypedQuery<PersonaJuridicaEntity> query = em.createQuery(q, PersonaJuridicaEntity.class);
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String parameterName = null;
            if (entry.getKey().equals(PersonaJuridicaModel.RAZON_SOCIAL)) {
                parameterName = MongoPersonaJuridicaProvider.RAZON_SOCIAL;
            } else if (entry.getKey().equals(PersonaJuridicaModel.NOMBRE_COMERCIAL)) {
                parameterName = MongoPersonaJuridicaProvider.NOMBRE_COMERCIAL;
            } else if (entry.getKey().equals(PersonaJuridicaModel.NUMERO_DOCUMENTO)) {
                parameterName = MongoPersonaJuridicaProvider.NUMERO_DOCUMENTO;
            }

            if (parameterName == null) {
                continue;
            }
            query.setParameter(parameterName, "%" + entry.getValue().toLowerCase() + "%");
        }
        if (firstResult != -1) {
            query.setFirstResult(firstResult);
        }
        if (maxResults != -1) {
            query.setMaxResults(maxResults);
        }
        List<PersonaJuridicaEntity> results = query.getResultList();
        List<PersonaJuridicaModel> personaNaturales = new ArrayList<PersonaJuridicaModel>();
        for (PersonaJuridicaEntity entity : results)
            personaNaturales.add(new PersonaJuridicaAdapter(em, entity));
        return personaNaturales;
    }

    @Override
    public SearchResultsModel<PersonaJuridicaModel> search(SearchCriteriaModel criteria) {
        SearchResultsModel<PersonaJuridicaEntity> entityResult = find(criteria, PersonaJuridicaEntity.class);

        SearchResultsModel<PersonaJuridicaModel> modelResult = new SearchResultsModel<>();
        List<PersonaJuridicaModel> list = new ArrayList<>();
        for (PersonaJuridicaEntity entity : entityResult.getModels()) {
            list.add(new PersonaJuridicaAdapter(em, entity));
        }
        modelResult.setTotalSize(entityResult.getTotalSize());
        modelResult.setModels(list);
        return modelResult;
    }

    @Override
    public SearchResultsModel<PersonaJuridicaModel> search(SearchCriteriaModel criteria, String filterText) {
        SearchResultsModel<PersonaJuridicaEntity> entityResult = findFullText(criteria,
                PersonaJuridicaEntity.class, filterText, "numeroDocumento", "razonSocial");

        SearchResultsModel<PersonaJuridicaModel> modelResult = new SearchResultsModel<>();
        List<PersonaJuridicaModel> list = new ArrayList<>();
        for (PersonaJuridicaEntity entity : entityResult.getModels()) {
            list.add(new PersonaJuridicaAdapter(em, entity));
        }
        modelResult.setTotalSize(entityResult.getTotalSize());
        modelResult.setModels(list);
        return modelResult;
    }

}