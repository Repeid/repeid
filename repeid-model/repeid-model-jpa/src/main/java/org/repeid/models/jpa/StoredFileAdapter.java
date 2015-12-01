package org.repeid.models.jpa;

import javax.persistence.EntityManager;

import org.repeid.models.StoredFileModel;
import org.repeid.models.enums.StoredFileProviderName;
import org.repeid.models.jpa.entities.StoredFileEntity;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

public class StoredFileAdapter implements StoredFileModel {

    private static final long serialVersionUID = 1L;

    private StoredFileEntity fileStoreEntity;
    private EntityManager em;

    public StoredFileAdapter(EntityManager em, StoredFileEntity storedFileEntity) {
        this.em = em;
        this.fileStoreEntity = storedFileEntity;
    }

    public static StoredFileEntity toStoredFileEntity(StoredFileModel model, EntityManager em) {
        if (model instanceof StoredFileAdapter) {
            return ((StoredFileAdapter) model).getStoredFileEntity();
        }
        return em.getReference(StoredFileEntity.class, model.getId());
    }

    public StoredFileEntity getStoredFileEntity() {
        return fileStoreEntity;
    }

    @Override
    public void commit() {
        em.merge(fileStoreEntity);
    }

    @Override
    public String getId() {
        return fileStoreEntity.getId();
    }

    @Override
    public String getFileId() {
        return fileStoreEntity.getFileId();
    }

    @Override
    public void setFileId(String fileId) {
        fileStoreEntity.setFileId(fileId);
    }

    @Override
    public StoredFileProviderName getProvider() {
        String provider = fileStoreEntity.getProvider();
        if (provider != null) {
            return StoredFileProviderName.valueOf(provider);
        } else {
            return null;
        }
    }

    @Override
    public void setProvider(StoredFileProviderName provider) {
        if (provider != null) {
            fileStoreEntity.setProvider(provider.toString());
        } else {
            fileStoreEntity.setProvider(null);
        }
    }

    @Override
    public String getUrl() {
        return fileStoreEntity.getUrl();
    }

    @Override
    public void setUrl(String url) {
        fileStoreEntity.setUrl(url);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof StoredFileModel))
            return false;
        StoredFileModel other = (StoredFileModel) obj;
        if (getId() == null) {
            if (other.getId() != null)
                return false;
        } else if (!getId().equals(other.getId()))
            return false;
        return true;
    }

}
