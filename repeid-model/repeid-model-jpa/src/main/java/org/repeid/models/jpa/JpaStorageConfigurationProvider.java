package org.repeid.models.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.repeid.models.ModelDuplicateException;
import org.repeid.models.StorageConfigurationModel;
import org.repeid.models.StorageConfigurationProvider;
import org.repeid.models.jpa.entities.StoreConfigurationEntity;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

@Named
@Stateless
@Local(StorageConfigurationProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaStorageConfigurationProvider extends AbstractHibernateStorage
        implements StorageConfigurationProvider {

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
    public StorageConfigurationModel create(String appKey, String denominacion) {
        if (findByDenominacion(denominacion) != null) {
            throw new ModelDuplicateException(
                    "StoreConfigurationEntity denominacion debe ser unico, se encontro otra entidad con denominacion:"
                            + denominacion);
        }

        StoreConfigurationEntity storeConfigurationEntity = new StoreConfigurationEntity();
        storeConfigurationEntity.setAppKey(appKey);
        storeConfigurationEntity.setDenominacion(denominacion);
        storeConfigurationEntity.setCarpetaFirma("firma");
        storeConfigurationEntity.setCarpetaFoto("foto");
        storeConfigurationEntity.setCarpetaRaiz(appKey);
        storeConfigurationEntity.setCarpetaTemporal("tmp");
        storeConfigurationEntity.setDefault(false);
        storeConfigurationEntity.setToken(null);
        em.persist(storeConfigurationEntity);
        return new StorageConfigurationAdapter(em, storeConfigurationEntity);
    }

    @Override
    public StorageConfigurationModel findById(String id) {
        StoreConfigurationEntity entity = this.em.find(StoreConfigurationEntity.class, id);
        return entity != null ? new StorageConfigurationAdapter(em, entity) : null;
    }

    @Override
    public StorageConfigurationModel findByDenominacion(String denominacion) {
        TypedQuery<StoreConfigurationEntity> query = em.createNamedQuery(
                "StoreConfigurationEntity.findByDenominacion", StoreConfigurationEntity.class);
        query.setParameter("denominacion", denominacion);
        List<StoreConfigurationEntity> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        } else if (results.size() > 1) {
            throw new IllegalStateException("Mas de un StoreConfigurationEntity con denominacion="
                    + denominacion + ", results=" + results);
        } else {
            return new StorageConfigurationAdapter(em, results.get(0));
        }
    }

    @Override
    public StorageConfigurationModel getDefaultStoreConfiguration() {
        TypedQuery<StoreConfigurationEntity> query = em
                .createNamedQuery("StoreConfigurationEntity.findByIsDefault", StoreConfigurationEntity.class);
        query.setParameter("isDefault", true);
        List<StoreConfigurationEntity> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        } else if (results.size() > 1) {
            throw new IllegalStateException(
                    "Mas de un StoreConfigurationEntity con isDefault=" + true + ", results=" + results);
        } else {
            return new StorageConfigurationAdapter(em, results.get(0));
        }
    }

    @Override
    public boolean remove(StorageConfigurationModel storeConfiguration) {
        StoreConfigurationEntity storeConfigurationEntity = em.find(StoreConfigurationEntity.class,
                storeConfiguration.getId());
        if (storeConfigurationEntity == null) {
            return false;
        }
        em.remove(storeConfigurationEntity);
        return true;
    }

    @Override
    public List<StorageConfigurationModel> getAll() {
        TypedQuery<StoreConfigurationEntity> query = em.createNamedQuery("StoreConfigurationEntity.findAll",
                StoreConfigurationEntity.class);

        List<StoreConfigurationEntity> entities = query.getResultList();
        List<StorageConfigurationModel> models = new ArrayList<StorageConfigurationModel>();
        for (StoreConfigurationEntity storeConfigurationEntity : entities) {
            models.add(new StorageConfigurationAdapter(em, storeConfigurationEntity));
        }
        return models;
    }

}
