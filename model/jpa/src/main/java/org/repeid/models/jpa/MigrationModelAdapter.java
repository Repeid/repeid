package org.repeid.models.jpa;

import org.repeid.migration.MigrationModel;
import org.repeid.models.jpa.entities.MigrationModelEntity;

import javax.persistence.EntityManager;

public class MigrationModelAdapter implements MigrationModel {
    
    protected EntityManager em;

    public MigrationModelAdapter(EntityManager em) {
        this.em = em;
    }

    @Override
    public String getStoredVersion() {
        MigrationModelEntity entity = em.find(MigrationModelEntity.class, MigrationModelEntity.SINGLETON_ID);
        if (entity == null) return null;
        return entity.getVersion();
    }

    @Override
    public void setStoredVersion(String version) {
        MigrationModelEntity entity = em.find(MigrationModelEntity.class, MigrationModelEntity.SINGLETON_ID);
        if (entity == null) {
            entity = new MigrationModelEntity();
            entity.setId(MigrationModelEntity.SINGLETON_ID);
            entity.setVersion(version);
            em.persist(entity);
        } else {
            entity.setVersion(version);
            em.flush();
        }
    }
}
