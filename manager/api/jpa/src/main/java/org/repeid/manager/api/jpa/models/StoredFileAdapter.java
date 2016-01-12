package org.repeid.manager.api.jpa.models;

import javax.persistence.EntityManager;

import org.repeid.manager.api.jpa.entities.StoreConfigurationEntity;
import org.repeid.manager.api.jpa.entities.StoredFileEntity;
import org.repeid.manager.api.model.StoreConfigurationModel;
import org.repeid.manager.api.model.StoredFileModel;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */

public class StoredFileAdapter implements StoredFileModel {

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
	public String getUrl() {
		return fileStoreEntity.getUrl();
	}

	@Override
	public void setUrl(String url) {
		fileStoreEntity.setUrl(url);
	}

	@Override
	public StoreConfigurationModel getStoreConfiguration() {
		StoreConfigurationEntity storeConfigurationEntity = fileStoreEntity.getStoreConfiguration();
		return new StoreConfigurationAdapter(em, storeConfigurationEntity);
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
